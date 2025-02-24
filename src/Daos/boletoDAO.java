
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

    /*
 * Inserta un nuevo boleto en la base de datos.
 * @param boleto El objeto Boleto que contiene los datos del boleto a insertar.
 */
    
    
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
    /*
 * Inserta un nuevo boleto en la base de datos y retorna el ID generado.
 * @param boleto El objeto Boleto que contiene los datos del boleto a insertar.
 * @return El ID del boleto insertado.
 * @throws SQLException Si ocurre un error al insertar el boleto o no se genera un ID.
 */
    
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

    
    
    /*
 * Obtiene una lista de boletos asociados a un usuario específico.
 * @param personaId El ID de la persona cuyos boletos se desean obtener.
 * @return Una lista de objetos Boleto asociados al usuario.
 */
    


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

    

    /*
 * Mapea un ResultSet a un objeto de tipo Boleto.
 * @param rs El ResultSet que contiene los datos del boleto.
 * @return Un objeto Boleto con los datos mapeados.
 */
    
    
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
    
    
    
    
    /* 
Este método obtiene un objeto Boleto a partir de su ID. 
Realiza una consulta a la base de datos para seleccionar todos los campos de la tabla Boletos donde el boleto_id coincide con el ID proporcionado. 
Utiliza un PreparedStatement para evitar inyecciones SQL y establece el ID del boleto como parámetro. 
Si se encuentra un resultado, crea y devuelve un nuevo objeto Boleto utilizando los datos recuperados. 
Si no se encuentra ningún boleto con el ID especificado, el método devuelve null.
*/
    
    
    
    
    
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
    return null; 
}

/**
 * Verifica si un boleto está actualmente en reventa.
 *
 * @param boletoId El ID del boleto que se desea verificar.
 * @return true si el boleto está en reventa activa, false en caso contrario.
 * @throws SQLException Si ocurre un error al acceder a la base de datos.
 */



public boolean estaEnReventa(int boletoId) throws SQLException {
    String sql = "SELECT COUNT(*) FROM Reventas WHERE boleto_id = ? AND estado = 'activo'";
    try (Connection conn = ConexionBD.crearConexion();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setInt(1, boletoId);
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1) > 0; 
            }
        }
    }
    return false;
}
/* 
Actualiza el propietario de un boleto en la base de datos. 
Recibe el ID del boleto y el ID del nuevo propietario, y ejecuta una consulta de actualización para cambiar el campo persona_id del boleto especificado. 
No devuelve ningún valor, pero lanza una SQLException si ocurre un error al acceder a la base de datos.
*/

public void actualizarPropietario(int boletoId, int nuevoPropietarioId) throws SQLException {
    String sql = "UPDATE Boletos SET persona_id = ? WHERE boleto_id = ?"; 
    
    try (Connection conn = ConexionBD.crearConexion();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setInt(1, nuevoPropietarioId); 
        pstmt.setInt(2, boletoId);           
        pstmt.executeUpdate();
    }
}

/**
 * Marca un boleto en reventa como vendido.
 *
 * @param boletoId El ID del boleto que se desea marcar como vendido.
 * @throws SQLException Si ocurre un error al acceder a la base de datos.
 */




public void marcarComoVendido(int boletoId) throws SQLException {
    String sql = "UPDATE Reventas SET estado = 'vendido' WHERE boleto_id = ?";
    try (Connection conn = ConexionBD.crearConexion();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setInt(1, boletoId);
        pstmt.executeUpdate();
    }
}


/**
 * Actualiza el propietario de un boleto y devuelve un valor booleano que indica si la operación fue exitosa.
 *
 * @param boletoId El ID del boleto cuyo propietario se desea actualizar.
 * @param compradorId El ID del nuevo propietario del boleto.
 * @return true si el propietario fue actualizado correctamente, false en caso contrario.
 * @throws SQLException Si ocurre un error al acceder a la base de datos.
 */

public boolean actualizarPropietarioBOOL(int boletoId, int compradorId) throws SQLException {
    String sql = "UPDATE boletos SET persona_id = ? WHERE boleto_id = ?";
    try (Connection conn = ConexionBD.crearConexion();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, compradorId);
        stmt.setInt(2, boletoId);

        int filasAfectadas = stmt.executeUpdate();
        return filasAfectadas > 0; 
    }
}

/**
 * Actualiza el estado de un boleto en la base de datos y devuelve un valor booleano que indica si la operación fue exitosa.
 *
 * @param boletoId El ID del boleto cuyo estado se desea actualizar.
 * @param nuevoEstado El nuevo estado que se asignará al boleto.
 * @return true si el estado fue actualizado correctamente, false en caso contrario.
 * @throws SQLException Si ocurre un error al acceder a la base de datos.
 */

public boolean actualizarEstado(int boletoId, String nuevoEstado) throws SQLException {
    String sql = "UPDATE Boletos SET estado = ? WHERE boleto_id = ?";
    
    try (Connection conn = ConexionBD.crearConexion();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setString(1, nuevoEstado); 
        pstmt.setInt(2, boletoId);
        
        int filasAfectadas = pstmt.executeUpdate();
        return filasAfectadas > 0;
        
    } catch (SQLException e) {
        System.err.println("Error al actualizar estado del boleto ID " + boletoId);
        throw e;
    }
}


