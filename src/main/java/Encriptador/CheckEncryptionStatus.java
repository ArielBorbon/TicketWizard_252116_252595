
package Encriptador;

import Utileria.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;




public class CheckEncryptionStatus {
    
    /**
 * Verifica si todas las contraseñas de los usuarios están encriptadas en la base de datos.
 *
 * @return true si todas las contraseñas están encriptadas, false en caso contrario o si ocurre un error.
 */
    
    
    public static boolean isEncryptionComplete() {
        String sql = "SELECT COUNT(*) AS count FROM Personas WHERE contrasena_encriptada != 'Y' OR contrasena_encriptada IS NULL";
        
        try (Connection conn = ConexionBD.crearConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            return !rs.next() || rs.getInt("count") == 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
