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
        for (int boletoId : boletosIds) {
            Boleto boleto = boletoDAO.obtenerPorId(boletoId);
            if (boleto.getPersonaId() != vendedorId) {
                throw new IllegalArgumentException("No eres dueño del boleto ID: " + boletoId);
            }

            // Validar precio máximo (precio original + 3%)
            double precioMaximo = boleto.getPrecioOriginal() * 1.03;
            if (precioReventa > precioMaximo) {
                throw new IllegalArgumentException("El precio excede el 3% del original. Máximo permitido: " + precioMaximo);
            }

            // Crear la entrada en Reventas
            Reventa reventa = new Reventa();
            reventa.setBoletoId(boletoId);
            reventa.setPrecioReventa(precioReventa);
            LocalDate localDateFechaLimite = fechaLimite.toInstant()
                                            .atZone(ZoneId.systemDefault())
                                            .toLocalDate();
            reventa.setFechaLimite(localDateFechaLimite);
            reventa.setEstado("activo");
            reventa.setPersonaIdVendedor(vendedorId);

            reventaDAO.crearReventa(reventa);

            // Actualizar el estado del boleto para marcarlo en reventa
            boletoDAO.actualizarPropietario(boletoId, 1);

        }
        return true;
    } catch (SQLException | IllegalArgumentException e) {
        e.printStackTrace();
        return false;
    }
}


}
