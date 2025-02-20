
package Daos;

import Entidades.Evento;
import Utileria.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

/**
 *
 * @author Ariel Eduardo Borbon Izaguirre 252116
 * Alberto Jimenez Garcia 252595
 */

public class eventoDAO {
private final ConexionBD conexionBD = new ConexionBD();


    public  List<Evento> obtenerEventosFuturos() throws SQLException {
        String sql = "SELECT * FROM Eventos WHERE fecha > NOW()";
        List<Evento> eventos = new ArrayList<>();
        
        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                eventos.add(mapearEvento(rs));
            }
        }
        return eventos;
    }


    private Evento mapearEvento(ResultSet rs) throws SQLException {
        Evento evento = new Evento();
        evento.setEventoId(rs.getInt("evento_id"));
        evento.setNombre(rs.getString("nombre"));
        

        Timestamp timestamp = rs.getTimestamp("fecha");
        evento.setFecha(timestamp.toLocalDateTime().toLocalDate());
        
        evento.setRecinto(rs.getString("recinto"));
        evento.setCiudad(rs.getString("ciudad"));
        evento.setEstado(rs.getString("estado"));
        evento.setDescripcion(rs.getString("descripcion"));
        evento.setTotalBoletos(rs.getInt("total_boletos"));
        return evento;
    }
}