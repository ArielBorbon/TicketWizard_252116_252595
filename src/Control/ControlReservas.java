/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import Utileria.ConexionBD;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.*;

/**
 *
 * @author PC Gamer
 */
public class ControlReservas {
    public static void iniciarLiberacionAutomatica() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    String sql = 
                        "UPDATE Boletos b " +
                        "JOIN Transacciones_boletos tb ON b.boleto_id = tb.boleto_id " +
                        "JOIN Transacciones t ON tb.transaccion_id = t.transaccion_id " +
                        "SET b.estado = 'disponible', b.persona_id = 1 " +
                        "WHERE t.estado = 'pendiente' AND t.fecha_expiracion <= NOW()";
                    
                    try (Connection conn = ConexionBD.crearConexion();
                         PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.executeUpdate();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 60_000); // Ejecutar cada minuto
    }
}
