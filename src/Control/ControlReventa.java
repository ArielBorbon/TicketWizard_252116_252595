
package Control;

import Daos.ReventaDAO;
import Daos.boletoDAO;
import Daos.personaDAO;
import Daos.transaccionDAO;
import Entidades.Boleto;
import Entidades.Reventa;
import Entidades.Transaccion;
import Utileria.ConexionBD;
import java.util.Date;
import java.util.List;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;
import java.sql.*;

/**
 *
 * @author Ariel Eduardo Borbon Izaguirre 252116
 * Alberto Jimenez Garcia 252595
 */

public class ControlReventa {
    private ReventaDAO reventaDAO = new ReventaDAO();
    private boletoDAO boletoDAO = new boletoDAO();

    
    
    
    /**
 * Publica boletos en reventa, asegurando que el vendedor sea el propietario y que el precio de reventa no exceda el límite permitido.
 *
 * @param vendedorId El ID del vendedor que está publicando los boletos.
 * @param boletosIds Una lista de IDs de los boletos que se desean poner en reventa.
 * @param precioReventa El precio al que se ofrecerán los boletos en reventa.
 * @param fechaLimite La fecha límite para la reventa.
 * @return true si la publicación en reventa fue exitosa, false en caso contrario.
 */
    
    
    
public boolean publicarEnReventa(int vendedorId, List<Integer> boletosIds, double precioReventa, Date fechaLimite) {
    try {
        for (int boletoId : boletosIds) {
            Boleto boleto = boletoDAO.obtenerPorId(boletoId);
            if (boleto.getPersonaId() != vendedorId) {
                throw new IllegalArgumentException("No eres dueño del boleto ID: " + boletoId);
            }

         
            double precioMaximo = boleto.getPrecioOriginal() * 1.03;
            if (precioReventa > precioMaximo) {
                throw new IllegalArgumentException("El precio excede el 3% del original. Máximo permitido: " + precioMaximo);
            }

     
            Reventa reventa = new Reventa();
            reventa.setBoletoId(boletoId);
            reventa.setPrecioReventa(precioReventa);
            LocalDateTime localDateTimeFechaLimite = LocalDateTime.now();
                                            
            reventa.setFechaLimite(localDateTimeFechaLimite);
            reventa.setEstado("activo");
            reventa.setPersonaIdVendedor(vendedorId);

            reventaDAO.crearReventa(reventa);

        
            boletoDAO.actualizarPropietario(boletoId, 1);

        }
        return true;
    } catch (SQLException | IllegalArgumentException e) {
        e.printStackTrace();
        return false;
    }
}

/**
 * Revende un boleto a la boletera, actualizando el propietario y creando una transacción de reventa.
 *
 * @param vendedorId El ID del vendedor que está revendiendo el boleto.
 * @param boletoId El ID del boleto que se desea revender.
 * @param precioReventa El precio al que se ofrecerá el boleto en reventa.
 * @param comision La comisión asociada a la reventa.
 * @return true si la reventa se completó exitosamente, false en caso contrario.
 * @throws SQLException Si ocurre un error al acceder a la base de datos.
 */


public boolean revenderABoletera(int vendedorId, int boletoId, double precioReventa, double comision) throws SQLException {
    Connection conn = null;
    try {
        conn = ConexionBD.crearConexion();
        conn.setAutoCommit(false);

       
        boletoDAO.actualizarPropietario(boletoId, 1);
        boletoDAO.actualizarEstado(boletoId, "disponible");                                                       

      
        personaDAO pDAO = new personaDAO();
        pDAO.actualizarSaldo(vendedorId, precioReventa);

   
        Transaccion transaccion = new Transaccion();
        transaccion.setTipo("compra_reventa");
        transaccion.setNumTransaccion(generarNumTransaccion());
        transaccion.setMontoTotal(precioReventa);
        transaccion.setComision(comision);
        transaccion.setEstado("completado");
        transaccion.setFechaHora(LocalDateTime.now());
        transaccion.setPersonaId(1); 
        

        transaccionDAO tDAO = new transaccionDAO();
        int transaccionId = tDAO.crearTransaccion(transaccion);


        Reventa reventa = new Reventa();
    reventa.setPrecioReventa(precioReventa);
    reventa.setFechaLimite(LocalDateTime.now().plusDays(7)); 
    reventa.setEstado("activo");
    reventa.setBoletoId(boletoId);
    reventa.setPersonaIdVendedor(1); 
    
    ReventaDAO rDAO = new ReventaDAO();
    rDAO.crearReventa(reventa);

        conn.commit();
        return true;
    } catch (SQLException e) {
        if (conn != null) conn.rollback();
        throw e;
    } finally {
        if (conn != null) conn.close();
    }
}


/**
 * Genera un número de transacción único para las reventas utilizando un UUID.
 *
 * @return Un string que representa el número de transacción, prefijado con "REV-" y limitado a los primeros 8 caracteres en mayúsculas.
 */



    private String generarNumTransaccion() {
        return "REV-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}










