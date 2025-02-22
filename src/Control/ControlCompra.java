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
        double comisionTotal = 0.0;

        for (int boletoId : boletosIds) {
            int vendedorId = boletoDAO.obtenerVendedorBoleto(boletoId);
            if (vendedorId != 1) {
                esCompraDirecta = false;
                break;
            }
        }

        // Configurar la transacción
        Transaccion transaccion = new Transaccion();
        transaccion.setTipo(esCompraDirecta ? "compra_directa" : "compra_reventa");
        transaccion.setNumTransaccion(generarNumTransaccion());
        transaccion.setFechaHora(LocalDate.now());
        transaccion.setMontoTotal(total);
        transaccion.setEstado("completado");
        transaccion.setPersonaId(compradorId);

        // Aplicar comisión del 3% si es una reventa
        if (!esCompraDirecta) {
            comisionTotal = total * 0.03;
            transaccion.setComision(comisionTotal);
        }

        // Verificar saldo del comprador
        if (comprador.getSaldo() < (total + comisionTotal)) {
            throw new SQLException("Saldo insuficiente para completar la compra.");
        }

        // Crear la transacción en la base de datos
        int transaccionId = transaccionDAO.crearTransaccion(transaccion);

        // Procesar los boletos
        for (int boletoId : boletosIds) {
            Boleto boleto = boletoDAO.obtenerPorId(boletoId);

            // Verificar si el boleto ya fue vendido
            if (boleto.getPersonaId() != 1 && !boletoDAO.estaEnReventa(boletoId)) {
                throw new SQLException("El boleto " + boletoId + " ya fue vendido.");
            }

            int vendedorId = boleto.getPersonaId();

            // Transferir el boleto al comprador
            boolean exito = boletoDAO.actualizarPropietarioBOOL(boletoId, compradorId);
            if (!exito) {
                conn.rollback();
                return false;
            }

            // Desmarcar el boleto de reventa si era de otro usuario
            if (!esCompraDirecta) {
                boletoDAO.marcarComoVendido(boletoId);
            }

            // Vincular boleto con la transacción
            transaccionBoletoDAO.vincularBoletoATransaccion(transaccionId, boletoId);

            // Si es una reventa, pagar al vendedor
            if (!esCompraDirecta) {
                double pagoVendedor = boleto.getPrecioOriginal() - comisionTotal;
                personaDAO.actualizarSaldo(vendedorId, pagoVendedor);
            }
        }

        // Actualizar saldos del comprador y de la boletera
        personaDAO.actualizarSaldo(compradorId, -(total + comisionTotal));
        if (!esCompraDirecta) {
            personaDAO.actualizarSaldo(1, comisionTotal); // Boletera recibe la comisión
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
    
    
    
    
    
}