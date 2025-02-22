/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces.Reventa;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import Entidades.Boleto;
import Entidades.Persona;
import Daos.boletoDAO;
import java.sql.SQLException;

/**
 *
 * @author PC Gamer
 */


public class SeleccionarBoletoR extends JFrame {
    private JComboBox<String> cmbBoletos;
    private boletoDAO boletoDAO;
    private Persona personaChida;
    private List<Boleto> boletosUsuario;

    public SeleccionarBoletoR(Persona personaChida) {
        this.personaChida = personaChida;
        this.boletoDAO = new boletoDAO();
        
        configurarVentana();
        initComponentes();
        cargarBoletosUsuario();
    }

    private void configurarVentana() {
        setTitle("Seleccionar Boleto para Reventa");
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
    }

    private void initComponentes() {
        JPanel panelSuperior = new JPanel();
        JLabel lblInstruccion = new JLabel("Seleccione el boleto a revender:");
        cmbBoletos = new JComboBox<>();
        panelSuperior.add(lblInstruccion);
        panelSuperior.add(cmbBoletos);

        JPanel panelInferior = new JPanel();
        JButton btnContinuar = new JButton("Continuar");
        btnContinuar.addActionListener(this::accionContinuar);
        panelInferior.add(btnContinuar);

        add(panelSuperior, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }

    private void cargarBoletosUsuario() {
        try {
            // Obtener boletos del usuario que no estén ya en reventa
            boletosUsuario = boletoDAO.obtenerBoletosPorPropietario(personaChida.getPersonaId());
            
            if (boletosUsuario.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "No tienes boletos disponibles para revender",
                    "Advertencia", 
                    JOptionPane.WARNING_MESSAGE);
                this.dispose();
                return;
            }

            DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();
            for (Boleto boleto : boletosUsuario) {
                String texto = String.format("Evento: %s - Fila %s, Asiento %s", 
                    boleto.getEvento().getNombre(), 
                    boleto.getFila(), 
                    boleto.getAsiento());
                modelo.addElement(texto);
            }
            cmbBoletos.setModel(modelo);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                "Error al cargar boletos: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void accionContinuar(ActionEvent e) {
        int indiceSeleccionado = cmbBoletos.getSelectedIndex();
        
        if (indiceSeleccionado >= 0) {
            Boleto boletoSeleccionado = boletosUsuario.get(indiceSeleccionado);
            
            // Abrir siguiente pantalla para configurar reventa
            new ConfigurarReventaFrame(personaChida, boletoSeleccionado).setVisible(true);
            this.dispose();
            
           
        }
    }
}