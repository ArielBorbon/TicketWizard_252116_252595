
package Daos;

import Entidades.PersonasTransacciones;
import Utileria.ConexionBD;
import java.sql.*;

/**
 *
 * @author Ariel Eduardo Borbon Izaguirre 252116
 * Alberto Jimenez Garcia 252595
 */
public class PersonasTransaccionesDAO {
    private final ConexionBD conexionBD = new ConexionBD();

    public PersonasTransaccionesDAO() {
    }

    
    
    
    public boolean insertar(PersonasTransacciones pt) throws SQLException {
        String sql = "INSERT INTO Personas_transacciones (transaccion_id, comprador_id, vendedor_id) VALUES (?, ?, ?)";
        
        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
          
            if (pt.getTransaccionId() <= 0 || pt.getCompradorId() <= 0 || pt.getVendedorId() <= 0) {
                throw new IllegalArgumentException("IDs de transacción, comprador o vendedor no válidos");
            }
            
            pstmt.setInt(1, pt.getTransaccionId());
            pstmt.setInt(2, pt.getCompradorId());
            pstmt.setInt(3, pt.getVendedorId());
            
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al insertar en Personas_transacciones: " + e.getMessage());
            throw e;
        }
    }

    public PersonasTransacciones obtenerPorTransaccion(int transaccionId) throws SQLException {
        String sql = "SELECT * FROM Personas_transacciones WHERE transaccion_id = ?";
        
        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, transaccionId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    PersonasTransacciones pt = new PersonasTransacciones();
                    pt.setTransaccionId(rs.getInt("transaccion_id"));
                    pt.setCompradorId(rs.getInt("comprador_id"));
                    pt.setVendedorId(rs.getInt("vendedor_id"));
                    return pt;
                }
            }
        }
        return null;
    }
}
   
   
   

