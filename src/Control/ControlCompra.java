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

public class ControlCompra {
    private transaccionDAO transaccionDAO = new transaccionDAO();
    private boletoDAO boletoDAO = new boletoDAO();
    private personaDAO personaDAO = new personaDAO();
    private TransaccionBoletoDAO transaccionBoletoDAO = new TransaccionBoletoDAO();

public boolean comprarBoletosDirectos(int compradorId, List<Integer> boletosIds) {
    Connection conn = null;
    try {
        conn = ConexionBD.crearConexion();
        conn.setAutoCommit(false); // Iniciar transacción

        // Validar saldo
        double total = calcularTotalCompra(boletosIds);
        Persona comprador = personaDAO.obtenerPorId(compradorId);

        if (comprador.getSaldo() >= total) {
            // Crear transacción de compra
            Transaccion transaccion = new Transaccion();
            transaccion.setTipo("compra_directa");
            transaccion.setMontoTotal(total);
            transaccion.setComision(total * 0.03);
            transaccion.setEstado("completado");
            transaccion.setPersonaId(compradorId);

            int transaccionId = transaccionDAO.crearTransaccion(transaccion);

            // Transferir boletos
            for (int boletoId : boletosIds) {
                boletoDAO.actualizarPropietario(boletoId, compradorId);
                transaccionBoletoDAO.vincularBoletoATransaccion(transaccionId, boletoId);
            }

            // Actualizar saldos
            personaDAO.actualizarSaldo(compradorId, -total);
            personaDAO.actualizarSaldo(1, transaccion.getComision()); // Plataforma

            conn.commit(); // Confirmar transacción
            return true;
        } else {
            // Reservar boletos por 10 minutos
            Transaccion transaccion = new Transaccion();
            transaccion.setTipo("compra_directa");
            transaccion.setMontoTotal(total);
            transaccion.setEstado("pendiente");
            LocalDate fechaExpiracion = LocalDateTime.now().plus(10, ChronoUnit.MINUTES).toLocalDate();
            transaccion.setPersonaId(compradorId);

            transaccionDAO.crearTransaccion(transaccion);
            conn.commit(); // Confirmar reserva
            return false;
        }
    } catch (SQLException e) {
        if (conn != null) {
            try {
                conn.rollback(); // Revertir cambios en caso de error
            } catch (SQLException rollbackEx) {
            }
        }
        e.printStackTrace();
        return false;
    } finally {
        if (conn != null) {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException closeEx) {
                closeEx.printStackTrace();
            }
        }
    }
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