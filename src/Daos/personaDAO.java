
package Daos;

import Entidades.Persona;
import Utileria.ConexionBD;
import java.sql.*;
/**
 *
 * @author Ariel Eduardo Borbon Izaguirre 252116
 * Alberto Jimenez Garcia 252595
 */

public class personaDAO {
 private final ConexionBD conexionBD = new ConexionBD();

 
 /*
 * Verifica si las credenciales de un usuario (usuario y contraseña) son válidas.
 * @param usuario El nombre de usuario proporcionado.
 * @param contrasena La contraseña proporcionada.
 * @return true si las credenciales son válidas, false en caso contrario.
 */
 
 
    public boolean autenticar(String usuario, String contrasena) throws SQLException {
        String sql = "SELECT * FROM Personas WHERE usuario = ? AND contrasena = ?";
        
        try (Connection conn = ConexionBD.crearConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, usuario);
            pstmt.setString(2, contrasena);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); 
            }
        }
    }

/*
 * Añade fondos al saldo de una persona en la base de datos.
 * @param personaId El ID de la persona a la que se le añadirán los fondos.
 * @param monto La cantidad de fondos que se agregarán al saldo.
 */
    public void agregarFondos(int personaId, double monto) throws SQLException {
        String sql = "UPDATE Personas SET saldo = saldo + ? WHERE persona_id = ?";
        
        try (Connection conn = ConexionBD.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setDouble(1, monto);
            ps.setInt(2, personaId);
            ps.executeUpdate();
        }
    }

    
    
    /*
 * Mapea un ResultSet a un objeto de tipo Persona.
 * @param rs El ResultSet que contiene los datos de la persona.
 * @return Un objeto Persona con los datos mapeados.
 */
    
    
    private Persona mapearPersona(ResultSet rs) throws SQLException {
        Persona persona = new Persona();
        persona.setPersonaId(rs.getInt("persona_id"));
        persona.setCorreo(rs.getString("correo"));
        persona.setNombreCompleto(rs.getString("nombre_completo"));
        persona.setDomicilio(rs.getString("domicilio"));
        persona.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
        persona.setEdad(rs.getInt("edad"));
        persona.setTotalBoletos(rs.getInt("total_boletos"));
        persona.setSaldo(rs.getDouble("saldo"));
        persona.setUsuario(rs.getString("usuario"));
        persona.setContrasena(rs.getString("contrasena"));
        return persona;
        
    }
        
    
    /*
 * Actualiza el saldo de una persona en la base de datos, sumando el monto especificado.
 * @param personaId El ID de la persona cuyo saldo se actualizará.
 * @param monto El monto que se sumará al saldo actual.
 */
    
public void actualizarSaldo(int personaId, double monto) throws SQLException {
    String sql = "UPDATE Personas SET saldo = saldo + ? WHERE persona_id = ?";
    try (Connection conn = ConexionBD.crearConexion();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setDouble(1, monto);
        pstmt.setInt(2, personaId);
        pstmt.executeUpdate();
    }
}
        
        


/*
 * Obtiene una persona de la base de datos utilizando su ID.
 * @param personaId El ID de la persona que se desea obtener.
 * @return Un objeto Persona si se encuentra, o null si no existe.
 */

        
        public Persona obtenerPorId(int personaId) throws SQLException {
    String sql = "SELECT * FROM Personas WHERE persona_id = ?";
    
    try (Connection conn = ConexionBD.crearConexion();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setInt(1, personaId);
        
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return mapearPersona(rs);
            }
        }
    }
    return null; 
}

        
        /*
 * Obtiene una persona de la base de datos utilizando su nombre de usuario.
 * @param personaUsuario El nombre de usuario de la persona que se desea obtener.
 * @return Un objeto Persona si se encuentra, o null si no existe.
 */
        
        
                public Persona obtenerPorUsuario(String personaUsuario) throws SQLException {
    String sql = "SELECT * FROM Personas WHERE usuario = ?";
    
    try (Connection conn = ConexionBD.crearConexion();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setString(1, personaUsuario);
        
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return mapearPersona(rs);
            }
        }
    }
    return null; 
}
        
    }

