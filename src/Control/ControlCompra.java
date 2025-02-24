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
import java.time.LocalDateTime;
import java.util.List;
import java.sql.Connection;
import java.util.UUID;
import java.sql.PreparedStatement;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Ariel Eduardo Borbon Izaguirre 252116
 * Alberto Jimenez Garcia 252595
 */


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
        }, 0, 60_000);
    }

    
    
/**
 * Realiza la compra de boletos de forma directa o a través de reventa, gestionando la transacción y actualizando los saldos.
 *
 * @param compradorId El ID de la persona que está comprando los boletos.
 * @param boletosIds Una lista de IDs de los boletos que se desean comprar.
 * @return true si la compra se completó exitosamente, false si la compra fue registrada como pendiente.
 * @throws SQLException Si ocurre un error al acceder a la base de datos o durante el proceso de compra.
 */

public boolean comprarBoletosDirectos(int compradorId, List<Integer> boletosIds) throws SQLException {
    Connection conn = null;
    try {
        conn = ConexionBD.crearConexion();
        conn.setAutoCommit(false);

        double total = calcularTotalCompra(boletosIds);
        Persona comprador = personaDAO.obtenerPorId(compradorId);
        boolean esCompraDirecta = true;
        double comisionTotal = 0.0;


        for (int boletoId : boletosIds) {
            if (boletoDAO.obtenerVendedorBoleto(boletoId) != 1) {
                esCompraDirecta = false;
                break;
            }
        }

   
        Transaccion transaccion = new Transaccion();
        transaccion.setTipo(esCompraDirecta ? "compra_directa" : "compra_reventa");
        transaccion.setNumTransaccion(generarNumTransaccion());
        transaccion.setFechaHora(LocalDateTime.now()); 
        transaccion.setMontoTotal(total);
        transaccion.setPersonaId(compradorId);

       
        if (!esCompraDirecta) {
            comisionTotal = total * 0.03;
            transaccion.setComision(comisionTotal);
        }

 
if (comprador.getSaldo() < (total + comisionTotal)) {

                transaccion.setEstado("pendiente");
            transaccion.setFechaExpiracion(LocalDateTime.now().plusMinutes(10));
    int transaccionId = transaccionDAO.crearTransaccion(transaccion);
    

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

        transaccion.setEstado("completado");
        int transaccionId = transaccionDAO.crearTransaccion(transaccion);


        for (int boletoId : boletosIds) {
            Boleto boleto = boletoDAO.obtenerPorId(boletoId);
            
            if (boleto.getPersonaId() != 1 && !boletoDAO.estaEnReventa(boletoId)) {
                conn.rollback();
                throw new SQLException("Boleto " + boletoId + " ya vendido");
            }


            if (!boletoDAO.actualizarPropietarioBOOL(boletoId, compradorId)) {
                conn.rollback();
                return false;
            }


            if (!esCompraDirecta) {
                boletoDAO.marcarComoVendido(boletoId);
                double pagoVendedor = boleto.getPrecioOriginal() - comisionTotal;
                personaDAO.actualizarSaldo(boleto.getPersonaId(), pagoVendedor);
            }
            
            transaccionBoletoDAO.vincularBoletoATransaccion(transaccionId, boletoId);
        }


        personaDAO.actualizarSaldo(compradorId, -(total + comisionTotal));
        if (!esCompraDirecta) {
            personaDAO.actualizarSaldo(1, comisionTotal);
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




/**
 * Genera un número de transacción único utilizando un UUID.
 *
 * @return Un string que representa el número de transacción, limitado a los primeros 8 caracteres en mayúsculas.
 */


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
    
    
    /**
 * Libera las reservas de boletos que han expirado, cambiando su estado a 'disponible' 
 * y asignando el ID de la persona a 1 (indicando que están disponibles para la compra).
 *
 * @throws SQLException Si ocurre un error al acceder a la base de datos.
 */
    public void liberarReservasExpiradas() throws SQLException {
    String sql = "UPDATE Boletos b " +
                "JOIN Transacciones_boletos tb ON b.boleto_id = tb.boleto_id " +
                "JOIN Transacciones t ON tb.transaccion_id = t.transaccion_id " +
                "SET b.estado = 'disponible', b.persona_id = 1 " + 
                "WHERE t.estado = 'pendiente' AND t.fecha_expiracion < NOW()";
    
    try (Connection conn = ConexionBD.crearConexion();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
         
        pstmt.executeUpdate();
    }
}
    
    
    /**
 * Completa una compra pendiente, actualizando el estado de la transacción y los boletos asociados.
 *
 * @param transaccionId El ID de la transacción que se desea completar.
 * @return true si la compra se completó exitosamente, false si no se pudo completar.
 * @throws SQLException Si ocurre un error al acceder a la base de datos o durante el proceso de compra.
 */
    
public boolean completarCompraPendiente(int transaccionId) throws SQLException {
    Connection conn = null;
    try {
        conn = ConexionBD.crearConexion();
        conn.setAutoCommit(false);


        Transaccion transaccion = new transaccionDAO().obtenerPorId(transaccionId);
        if (transaccion == null || 
            !transaccion.getEstado().equals("pendiente") || 
            transaccion.getFechaExpiracion().isBefore(LocalDateTime.now())) {
            return false;
        }

        Persona comprador = new personaDAO().obtenerPorId(transaccion.getPersonaId());
        double totalAPagar = transaccion.getMontoTotal() + transaccion.getComision();
        
        if (comprador.getSaldo() < totalAPagar) {
            return false;
        }

        List<Integer> boletosIds = new TransaccionBoletoDAO().obtenerBoletosDeTransaccion(transaccionId);
        for (int boletoId : boletosIds) {
            Boleto boleto = new boletoDAO().obtenerPorId(boletoId);
            if (!boleto.getEstado().equals("reservado")) {
                conn.rollback();
                return false;
            }
        }

        for (int boletoId : boletosIds) {
            new boletoDAO().actualizarEstadoYPropietario(boletoId, "vendido", comprador.getPersonaId());
        }

        new personaDAO().actualizarSaldo(comprador.getPersonaId(), -totalAPagar);
        
        
        if (transaccion.getTipo().equals("compra_reventa")) {
            double comision = transaccion.getComision();
            new personaDAO().actualizarSaldo(1, comision); 
        }

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