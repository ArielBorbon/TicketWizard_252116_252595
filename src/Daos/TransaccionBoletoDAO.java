
package Daos;

import Utileria.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Ariel Eduardo Borbon Izaguirre 252116
 * Alberto Jimenez Garcia 252595
 * 
 */

public class TransaccionBoletoDAO {
    private ConexionBD conexionBD;


    public void vincularBoletoATransaccion(int transaccionId, int boletoId) throws SQLException {
        String sql = "INSERT INTO Transacciones_boletos (transaccion_id, boleto_id) VALUES (?, ?)";
        
        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, transaccionId);
            ps.setInt(2, boletoId);
            ps.executeUpdate();
        }
    }


    public List<Integer> obtenerBoletosDeTransaccion(int transaccionId) throws SQLException {
        String sql = "SELECT boleto_id FROM Transacciones_boletos WHERE transaccion_id = ?";
        List<Integer> boletos = new ArrayList<>();
        
        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, transaccionId);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    boletos.add(rs.getInt("boleto_id"));
                }
            }
        }
        return boletos;
    }
}
