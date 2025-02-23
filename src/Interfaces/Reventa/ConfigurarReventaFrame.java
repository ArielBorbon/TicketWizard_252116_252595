/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces.Reventa;

import Daos.ReventaDAO;
import Entidades.Boleto;
import Entidades.Persona;
import Entidades.Reventa;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import java.sql.SQLException;
import javax.swing.JPanel;

/**
 *
 * @author PC Gamer
 */


public class ConfigurarReventaFrame extends JFrame {
    private Persona personaChida;
    private Boleto boletoChido;
    private JComboBox<String> cmbComision;

    public ConfigurarReventaFrame(Persona personaChida, Boleto boletoChido) {
        this.personaChida = personaChida;
        this.boletoChido = boletoChido;

        configurarVentana();
        initComponentes();
    }

    private void configurarVentana() {
        setTitle("Configurar Reventa");
        setSize(350, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(2, 1, 10, 10));
    }

    private void initComponentes() {
        // Panel para selección de comisión
        JPanel panelComision = new JPanel();
        JLabel lblComision = new JLabel("Seleccione la comisión:");
        cmbComision = new JComboBox<>(new String[]{"1%", "2%", "3%"});
        panelComision.add(lblComision);
        panelComision.add(cmbComision);

        // Botón de continuar
        JPanel panelBoton = new JPanel();
        JButton btnContinuar = new JButton("Continuar");
        btnContinuar.addActionListener(this::accionContinuar);
        panelBoton.add(btnContinuar);

        // Agregar paneles al frame
        add(panelComision);
        add(panelBoton);
    }

    private void accionContinuar(ActionEvent e) {
        // Obtener el valor seleccionado
        int indiceSeleccionado = cmbComision.getSelectedIndex();
        double comisionSeleccionada = switch (indiceSeleccionado) {
            case 0 -> 0.01;
            case 1 -> 0.02;
            case 2 -> 0.03;
            default -> 0.00;
        };

        System.out.println(comisionSeleccionada);
        System.out.println(boletoChido.getPrecioOriginal());
        
        
        ConfirmarReventaFrame pantallaconfirmacionReventa = new ConfirmarReventaFrame(personaChida, boletoChido, comisionSeleccionada);
        pantallaconfirmacionReventa.setVisible(true);
        dispose();

    }
}
