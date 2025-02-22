/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces.ComprarBoleto;

import Interfaces.ComprarBoleto.ConfirmarCompraFrame;
import Daos.boletoDAO;
import Entidades.Boleto;
import Entidades.Evento;
import Entidades.Persona;
import java.awt.Component;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.sql.SQLException;

/**
 *
 * @author PC Gamer
 */
public class SeleccionarBoletosFrame extends JFrame {
    
    private Evento eventoChido;
    private Persona personaChida;
    private JComboBox<String> comboBoletos;
    private JButton btnContinuar;
    private boletoDAO boletoDAO;
    private List<Boleto> boletosChidos; // Lista de boletos disponibles para el evento
    private List<Boleto> boletos;
    public SeleccionarBoletosFrame(Evento eventoChido, List<Boleto> boletosChidos, Persona personachida) {
        this.eventoChido = eventoChido;
        boletoDAO = new boletoDAO();
        this.boletosChidos = boletosChidos;
        this.personaChida = personachida;
        initComponents();
        cargarBoletos();
    }

    private void initComponents() {
        setTitle("Seleccionar Boletos para " + eventoChido.getNombre());
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Layout vertical usando BoxLayout
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JLabel lblTitulo = new JLabel("Seleccione un boleto:");
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(lblTitulo);

        comboBoletos = new JComboBox<>();
        comboBoletos.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(comboBoletos);

        add(Box.createVerticalStrut(10));

        btnContinuar = new JButton("Continuar");
        btnContinuar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnContinuar.addActionListener(e -> continuarAction());
        add(btnContinuar);

        add(Box.createVerticalStrut(20));

        setVisible(true);
    }

    private void cargarBoletos() {
        try {
            // Se consulta en la BD los boletos disponibles para el evento.
            // Se asume que existe el método obtenerBoletosPorEvento(int eventoId) en boletoDAO.
            boletos = boletoDAO.obtenerBoletosPorEvento(eventoChido.getEventoId());
            comboBoletos.removeAllItems();
            for (Boleto boleto : boletos) {
                // Se muestran datos relevantes del boleto (por ejemplo, número de serie y asiento).
                comboBoletos.addItem("Asiento: " + boleto.getAsiento() + " - Fila: " + boleto.getFila() + " -  Precio: " + boleto.getPrecioOriginal());
                
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar boletos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void continuarAction() {
    int indiceSeleccionado = comboBoletos.getSelectedIndex();
    if (indiceSeleccionado >= 0 && boletos != null && !boletos.isEmpty()) {
        Boleto boletoSeleccionado = boletos.get(indiceSeleccionado);
        
        // Abrir nueva ventana de confirmación
        new ConfirmarCompraFrame(eventoChido, personaChida, boletoSeleccionado).setVisible(true);
        this.dispose(); // Cerrar ventana actual
        
    } else {
        JOptionPane.showMessageDialog(this, 
            "Seleccione un boleto", 
            "Advertencia", 
            JOptionPane.WARNING_MESSAGE);
    }
}


}
