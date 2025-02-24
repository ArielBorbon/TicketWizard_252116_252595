
package Daos;

import Entidades.Transaccion;
import Utileria.ConexionBD;
import java.sql.*;
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




    /*
 * Obtiene el historial de transacciones de una persona específica utilizando su ID.
 * @param personaId El ID de la persona cuyo historial de transacciones se desea obtener.
 * @return Una lista de objetos Transaccion asociados a la persona.
 */


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
    
    
/*
 * Mapea un ResultSet a un objeto de tipo Transaccion.
 * @param rs El ResultSet que contiene los datos de la transacción.
 * @return Un objeto Transaccion con los datos mapeados.
 */

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
    
    
    /*
 * Crea una nueva transacción en la base de datos y retorna el ID generado.
 * @param transaccion El objeto Transaccion que contiene los datos de la transacción a crear.
 * @return El ID de la transacción generada.
 * @throws SQLException Si ocurre un error al ejecutar la consulta o no se genera un ID.
 */
    
public int crearTransaccion(Transaccion transaccion) throws SQLException {
    String sql = "INSERT INTO Transacciones (num_transaccion, tipo, monto_total, comision, estado, fecha_expiracion, fecha_hora, persona_id) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    
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
        pstmt.setTimestamp(7, Timestamp.valueOf(transaccion.getFechaHora()));
        
        pstmt.setInt(8, transaccion.getPersonaId());
        
        
        
        pstmt.executeUpdate();
        
        try (ResultSet rs = pstmt.getGeneratedKeys()) {
            if (rs.next()) return rs.getInt(1);
        }
        throw new SQLException("No se generó ID de transacción");
    }
}

/*
 * Verifica si una transacción pendiente sigue siendo válida (no ha expirado).
 * @param transaccionId El ID de la transacción pendiente a verificar.
 * @return true si la transacción sigue siendo válida, false si ha expirado.
 * @throws SQLException Si ocurre un error al ejecutar la consulta.
 */


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
           
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "La reserva ha expirado.");
            return false;
        }
    }
}
    /*
 * Obtiene una transacción de la base de datos utilizando su ID.
 * @param transaccionId El ID de la transacción que se desea obtener.
 * @return Un objeto Transaccion si se encuentra, o null si no existe.
 */
    
    
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
        }
    }
    return null; 
}
    
    
    /*
 * Actualiza el estado de una transacción en la base de datos.
 * @param transaccionId El ID de la transacción cuyo estado se actualizará.
 * @param nuevoEstado El nuevo estado que se asignará a la transacción.
 * @return true si la actualización fue exitosa, false si no se afectaron filas.
 */
    
    
    
    
    
    public boolean actualizarEstado(int transaccionId, String nuevoEstado) throws SQLException {
    String sql = "UPDATE Transacciones SET estado = ? WHERE transaccion_id = ?";
    
    try (Connection conn = ConexionBD.crearConexion();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setString(1, nuevoEstado);
        pstmt.setInt(2, transaccionId);
        
        int filasAfectadas = pstmt.executeUpdate();
        return filasAfectadas > 0; 
    }
}
    
    
    /*
 * Obtiene una lista de IDs de boletos asociados a una transacción específica.
 * @param transaccionId El ID de la transacción de la cual se desean obtener los boletos.
 * @return Una lista de IDs de boletos asociados a la transacción.
 */
    
    
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
    return ids;
}
    
    
    /*
 * Obtiene una lista de transacciones pendientes de una persona que aún no han expirado.
 * @param personaId El ID de la persona cuyas transacciones pendientes se desean obtener.
 * @return Una lista de objetos Transaccion que están pendientes y no han expirado.
 */
    
    
    public List<Transaccion> obtenerTransaccionesPendientes(int personaId) throws SQLException {
    String sql = "SELECT * FROM Transacciones WHERE persona_id = ? AND estado = 'pendiente' AND fecha_expiracion > NOW()";
    List<Transaccion> resultados = new ArrayList<>();
    
    try (Connection conn = ConexionBD.crearConexion();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setInt(1, personaId);
        
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
               
                resultados.add(mapearTransaccion(rs)); 
            }
        }
    }
    return resultados;
}
    
    
}