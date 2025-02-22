package control;

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

public class ControlCompra {
    private transaccionDAO transaccionDAO = new transaccionDAO();
    private boletoDAO boletoDAO = new boletoDAO();
    private personaDAO personaDAO = new personaDAO();
    private TransaccionBoletoDAO transaccionBoletoDAO = new TransaccionBoletoDAO();

// Corregir manejo de fechas y transacciones
public boolean comprarBoletosDirectos(int compradorId, List<Integer> boletosIds) throws SQLException {
    Connection conn = null;
    try {
        conn = ConexionBD.crearConexion();
        conn.setAutoCommit(false);

        double total = calcularTotalCompra(boletosIds);
        Persona comprador = personaDAO.obtenerPorId(compradorId);

        
                boolean esCompraDirecta = true;
        for (int boletoId : boletosIds) {
            int vendedorId = boletoDAO.obtenerVendedorBoleto(boletoId);
            if (vendedorId != 1) {
                esCompraDirecta = false;
                break;
            }
        }
                 Transaccion transaccion = new Transaccion();
     //   transaccion.setFechaExpiracion(LocalDateTime.now()); // Siempre fecha/hora actual             ************
        double comision = 0.0;
        
        transaccion.setTipo(esCompraDirecta ? "compra_directa" : "compra_reventa"); // Evita NullPointer
        transaccion.setComision(esCompraDirecta ? 0.0 : total * 0.03); // Comisión 0% si es del sistema
        

            
            transaccion.setComision(comision);
        
 
            
            
        if (comprador.getSaldo() >= total) {
            // Configurar transacción exitosa
            transaccion.setMontoTotal(total);
            transaccion.setEstado("completado");
            transaccion.setFechaExpiracion(null);
            transaccion.setPersonaId(compradorId);
            transaccion.setNumTransaccion(generarNumTransaccion());
            
            int transaccionId = transaccionDAO.crearTransaccion(transaccion);

            
            
            // Transferir boletos y actualizar saldos
            for (int boletoId : boletosIds) {
                // Verificar si el boleto sigue disponible
        Boleto boleto = boletoDAO.obtenerPorId(boletoId);
        if (boleto.getPersonaId() != 1) { // Si no pertenece al sistema
            throw new SQLException("El boleto " + boletoId + " ya fue vendido");
        }
        
        boolean exito = boletoDAO.actualizarPropietario(boletoId, compradorId);
        if (!exito) {
            conn.rollback();
            return false;
        }
                boletoDAO.actualizarPropietario(boletoId, compradorId);
                transaccionBoletoDAO.vincularBoletoATransaccion(transaccionId, boletoId);
            }

            personaDAO.actualizarSaldo(compradorId, -total);
            personaDAO.actualizarSaldo(1, transaccion.getComision());

            
                           if (!transaccion.getTipo().equals("compra_directa")) {
  //      personaDAO.actualizarSaldo(vendedorId, total - comision);
                           }
            
            
            conn.commit();
            return true;
        } else {
            // Reserva temporal
            transaccion.setTipo("compra_directa");
            transaccion.setMontoTotal(total);
            transaccion.setEstado("pendiente");
            transaccion.setFechaExpiracion(LocalDateTime.now().plusMinutes(10));
            transaccion.setPersonaId(compradorId);
            transaccion.setNumTransaccion(generarNumTransaccion());

            transaccionDAO.crearTransaccion(transaccion);
            conn.commit();
            return false;
        }
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
    
    
    
    
    
}