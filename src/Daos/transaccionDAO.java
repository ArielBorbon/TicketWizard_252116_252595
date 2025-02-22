
package Daos;

import Entidades.Transaccion;
import Utileria.ConexionBD;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Ariel Eduardo Borbon Izaguirre 252116
 * Alberto Jimenez Garcia 252595
 */
public class transaccionDAO {
private final ConexionBD conexionBD = new ConexionBD();


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
            transaccion.setFechaExpiracion(expiracion.toLocalDateTime());
        }
        
        transaccion.setPersonaId(rs.getInt("persona_id"));
        return transaccion;
    }
    
    
public int crearTransaccion(Transaccion transaccion) throws SQLException {
    String sql = "INSERT INTO Transacciones (num_transaccion, tipo, monto_total, comision, estado, fecha_expiracion, persona_id) "
               + "VALUES (?, ?, ?, ?, ?, ?, ?)";
    
    try (Connection conn = ConexionBD.crearConexion();
         PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        
        pstmt.setString(1, transaccion.getNumTransaccion());
        pstmt.setString(2, transaccion.getTipo());
        pstmt.setDouble(3, transaccion.getMontoTotal());
        pstmt.setDouble(4, transaccion.getComision());
        pstmt.setString(5, transaccion.getEstado());
     //   pstmt.setTimestamp(6, Timestamp.valueOf(transaccion.getFechaExpiracion())); // Conversión directa
        pstmt.setInt(7, transaccion.getPersonaId());
        
        if (transaccion.getFechaExpiracion() != null) {
            pstmt.setTimestamp(6, Timestamp.valueOf(transaccion.getFechaExpiracion()));
        } else {
            pstmt.setNull(6, Types.TIMESTAMP); // Permite NULL en la BD
        }
        
        
        pstmt.executeUpdate();
        
        try (ResultSet rs = pstmt.getGeneratedKeys()) {
            if (rs.next()) return rs.getInt(1);
        }
        throw new SQLException("No se generó ID de transacción");
    }
}
    
    
    
    
}
