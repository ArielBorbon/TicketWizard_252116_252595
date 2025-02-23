/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces.AgregarSaldo;

import Control.ControlCompra;
import Entidades.Persona;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.sql.*;

/**
 *
 * @author PC Gamer
 */
public class AgregarSaldoFrame extends JFrame {
    private Persona persona;
    private int transaccionId;

    public AgregarSaldoFrame(Persona persona, int transaccionId) {
        this.persona = persona;
        this.transaccionId = transaccionId;
        // ... (diseño de la interfaz para agregar saldo)
        
        JButton btnCompletarCompra = new JButton("Completar Compra");
        btnCompletarCompra.addActionListener(e -> completarCompra());
    }

    private void completarCompra() {
        try {
            ControlCompra control = new ControlCompra();
            boolean exito = control.completarCompraPendiente(transaccionId);
            
            if (exito) {
                JOptionPane.showMessageDialog(this, "¡Compra completada!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "La reserva ha expirado o no hay saldo suficiente.");
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
}