/**
 * Actualiza el estado y el propietario de un boleto en la base de datos.
 *
 * @param boletoId El ID del boleto que se desea actualizar.
 * @param nuevoEstado El nuevo estado que se asignará al boleto.
 * @param nuevoPropietarioId El ID del nuevo propietario del boleto.
 * @return true si el estado y el propietario fueron actualizados correctamente, false en caso contrario.
 * @throws SQLException Si ocurre un error al acceder a la base de datos.
 */


    public boolean actualizarEstadoYPropietario(int boletoId, String nuevoEstado, int nuevoPropietarioId) throws SQLException {
    String sql = "UPDATE Boletos SET estado = ?, persona_id = ? WHERE boleto_id = ?";
    
    try (Connection conn = ConexionBD.crearConexion();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setString(1, nuevoEstado);
        pstmt.setInt(2, nuevoPropietarioId);
        pstmt.setInt(3, boletoId);
        
        return pstmt.executeUpdate() > 0;
    }
}

/**
 * Obtiene una lista de boletos disponibles o reservados para un evento específico, 
 * considerando el estado de la transacción y el usuario.
 *
 * @param eventoId El ID del evento para el cual se desean obtener los boletos.
 * @param usuarioId El ID del usuario que está realizando la consulta.
 * @return Una lista de objetos Boleto que cumplen con los criterios especificados.
 * @throws SQLException Si ocurre un error al acceder a la base de datos.
 */
    
    
public List<Boleto> obtenerBoletosPorEvento(int eventoId, int usuarioId) throws SQLException {
    String sql = 
        "SELECT b.* FROM Boletos b " +
        "LEFT JOIN Transacciones_boletos tb ON b.boleto_id = tb.boleto_id " +
        "LEFT JOIN Transacciones t ON tb.transaccion_id = t.transaccion_id " +
        "WHERE b.evento_id = ? " +
        "AND (" +
            "b.estado = 'disponible' " + 
            "OR (" +
                "b.estado = 'reservado' " +
                "AND t.persona_id = ? " +
                "AND t.estado = 'pendiente' " + 
                "AND t.fecha_expiracion > NOW()" + 
            ")" +
        ")";
    
    List<Boleto> boletos = new ArrayList<>();
    
    try (Connection conn = ConexionBD.crearConexion();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setInt(1, eventoId);
        pstmt.setInt(2, usuarioId);  
        
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                boletos.add(mapearBoleto(rs));
            }
        }
    }
    return boletos;
}




/**
 * Obtiene el ID del vendedor asociado a un boleto específico.
 *
 * @param boletoId El ID del boleto del cual se desea obtener el vendedor.
 * @return El ID de la persona que vendió el boleto.
 * @throws SQLException Si ocurre un error al acceder a la base de datos o si el boleto no se encuentra.
 */




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




    
    /**
 * Obtiene una lista de boletos que pertenecen a un propietario específico, 
 * excluyendo aquellos que están actualmente en reventa activa.
 *
 * @param personaId El ID de la persona cuyo boletos se desean obtener.
 * @return Una lista de objetos Boleto que pertenecen al propietario especificado.
 * @throws SQLException Si ocurre un error al acceder a la base de datos.
 */


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
             
                boleto.setBoletoId(rs.getInt("boleto_id"));
                boleto.setFila(rs.getString("fila"));
                boleto.setAsiento(rs.getString("asiento"));
                boleto.setNumSerie(rs.getString("num_serie"));
                boleto.setPrecioOriginal(rs.getDouble("precio_original"));

              
                Evento evento = new Evento();
                evento.setNombre(rs.getString("nombre"));
                evento.setFecha(rs.getTimestamp("fecha").toLocalDateTime());
                evento.setRecinto(rs.getString("recinto"));
                
                boleto.setEvento(evento);
                
                boletos.add(boleto);
            }
        }
    }
    return boletos;
}
    
    
    
    
    
    /**
 * Marca un boleto como disponible para reventa, insertando un nuevo registro en la tabla de reventas.
 *
 * @param boletoId El ID del boleto que se desea poner en reventa.
 * @param precioReventa El precio al que se ofrecerá el boleto en reventa.
 * @param vendedorId El ID de la persona que está vendiendo el boleto.
 * @throws SQLException Si ocurre un error al acceder a la base de datos.
 */
    
    
public void marcarComoEnReventa(int boletoId, double precioReventa, int vendedorId) throws SQLException {
    String sql = "INSERT INTO Reventas (boleto_id, precio_reventa, estado, persona_id_vendedor) " +
                 "VALUES (?, ?, 'activo', ?)";
    try (Connection conn = ConexionBD.crearConexion();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setInt(1, boletoId);
        pstmt.setDouble(2, precioReventa);  
        pstmt.setInt(3, vendedorId);       
        pstmt.executeUpdate();
    }
}

/**
 * Marca un boleto como disponible para reventa, utilizando el precio de reventa y el ID del vendedor 
 * ya existentes en la tabla de reventas.
 *
 * @param boletoId El ID del boleto que se desea poner en reventa.
 * @throws SQLException Si ocurre un error al acceder a la base de datos.
 */

public void marcarComoEnReventa(int boletoId) throws SQLException {

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

/**
 * Reserva un boleto cambiando su estado a 'reservado' y asignando el ID de la transacción al campo persona_id.
 *
 * @param boletoId El ID del boleto que se desea reservar.
 * @param transaccionId El ID de la transacción asociada a la reserva del boleto.
 * @return true si el boleto fue reservado correctamente, false en caso contrario.
 * @throws SQLException Si ocurre un error al acceder a la base de datos.
 */

public boolean reservarBoleto(int boletoId, int transaccionId) throws SQLException {
    String sql = "UPDATE Boletos SET estado = 'reservado', persona_id = ? WHERE boleto_id = ?";
    try (Connection conn = ConexionBD.crearConexion();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
         
        pstmt.setInt(1, transaccionId); 
        pstmt.setInt(2, boletoId);
        
        return pstmt.executeUpdate() > 0;
    }
}





}