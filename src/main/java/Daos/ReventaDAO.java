
package Daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;
import Entidades.Reventa;
import Utileria.ConexionBD;

/**
 *
 * @author Ariel Eduardo Borbon Izaguirre 252116
 * Alberto Jimenez Garcia 252595
 */
public class ReventaDAO {
    private final ConexionBD conexionBD = new ConexionBD();

    
    
    
    /**
 * Crea una nueva reventa en la base de datos insertando un registro en la tabla Reventas.
 *
 * @param reventa El objeto Reventa que contiene la información de la reventa a crear.
 * @throws SQLException Si ocurre un error al acceder a la base de datos.
 */
    
    
    public void crearReventa(Reventa reventa) throws SQLException {
        String sql = "INSERT INTO Reventas (precio_reventa, fecha_limite, estado, boleto_id, persona_id_vendedor) "
                   + "VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setDouble(1, reventa.getPrecioReventa());
            ps.setTimestamp(2, Timestamp.valueOf(reventa.getFechaLimite()));
            ps.setString(3, reventa.getEstado());
            ps.setInt(4, reventa.getBoletoId());
            ps.setInt(5, reventa.getPersonaIdVendedor());
            
            ps.executeUpdate();
        }
    }

    
    /**
 * Obtiene una lista de reventas que están actualmente activas en la base de datos.
 *
 * @return Una lista de objetos Reventa que tienen el estado 'activo'.
 * @throws SQLException Si ocurre un error al acceder a la base de datos.
 */
    
    
    public List<Reventa> obtenerReventasActivas() throws SQLException {
        String sql = "SELECT * FROM Reventas WHERE estado = 'activo'";
        List<Reventa> reventas = new ArrayList<>();
        
        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                reventas.add(mapearReventa(rs));
            }
        }
        return reventas;
    }

    
    
    /**
 * Mapea un registro de ResultSet a un objeto Reventa.
 *
 * @param rs El ResultSet que contiene los datos de la reventa.
 * @return Un objeto Reventa con los datos mapeados desde el ResultSet.
 * @throws SQLException Si ocurre un error al acceder a los datos del ResultSet.
 */
    
    private Reventa mapearReventa(ResultSet rs) throws SQLException {
        Reventa reventa = new Reventa();
        reventa.setReventaId(rs.getInt("reventa_id"));
        reventa.setPrecioReventa(rs.getDouble("precio_reventa"));
        reventa.setFechaLimite(rs.getTimestamp("fecha_limite").toLocalDateTime());
        reventa.setEstado(rs.getString("estado"));
        reventa.setBoletoId(rs.getInt("boleto_id"));
        reventa.setPersonaIdVendedor(rs.getInt("persona_id_vendedor"));
        return reventa;
    }
}


    
    
    
    
    
