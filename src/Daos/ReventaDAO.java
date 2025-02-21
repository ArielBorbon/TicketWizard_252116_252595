
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

    
    public void publicarReventa(Reventa reventa) throws SQLException {
        String sql = "INSERT INTO Reventas (precio_reventa, fecha_limite, estado, boleto_id, persona_id_vendedor) "
                   + "VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setDouble(1, reventa.getPrecioReventa());
            ps.setTimestamp(2, Timestamp.valueOf(reventa.getFechaLimite().atStartOfDay()));
            ps.setString(3, reventa.getEstado());
            ps.setInt(4, reventa.getBoletoId());
            ps.setInt(5, reventa.getPersonaIdVendedor());
            
            ps.executeUpdate();
        }
    }


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

    
    private Reventa mapearReventa(ResultSet rs) throws SQLException {
        Reventa reventa = new Reventa();
        reventa.setReventaId(rs.getInt("reventa_id"));
        reventa.setPrecioReventa(rs.getDouble("precio_reventa"));
        reventa.setFechaLimite(rs.getTimestamp("fecha_limite").toLocalDateTime().toLocalDate());
        reventa.setEstado(rs.getString("estado"));
        reventa.setBoletoId(rs.getInt("boleto_id"));
        reventa.setPersonaIdVendedor(rs.getInt("persona_id_vendedor"));
        return reventa;
    }
    
    
    public void crearReventa(Reventa reventa) throws SQLException {
    String sql = "INSERT INTO Reventas (precio_reventa, fecha_limite, estado, boleto_id, persona_id_vendedor) "
               + "VALUES (?, ?, ?, ?, ?)";

    try (Connection conn = conexionBD.crearConexion();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setDouble(1, reventa.getPrecioReventa());
        ps.setTimestamp(2, Timestamp.valueOf(reventa.getFechaLimite().atStartOfDay()));
        ps.setString(3, reventa.getEstado());
        ps.setInt(4, reventa.getBoletoId());
        ps.setInt(5, reventa.getPersonaIdVendedor());

        ps.executeUpdate();
    }
}

    
    
    
    
    
}
