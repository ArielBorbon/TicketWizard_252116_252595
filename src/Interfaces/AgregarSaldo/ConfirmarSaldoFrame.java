/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
import javax.swing.SwingUtilities;
import java.sql.SQLException;

/**
 *
 * @author PC Gamer
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
        // Mensaje de confirmación
        lblMensaje = new JLabel(
            "<html>Se agregará $" + cantidad + " a tu saldo.<br>" +
            "Esta cantidad será debitada de tu tarjeta.</html>", 
            SwingConstants.CENTER);
        add(lblMensaje, BorderLayout.CENTER);
        
        // Botón Continuar
        btnContinuar = new JButton("Continuar");
        btnContinuar.addActionListener(this::accionContinuar);
        JPanel panelBoton = new JPanel();
        panelBoton.add(btnContinuar);
        add(panelBoton, BorderLayout.SOUTH);
    }
    
    private void accionContinuar(ActionEvent e) {
        try {
            // Se actualiza el saldo sumando la cantidad ingresada.
            // Se asume que personaDAO.actualizarSaldo suma el monto al saldo existente.
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
    
    // Método main para pruebas
 
}

