/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces.ComprarBoleto;

/**
 *
 * @author PC Gamer
 */
import Control.ControlCompra;
import Daos.boletoDAO;
import Daos.personaDAO;
import javax.swing.*;
import java.awt.*;
import Entidades.Boleto;
import Entidades.Evento;
import Entidades.Persona;
import Interfaces.AgregarSaldo.AgregarSaldoFrame;
import Utileria.ConexionBD;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;

public class ConfirmarCompraFrame extends JFrame {
    private Evento eventoChido;
    private Persona personaChida;
    private Boleto boletoChido;

    
    public ConfirmarCompraFrame(Evento eventoChido, Persona personaChida, Boleto boletoChido) {
        this.eventoChido = eventoChido;
        this.personaChida = personaChida;
        this.boletoChido = boletoChido;
        
        
        configurarVentana();
        initComponentes();
    }
    
    
    
private void confirmarCompra() throws SQLException {
    try{
        ControlCompra control = new ControlCompra();
    
    List<Integer> boletosIds = Collections.singletonList(this.boletoChido.getBoletoId());
    boolean exito = control.comprarBoletosDirectos(personaChida.getPersonaId(), boletosIds);

    if (exito) {
        // ==== SOLUCIÓN: Obtener saldo actualizado desde la BD ====
        boletoDAO bDAO = new boletoDAO();
        bDAO.actualizarEstado(boletoChido.getBoletoId(), "vendido");
        personaDAO pdao = new personaDAO();
        Persona personaActualizada = pdao.obtenerPorId(personaChida.getPersonaId());
        personaChida.setSaldo(personaActualizada.getSaldo());

        JOptionPane.showMessageDialog(this, 
            "¡Compra confirmada!\nNuevo saldo: $" + personaChida.getSaldo(),
            "Éxito", 
            JOptionPane.INFORMATION_MESSAGE);
        this.dispose();
    } else {
            // ==== Obtener ID de transacción pendiente ====
            int transaccionId = obtenerTransaccionPendiente(personaChida.getPersonaId(), boletoChido.getBoletoId());
            
            // Mostrar opción para reintentar
            JOptionPane.showMessageDialog(null, 
                "¿Transaccion Pendiente, Tienes 10 minutos para añadir fondos",
                "Reserva Activa",
                JOptionPane.WARNING_MESSAGE);

        //    if (opcion == JOptionPane.YES_OPTION) {
        //        new AgregarSaldoFrame(personaChida, transaccionId).setVisible(true); // Pasar transaccionId
         //   }
            dispose();
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this,
            "Error al procesar la compra. Intente nuevamente.",
            "Error",
            JOptionPane.ERROR_MESSAGE
        );
    
        Logger.getLogger(ConfirmarCompraFrame.class.getName()).log(Level.SEVERE, null, ex);

    }
}




    private void configurarVentana() {
        setTitle("Confirmar Compra");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1, 10, 10));
    }

    private void initComponentes() {
        JLabel lblEvento = new JLabel("Evento: " + eventoChido.getNombre());
        JLabel lblFila = new JLabel("Fila: " + boletoChido.getFila());
        JLabel lblAsiento = new JLabel("Asiento: " + boletoChido.getAsiento());
        JLabel lblCosto = new JLabel("Costo: $" + boletoChido.getPrecioOriginal());
        
        JButton btnConfirmar = new JButton("Confirmar Compra");
        btnConfirmar.addActionListener(e -> {
            try {
                confirmarCompra();
            } catch (SQLException ex) {
                Logger.getLogger(ConfirmarCompraFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        add(lblEvento);
        add(lblFila);
        add(lblAsiento);
        add(lblCosto);
        add(btnConfirmar);
    }
    
    
    private int obtenerTransaccionPendiente(int personaId, int boletoId) throws SQLException {
    String sql = "SELECT t.transaccion_id FROM Transacciones t " +
                "JOIN Transacciones_boletos tb ON t.transaccion_id = tb.transaccion_id " +
                "WHERE t.persona_id = ? AND tb.boleto_id = ? AND t.estado = 'pendiente'";
    
    try (Connection conn = ConexionBD.crearConexion();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setInt(1, personaId);
        pstmt.setInt(2, boletoId);
        
        ResultSet rs = pstmt.executeQuery();
        return rs.next() ? rs.getInt("transaccion_id") : -1;
    }
}
    
    


}
