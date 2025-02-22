
package Daos;

import Entidades.Boleto;
import Utileria.ConexionBD;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
/**
 *
 * @author Ariel Eduardo Borbon Izaguirre 252116
 * Alberto Jimenez Garcia 252595
 */
public class boletoDAO {
    private final ConexionBD conexionBD = new ConexionBD();

    
    public void insertarBoleto(Boleto boleto) throws SQLException {
        String sql = "INSERT INTO Boletos (num_serie, fila, asiento, num_control, precio_original, evento_id, persona_id) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, boleto.getNumSerie());
            ps.setString(2, boleto.getFila());
            ps.setString(3, boleto.getAsiento());
            ps.setString(4, boleto.getNumControl());
            ps.setDouble(5, boleto.getPrecioOriginal());
            ps.setInt(6, boleto.getEventoId());
            ps.setInt(7, boleto.getPersonaId());
            
            ps.executeUpdate();
        }
    }


    public List<Boleto> obtenerBoletosPorUsuario(int personaId) throws SQLException {
        String sql = "SELECT * FROM Boletos WHERE persona_id = ?";
        List<Boleto> boletos = new ArrayList<>();
        
        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, personaId);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    boletos.add(mapearBoleto(rs));
                }
            }
        }
        return boletos;
    }

    private Boleto mapearBoleto(ResultSet rs) throws SQLException {
        Boleto boleto = new Boleto();
        boleto.setBoletoId(rs.getInt("boleto_id"));
        boleto.setNumSerie(rs.getString("num_serie"));
        boleto.setFila(rs.getString("fila"));
        boleto.setAsiento(rs.getString("asiento"));
        boleto.setNumControl(rs.getString("num_control"));
        boleto.setPrecioOriginal(rs.getDouble("precio_original"));
        boleto.setEventoId(rs.getInt("evento_id"));
        boleto.setPersonaId(rs.getInt("persona_id"));
        return boleto;
    }
    
public Boleto obtenerPorId(int boletoId) throws SQLException {
    String sql = "SELECT * FROM Boletos WHERE boleto_id = ?";
    try (Connection conn = ConexionBD.crearConexion();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setInt(1, boletoId);
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return new Boleto(
                    rs.getInt("boleto_id"),
                    rs.getString("num_serie"),
                    rs.getString("fila"),
                    rs.getString("asiento"),
                    rs.getString("num_control"),
                    rs.getDouble("precio_original"),
                    rs.getInt("evento_id"),
                    rs.getInt("persona_id")
                );
            }
        }
    }
    return null; // Si no se encuentra el boleto
}

public void actualizarPropietario(int boletoId, int nuevoPropietarioId) throws SQLException {
    String sql = "UPDATE Boletos SET persona_id = ? WHERE boleto_id = ?";
    try (Connection conn = ConexionBD.crearConexion();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setInt(1, nuevoPropietarioId);
        pstmt.setInt(2, boletoId);
        pstmt.executeUpdate();
    }
}
    
    
public List<Boleto> obtenerBoletosPorEvento(int eventoId) throws SQLException {
    String sql = "SELECT * FROM Boletos WHERE evento_id = ?";
    List<Boleto> boletos = new ArrayList<>();
    
    try (Connection conn = ConexionBD.crearConexion();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setInt(1, eventoId);
        
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Boleto boleto = new Boleto();
                boleto.setBoletoId(rs.getInt("boleto_id"));
                boleto.setNumSerie(rs.getString("num_serie"));
                boleto.setFila(rs.getString("fila"));
                boleto.setAsiento(rs.getString("asiento"));
                boleto.setNumControl(rs.getString("num_control"));
                boleto.setPrecioOriginal(rs.getDouble("precio_original"));
                boleto.setEventoId(rs.getInt("evento_id"));
                boleto.setPersonaId(rs.getInt("persona_id"));
                boletos.add(boleto);
            }
        }
    }
    return boletos;
}





    
    
    
    
    
}
