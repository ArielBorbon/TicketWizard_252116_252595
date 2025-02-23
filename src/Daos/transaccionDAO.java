
package Daos;

import Entidades.Transaccion;
import Utileria.ConexionBD;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
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
        transaccion.setFechaHora(timestamp.toLocalDateTime());
        
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
        
        if (transaccion.getFechaExpiracion() != null) {
            pstmt.setTimestamp(6, Timestamp.valueOf(transaccion.getFechaExpiracion()));
            
        } else {
            pstmt.setNull(6, Types.TIMESTAMP); 
        }
        
        
        pstmt.setInt(7, transaccion.getPersonaId());
        
        
        
        pstmt.executeUpdate();
        
        try (ResultSet rs = pstmt.getGeneratedKeys()) {
            if (rs.next()) return rs.getInt(1);
        }
        throw new SQLException("No se generó ID de transacción");
    }
}
    public boolean completarCompraPendiente(int transaccionId) throws SQLException {
    String sql = 
        "SELECT COUNT(*) FROM Transacciones " +
        "WHERE transaccion_id = ? " +
        "AND estado = 'pendiente' " +
        "AND fecha_expiracion > NOW()";
    
    try (Connection conn = ConexionBD.crearConexion();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setInt(1, transaccionId);
        ResultSet rs = pstmt.executeQuery();
        
        if (rs.next() && rs.getInt(1) > 0) {
            // Procesar pago y actualizar a "completado"
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "La reserva ha expirado.");
            return false;
        }
    }
}
    
    
    
    public Transaccion obtenerPorId(int transaccionId) throws SQLException {
    String sql = "SELECT * FROM Transacciones WHERE transaccion_id = ?";
    
    try (Connection conn = ConexionBD.crearConexion();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setInt(1, transaccionId);
        
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                Transaccion transaccion = new Transaccion();
                transaccion.setTransaccionId(rs.getInt("transaccion_id"));
                transaccion.setNumTransaccion(rs.getString("num_transaccion"));
                
                // Convertir Timestamp a LocalDateTime
                Timestamp timestamp = rs.getTimestamp("fecha_hora");
                transaccion.setFechaHora(timestamp.toLocalDateTime());
                
                transaccion.setTipo(rs.getString("tipo"));
                transaccion.setMontoTotal(rs.getDouble("monto_total"));
                transaccion.setComision(rs.getDouble("comision"));
                transaccion.setEstado(rs.getString("estado"));
                
                // Fecha de expiración (puede ser null)
                Timestamp expiracion = rs.getTimestamp("fecha_expiracion");
                if (expiracion != null) {
                    transaccion.setFechaExpiracion(expiracion.toLocalDateTime());
                }
                
                transaccion.setPersonaId(rs.getInt("persona_id"));
                return transaccion;
            }
        }
    }
    return null; // Si no se encuentra la transacción
}
    public boolean actualizarEstado(int transaccionId, String nuevoEstado) throws SQLException {
    String sql = "UPDATE Transacciones SET estado = ? WHERE transaccion_id = ?";
    
    try (Connection conn = ConexionBD.crearConexion();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setString(1, nuevoEstado);
        pstmt.setInt(2, transaccionId);
        
        int filasAfectadas = pstmt.executeUpdate();
        return filasAfectadas > 0; // True si se actualizó al menos una fila
    }
}
    
    public List<Integer> obtenerBoletosDeTransaccion(int transaccionId) throws SQLException {
    String sql = "SELECT boleto_id FROM Transacciones_boletos WHERE transaccion_id = ?";
    List<Integer> ids = new ArrayList<>();
    
    try (Connection conn = ConexionBD.crearConexion();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setInt(1, transaccionId);
        
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                ids.add(rs.getInt("boleto_id"));
            }
        }
    }
    return ids; // Lista vacía si no hay boletos
}
    
    
    
    
    
    public List<Transaccion> obtenerTransaccionesPendientes(int personaId) throws SQLException {
    String sql = "SELECT * FROM Transacciones WHERE persona_id = ? AND estado = 'pendiente' AND fecha_expiracion > NOW()";
    List<Transaccion> resultados = new ArrayList<>();
    
    try (Connection conn = ConexionBD.crearConexion();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setInt(1, personaId);
        
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                // Usar el método obtenerPorId mapeado previamente
                resultados.add(mapearTransaccion(rs)); 
            }
        }
    }
    return resultados;
}
    
    
}
