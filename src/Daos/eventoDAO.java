
package Daos;

import Entidades.Evento;
import Utileria.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

/**
 *
 * @author Ariel Eduardo Borbon Izaguirre 252116
 * Alberto Jimenez Garcia 252595
 */

public class eventoDAO {
private final ConexionBD conexionBD = new ConexionBD();


    public  List<Evento> obtenerEventosFuturos() throws SQLException {
        String sql = "SELECT * FROM Eventos WHERE fecha > NOW()";
        List<Evento> eventos = new ArrayList<>();
        
        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                eventos.add(mapearEvento(rs));
            }
        }
        return eventos;
    }


    private Evento mapearEvento(ResultSet rs) throws SQLException {
        Evento evento = new Evento();
        evento.setEventoId(rs.getInt("evento_id"));
        evento.setNombre(rs.getString("nombre"));
        

        Timestamp timestamp = rs.getTimestamp("fecha");
        evento.setFecha(timestamp.toLocalDateTime().toLocalDate());
        
        evento.setRecinto(rs.getString("recinto"));
        evento.setCiudad(rs.getString("ciudad"));
        evento.setEstado(rs.getString("estado"));
        evento.setDescripcion(rs.getString("descripcion"));
        evento.setTotalBoletos(rs.getInt("total_boletos"));
        return evento;
    }
    


public List<Evento> listarEventos() throws SQLException {
    List<Evento> eventos = new ArrayList<>();
    String sql = "SELECT * FROM Eventos";
    
    try (Connection conn = ConexionBD.crearConexion();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {
        
        while (rs.next()) {
            Evento evento = new Evento();
            evento.setEventoId(rs.getInt("evento_id"));
            evento.setNombre(rs.getString("nombre"));
            
            // Obtener el Timestamp de la base de datos
            Timestamp timestamp = rs.getTimestamp("fecha");
            
            // Convertir Timestamp a LocalDate
            if (timestamp != null) {
                LocalDate localDate = timestamp.toLocalDateTime().toLocalDate();
                evento.setFecha(localDate);  // Asignar el LocalDate al setter
            }
            
            evento.setRecinto(rs.getString("recinto"));
            // ... otros campos
            evento.setCiudad("ciudad");
            evento.setEstado("estado");
            evento.setDescripcion("descripcion");
            evento.getTotalBoletos();
            
            eventos.add(evento);
        }
    }
    return eventos;
}

    

    public List<Evento> listarEventosConFiltro(String nombreFiltro, String fechaFiltro) throws SQLException {
    String sql = "SELECT * FROM Eventos WHERE 1=1"; // Empezamos con una cláusula que siempre es verdadera

    // Condicionales para agregar filtros a la consulta
    if (!fechaFiltro.isEmpty()) {
        sql += " AND DATE(fecha) = ?";
    }
    if (!nombreFiltro.isEmpty()) {
        sql += " AND nombre LIKE ?";
    }

    try (Connection conn = ConexionBD.crearConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
        int index = 1; // Índice para el PreparedStatement

        // Establecer parámetros de fecha si es necesario
        if (!fechaFiltro.isEmpty()) {
            LocalDate fecha = LocalDate.parse(fechaFiltro);
            pstmt.setDate(index++, java.sql.Date.valueOf(fecha));  // Usa LocalDate para convertir a SQL Date
        }


        // Establecer parámetros de nombre si es necesario
        if (!nombreFiltro.isEmpty()) {
            pstmt.setString(index++, "%" + nombreFiltro + "%"); // Usar LIKE para permitir coincidencias parciales
        }

        try (ResultSet rs = pstmt.executeQuery()) {
            List<Evento> eventos = new ArrayList<>();
            while (rs.next()) {
                Evento evento = new Evento();
                evento.setNombre(rs.getString("nombre"));
                evento.setFecha(rs.getTimestamp("fecha").toLocalDateTime().toLocalDate());
                eventos.add(evento);
            }
            return eventos;
        }
    }
}
}

    