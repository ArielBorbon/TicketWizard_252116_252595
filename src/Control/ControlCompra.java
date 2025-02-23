package Control;



import Daos.TransaccionBoletoDAO;
import Daos.boletoDAO;
import Daos.personaDAO;
import Daos.transaccionDAO;
import Entidades.Boleto;
import Entidades.Persona;
import Entidades.Transaccion;
import Utileria.ConexionBD;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.sql.Connection;
import java.util.UUID;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.util.Timer;
import java.util.TimerTask;

public class ControlCompra {
    private transaccionDAO transaccionDAO = new transaccionDAO();
    private boletoDAO boletoDAO = new boletoDAO();
    private personaDAO personaDAO = new personaDAO();
    private TransaccionBoletoDAO transaccionBoletoDAO = new TransaccionBoletoDAO();
    private Timer timer;
    
    public ControlCompra() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    liberarReservasExpiradas();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 60_000); // Ejecutar cada 60 segundos
    }

    
    

// Corregir manejo de fechas y transacciones
public boolean comprarBoletosDirectos(int compradorId, List<Integer> boletosIds) throws SQLException {
    Connection conn = null;
    try {
        conn = ConexionBD.crearConexion();
        conn.setAutoCommit(false);

        double total = calcularTotalCompra(boletosIds);
        Persona comprador = personaDAO.obtenerPorId(compradorId);
        boolean esCompraDirecta = true;
        double comisionTotal = 0.0;

        // Determinar tipo de transacción
        for (int boletoId : boletosIds) {
            if (boletoDAO.obtenerVendedorBoleto(boletoId) != 1) {
                esCompraDirecta = false;
                break;
            }
        }

        // Configurar transacción base
        Transaccion transaccion = new Transaccion();
        transaccion.setTipo(esCompraDirecta ? "compra_directa" : "compra_reventa");
        transaccion.setNumTransaccion(generarNumTransaccion());
        transaccion.setFechaHora(LocalDateTime.now()); // Cambiado a LocalDateTime
        transaccion.setMontoTotal(total);
        transaccion.setPersonaId(compradorId);

        // Calcular comisión para reventas
        if (!esCompraDirecta) {
            comisionTotal = total * 0.03;
            transaccion.setComision(comisionTotal);
        }

        // Verificar saldo
if (comprador.getSaldo() < (total + comisionTotal)) {
    // Crear transacción pendiente
                transaccion.setEstado("pendiente");
            transaccion.setFechaExpiracion(LocalDateTime.now().plusMinutes(10));
    int transaccionId = transaccionDAO.crearTransaccion(transaccion);
    
    // Reservar cada boleto
    for (int boletoId : boletosIds) {
        if (!boletoDAO.reservarBoleto(boletoId, transaccionId)) {
            conn.rollback();
            throw new SQLException("Error al reservar boleto " + boletoId);
        }
        transaccionBoletoDAO.vincularBoletoATransaccion(transaccionId, boletoId);
    }
    
    conn.commit();
    return false;
}

        // ===== COMPRA EXITOSA =====
        transaccion.setEstado("completado");
        int transaccionId = transaccionDAO.crearTransaccion(transaccion);

        // Procesar boletos
        for (int boletoId : boletosIds) {
            Boleto boleto = boletoDAO.obtenerPorId(boletoId);
            
            if (boleto.getPersonaId() != 1 && !boletoDAO.estaEnReventa(boletoId)) {
                conn.rollback();
                throw new SQLException("Boleto " + boletoId + " ya vendido");
            }

            // Transferir propiedad
            if (!boletoDAO.actualizarPropietarioBOOL(boletoId, compradorId)) {
                conn.rollback();
                return false;
            }

            // Manejar reventas
            if (!esCompraDirecta) {
                boletoDAO.marcarComoVendido(boletoId);
                double pagoVendedor = boleto.getPrecioOriginal() - comisionTotal;
                personaDAO.actualizarSaldo(boleto.getPersonaId(), pagoVendedor);
            }
            
            transaccionBoletoDAO.vincularBoletoATransaccion(transaccionId, boletoId);
        }

        // Actualizar saldos
        personaDAO.actualizarSaldo(compradorId, -(total + comisionTotal));
        if (!esCompraDirecta) {
            personaDAO.actualizarSaldo(1, comisionTotal); // Comisión a la plataforma
        }

        conn.commit();
        return true;

    } catch (SQLException e) {
        if (conn != null) conn.rollback();
        throw e;
    } finally {
        if (conn != null) {
            conn.setAutoCommit(true);
            conn.close();
        }
    }
}


private String generarNumTransaccion() {
    return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
}

    
    private double calcularTotalCompra(List<Integer> boletosIds) throws SQLException {
        double total = 0;
        for (int boletoId : boletosIds) {
            Boleto boleto = boletoDAO.obtenerPorId(boletoId);
            total += boleto.getPrecioOriginal();
        }
        return total;
    }
    
    
    
    public void liberarReservasExpiradas() throws SQLException {
    String sql = "UPDATE Boletos b " +
                "JOIN Transacciones_boletos tb ON b.boleto_id = tb.boleto_id " +
                "JOIN Transacciones t ON tb.transaccion_id = t.transaccion_id " +
                "SET b.estado = 'disponible', b.persona_id = 1 " + // Asignar a la plataforma
                "WHERE t.estado = 'pendiente' AND t.fecha_expiracion < NOW()";
    
    try (Connection conn = ConexionBD.crearConexion();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
         
        pstmt.executeUpdate();
    }
}
    
    
    
    
public boolean completarCompraPendiente(int transaccionId) throws SQLException {
    Connection conn = null;
    try {
        conn = ConexionBD.crearConexion();
        conn.setAutoCommit(false);

        // Paso 1: Verificar validez de la transacción
        Transaccion transaccion = new transaccionDAO().obtenerPorId(transaccionId);
        if (transaccion == null || 
            !transaccion.getEstado().equals("pendiente") || 
            transaccion.getFechaExpiracion().isBefore(LocalDateTime.now())) {
            return false;
        }

        // Paso 2: Verificar saldo actualizado
        Persona comprador = new personaDAO().obtenerPorId(transaccion.getPersonaId());
        double totalAPagar = transaccion.getMontoTotal() + transaccion.getComision();
        
        if (comprador.getSaldo() < totalAPagar) {
            return false;
        }

        // Paso 3: Validar estado de los boletos
        List<Integer> boletosIds = new TransaccionBoletoDAO().obtenerBoletosDeTransaccion(transaccionId);
        for (int boletoId : boletosIds) {
            Boleto boleto = new boletoDAO().obtenerPorId(boletoId);
            if (!boleto.getEstado().equals("reservado")) {
                conn.rollback();
                return false;
            }
        }

        // Paso 4: Actualizar estados
        for (int boletoId : boletosIds) {
            new boletoDAO().actualizarEstadoYPropietario(boletoId, "vendido", comprador.getPersonaId());
        }

        // Paso 5: Actualizar saldos
        new personaDAO().actualizarSaldo(comprador.getPersonaId(), -totalAPagar);
        
        // Paso 6: Comisión para reventas
        if (transaccion.getTipo().equals("compra_reventa")) {
            double comision = transaccion.getComision();
            new personaDAO().actualizarSaldo(1, comision); // Plataforma
        }

        // Paso 7: Marcar transacción como completada
        new transaccionDAO().actualizarEstado(transaccionId, "completado");

        conn.commit();
        return true;

    } catch (SQLException e) {
        if (conn != null) conn.rollback();
        throw e;
    } finally {
        if (conn != null) conn.close();
    }
}
    
    
    
    
    
}
