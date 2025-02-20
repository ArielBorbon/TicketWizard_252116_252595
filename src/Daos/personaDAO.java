
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
    private ConexionBD conexionBD;

    public Persona autenticar(String usuario, String contrasena) throws SQLException {
        String sql = "SELECT * FROM Personas WHERE usuario = ? AND contrasena = ?";
        
        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement sentencia = conn.prepareStatement(sql)) {
            
            sentencia.setString(1, usuario);
            sentencia.setString(2, contrasena);
            
            try (ResultSet rs = sentencia.executeQuery()) {
                if (rs.next()) {
                    return mapearPersona(rs);
                }
            }
        }
        return null;
    }

    public void agregarFondos(int personaId, double monto) throws SQLException {
        String sql = "UPDATE Personas SET saldo = saldo + ? WHERE persona_id = ?";
        
        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setDouble(1, monto);
            ps.setInt(2, personaId);
            ps.executeUpdate();
        }
    }

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
}

