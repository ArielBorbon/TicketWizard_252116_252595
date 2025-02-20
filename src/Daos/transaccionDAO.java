
package Daos;

import Entidades.Transaccion;
import Utileria.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Ariel Eduardo Borbon Izaguirre 252116
 * Alberto Jimenez Garcia 252595
 */
public class transaccionDAO {
    private ConexionBD conexionBD;


    public List<Transaccion> obtenerHistorial(int personaId) throws SQLException {
        String sql = "SELECT * FROM Transacciones WHERE persona_id = ?";
        List<Transaccion> transacciones = new ArrayList<>();
        
        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, personaId);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    transacciones.add(mapearTransaccion(rs));
                }
            }
        }
        return transacciones;
    }


    private Transaccion mapearTransaccion(ResultSet rs) throws SQLException {
        Transaccion transaccion = new Transaccion();
        transaccion.setTransaccionId(rs.getInt("transaccion_id"));
        transaccion.setNumTransaccion(rs.getString("num_transaccion"));
        

        Timestamp timestamp = rs.getTimestamp("fecha_hora");
        transaccion.setFechaHora(timestamp.toLocalDateTime().toLocalDate());
        
        transaccion.setTipo(rs.getString("tipo"));
        transaccion.setMontoTotal(rs.getDouble("monto_total"));
        transaccion.setComision(rs.getDouble("comision"));
        transaccion.setEstado(rs.getString("estado"));
        
        Timestamp expiracion = rs.getTimestamp("fecha_expiracion");
        if (expiracion != null) {
            transaccion.setFechaExpiracion(expiracion.toLocalDateTime().toLocalDate());
        }
        
        transaccion.setPersonaId(rs.getInt("persona_id"));
        return transaccion;
    }
}
