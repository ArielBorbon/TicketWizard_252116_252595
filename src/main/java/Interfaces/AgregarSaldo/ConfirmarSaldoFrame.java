
package Interfaces.AgregarSaldo;

import Daos.personaDAO;
import Entidades.Persona;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.sql.SQLException;

/**
 *
 * @author Ariel Eduardo Borbon Izaguirre 252116
 * Alberto Jimenez Garcia 252595
 */

public class ConfirmarSaldoFrame extends JFrame {
    private Persona personaChida;
    private double cantidad;
    private JLabel lblMensaje;
    private JButton btnContinuar;
    
    public ConfirmarSaldoFrame(Persona personaChida, double cantidad) {
        this.personaChida = personaChida;
        this.cantidad = cantidad;
        
        configurarVentana();
        initComponentes();
    }
    
    private void configurarVentana() {
        setTitle("Confirmación de Ingreso de Fondos");
        setSize(350, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
    }
    
    private void initComponentes() {

        lblMensaje = new JLabel(
            "<html>Se agregará $" + cantidad + " a tu saldo.<br>" +
            "Esta cantidad será debitada de tu tarjeta.</html>", 
            SwingConstants.CENTER);
        add(lblMensaje, BorderLayout.CENTER);
        

        btnContinuar = new JButton("Continuar");
        btnContinuar.addActionListener(this::accionContinuar);
        JPanel panelBoton = new JPanel();
        panelBoton.add(btnContinuar);
        add(panelBoton, BorderLayout.SOUTH);
    }
    
    private void accionContinuar(ActionEvent e) {
        try {
            
            personaDAO dao = new personaDAO();
            dao.actualizarSaldo(personaChida.getPersonaId(), cantidad);
            
            JOptionPane.showMessageDialog(this, 
                "Operación confirmada.\nSe agregó $" + cantidad + " a tu saldo.",
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
            
            this.dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al actualizar el saldo: " + ex.getMessage(),
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    

 
}
