
package Daos;

import Entidades.Boleto;
import Entidades.Evento;
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
    
    
public int insertarBoletoint(Boleto boleto) throws SQLException {
    String sql = "INSERT INTO Boletos (num_serie, fila, asiento, num_control, precio_original, evento_id, persona_id) " +
                 "VALUES (?, ?, ?, ?, ?, ?, ?)";
    try (Connection conn = ConexionBD.crearConexion();
         PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        
        ps.setString(1, boleto.getNumSerie());
        ps.setString(2, boleto.getFila());
        ps.setString(3, boleto.getAsiento());
        ps.setString(4, boleto.getNumControl());
        ps.setDouble(5, boleto.getPrecioOriginal());
        ps.setInt(6, boleto.getEventoId());
        ps.setInt(7, boleto.getPersonaId());
        
        int affectedRows = ps.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Error al insertar el boleto, no se afectaron filas.");
        }
        try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Error al insertar el boleto, no se obtuvo ID.");
            }
        }
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
                System.out.println(rs.getDouble("precio_original"));
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

public boolean estaEnReventa(int boletoId) throws SQLException {
    String sql = "SELECT COUNT(*) FROM Reventas WHERE boleto_id = ? AND estado = 'activo'";
    try (Connection conn = ConexionBD.crearConexion();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setInt(1, boletoId);
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1) > 0;  // Si el conteo es mayor que 0, significa que el boleto está en reventa
            }
        }
    }
    return false;
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


public void marcarComoVendido(int boletoId) throws SQLException {
    String sql = "UPDATE Reventas SET estado = 'vendido' WHERE boleto_id = ?";
    try (Connection conn = ConexionBD.crearConexion();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setInt(1, boletoId);
        pstmt.executeUpdate();
    }
}


public boolean actualizarPropietarioBOOL(int boletoId, int compradorId) throws SQLException {
    String sql = "UPDATE boletos SET persona_id = ? WHERE boleto_id = ?";
    try (Connection conn = ConexionBD.crearConexion();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, compradorId);
        stmt.setInt(2, boletoId);

        int filasAfectadas = stmt.executeUpdate();
        return filasAfectadas > 0; // Retorna true si al menos una fila fue actualizada
    }
}




    
    
public List<Boleto> obtenerBoletosPorEvento(int eventoId) throws SQLException {
    String sql = "SELECT * FROM Boletos WHERE evento_id = ? AND persona_id = 1";
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




public int obtenerVendedorBoleto(int boletoId) throws SQLException {
    String sql = "SELECT persona_id FROM Boletos WHERE boleto_id = ?";
    try (Connection conn = ConexionBD.crearConexion();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setInt(1, boletoId);
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("persona_id");
            }
            throw new SQLException("Boleto no encontrado");
        }
    }
}

    public List<Boleto> obtenerBoletosPorPropietario(int personaId) throws SQLException {
    List<Boleto> boletos = new ArrayList<>();
    String sql = "SELECT b.*, e.nombre, e.fecha, e.recinto " +
                 "FROM Boletos b " +
                 "JOIN Eventos e ON b.evento_id = e.evento_id " +
                 "WHERE b.persona_id = ? " +
                 "AND b.boleto_id NOT IN (SELECT boleto_id FROM Reventas WHERE estado = 'activo')";

    try (Connection conn = ConexionBD.crearConexion();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setInt(1, personaId);
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Boleto boleto = new Boleto();
                // Mapear datos del boleto
                boleto.setBoletoId(rs.getInt("boleto_id"));
                boleto.setFila(rs.getString("fila"));
                boleto.setAsiento(rs.getString("asiento"));
                boleto.setNumSerie(rs.getString("num_serie"));
                boleto.setPrecioOriginal(rs.getDouble("precio_original"));

                // Mapear datos del evento
                Evento evento = new Evento();
                evento.setNombre(rs.getString("nombre"));
                evento.setFecha(rs.getTimestamp("fecha").toLocalDateTime());
                evento.setRecinto(rs.getString("recinto"));
                
                boleto.setEvento(evento); // Asignar el evento al boleto
                
                boletos.add(boleto);
            }
        }
    }
    return boletos;
}
    
public void marcarComoEnReventa(int boletoId, double precioReventa, int vendedorId) throws SQLException {
    String sql = "INSERT INTO Reventas (boleto_id, precio_reventa, estado, persona_id_vendedor) " +
                 "VALUES (?, ?, 'activo', ?)";
    try (Connection conn = ConexionBD.crearConexion();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setInt(1, boletoId);
        pstmt.setDouble(2, precioReventa);  // El precio de reventa
        pstmt.setInt(3, vendedorId);        // El id del vendedor
        pstmt.executeUpdate();
    }
}



public void marcarComoEnReventa(int boletoId) throws SQLException {
    // Primero, obtener el precio de reventa y el vendedor
    String sql = "SELECT precio_reventa, persona_id_vendedor FROM Reventas WHERE boleto_id = ?";
    double precioReventa = 0.0;
    int vendedorId = 0;

    try (Connection conn = ConexionBD.crearConexion();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setInt(1, boletoId);
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                precioReventa = rs.getDouble("precio_reventa");
                vendedorId = rs.getInt("persona_id_vendedor");
            }
        }
    }

    // Ahora insertar en Reventas con los datos obtenidos
    String insertSql = "INSERT INTO Reventas (boleto_id, precio_reventa, estado, persona_id_vendedor) " +
                       "VALUES (?, ?, 'activo', ?)";
    try (Connection conn = ConexionBD.crearConexion();
         PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
        
        pstmt.setInt(1, boletoId);
        pstmt.setDouble(2, precioReventa);
        pstmt.setInt(3, vendedorId);
        pstmt.executeUpdate();
    }
}
}
