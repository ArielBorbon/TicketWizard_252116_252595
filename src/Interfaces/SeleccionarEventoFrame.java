/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces;

import Daos.eventoDAO;
import Entidades.Evento;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.sql.SQLException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author PC Gamer
 */
public class SeleccionarEventoFrame extends JFrame {
    private JComboBox<Evento> cmbEventos;
    private eventoDAO eventoDAO;
    private int usuarioId;

    public SeleccionarEventoFrame(int usuarioId) {
        this.usuarioId = usuarioId;
        eventoDAO = new eventoDAO();
        configurarVentana();
        initComponentes();
        cargarEventos();
    }

    private void configurarVentana() {
        setTitle("Seleccionar Evento - TicketWizard");
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
    }

    private void initComponentes() {
        JPanel panelSuperior = new JPanel();
        JLabel lblTitulo = new JLabel("Seleccione un evento:");
        cmbEventos = new JComboBox<>();
        panelSuperior.add(lblTitulo);
        panelSuperior.add(cmbEventos);

        JPanel panelInferior = new JPanel();
        JButton btnConfirmar = new JButton("Confirmar");
        btnConfirmar.addActionListener(this::accionConfirmar);
        
        panelInferior.add(btnConfirmar);

        add(panelSuperior, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }

    private void cargarEventos() {
        try {
            List<Evento> eventos = eventoDAO.listarEventos();
            DefaultComboBoxModel<Evento> modelo = new DefaultComboBoxModel<>();
            for (Evento evento : eventos) {
                modelo.addElement(evento);
            }
            cmbEventos.setModel(modelo);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                "Error al cargar eventos: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void accionConfirmar(ActionEvent e) {
        Evento eventoSeleccionado = (Evento) cmbEventos.getSelectedItem();
        
        if (eventoSeleccionado != null) {
            // Abrir siguiente ventana con el evento seleccionado
    //        new SeleccionarBoletosFrame(usuarioId, eventoSeleccionado).setVisible(true);
    //        this.dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                "Debe seleccionar un evento",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE);
        }
    }

}
