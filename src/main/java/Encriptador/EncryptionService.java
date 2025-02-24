
package Encriptador;

import Utileria.ConexionBD;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EncryptionService {
    
    
    /**
 * Encripta las contraseñas de los usuarios que aún no están encriptadas en la base de datos.
 * Actualiza el estado de la contraseña a 'Y' una vez que ha sido encriptada.
 */
    
    public static void encryptPasswords() {
        String selectSQL = "SELECT persona_id, contrasena FROM Personas WHERE contrasena_encriptada = 'N'";
        String updateSQL = "UPDATE Personas SET contrasena = ?, contrasena_encriptada = 'Y' WHERE persona_id = ?";
        
        try (Connection conn = ConexionBD.crearConexion();
             PreparedStatement selectStmt = conn.prepareStatement(selectSQL);
             PreparedStatement updateStmt = conn.prepareStatement(updateSQL);
             ResultSet rs = selectStmt.executeQuery()) {
            
            conn.setAutoCommit(false);
            
            while (rs.next()) {
                String plainPassword = rs.getString("contrasena");
                String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt());
                
                updateStmt.setString(1, hashedPassword);
                updateStmt.setInt(2, rs.getInt("persona_id"));
                updateStmt.addBatch();
            }
            
            updateStmt.executeBatch();
            conn.commit();
            
        } catch (Exception e) {
            e.printStackTrace();
           
        }
    }
}