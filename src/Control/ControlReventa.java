/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import Daos.ReventaDAO;
import Daos.boletoDAO;
import Entidades.Boleto;
import Entidades.Reventa;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.sql.SQLException;

/**
 *
 * @author PC Gamer
 */

public class ControlReventa {
    private ReventaDAO reventaDAO = new ReventaDAO();
    private boletoDAO boletoDAO = new boletoDAO();

    public boolean publicarEnReventa(int vendedorId, List<Integer> boletosIds, double precioReventa, Date fechaLimite) {
        try {
            // 1. Validar que los boletos pertenecen al vendedor
            for (int boletoId : boletosIds) {
                Boleto boleto = boletoDAO.obtenerPorId(boletoId);
                if (boleto.getPersonaId() != vendedorId) {
                    throw new IllegalArgumentException("No eres dueño del boleto ID: " + boletoId);
                }
                
                // 2. Validar precio máximo (precio original + 3%)
                double precioMaximo = boleto.getPrecioOriginal() * 1.03;
                if (precioReventa > precioMaximo) {
                    throw new IllegalArgumentException("El precio excede el 3% del original. Máximo permitido: " + precioMaximo);
                }
            }
            
            // 3. Crear entradas en Reventas
            for (int boletoId : boletosIds) {
                Reventa reventa = new Reventa();
                reventa.setBoletoId(boletoId);
                reventa.setPrecioReventa(precioReventa);
                LocalDate localDateFechaLimite = fechaLimite.toInstant()
                                            .atZone(ZoneId.systemDefault())  // Usar la zona horaria predeterminada
                                            .toLocalDate();
                reventa.setFechaLimite(localDateFechaLimite);
                reventa.setEstado("activo");
                reventa.setPersonaIdVendedor(vendedorId);
                
                reventaDAO.crearReventa(reventa);
            }
            return true;
        } catch (SQLException | IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }
}
