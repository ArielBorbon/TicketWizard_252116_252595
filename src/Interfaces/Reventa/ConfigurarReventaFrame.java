
package Interfaces.Reventa;

import Entidades.Boleto;
import Entidades.Persona;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Ariel Eduardo Borbon Izaguirre 252116
 * Alberto Jimenez Garcia 252595
 */


public class ConfigurarReventaFrame extends JFrame {
    private Persona usuarioActual;
    private Boleto boletoSeleccionado;
    private JComboBox<String> cmbComision;
    private JButton btnContinuar;

    public ConfigurarReventaFrame(Persona usuarioActual, Boleto boletoSeleccionado) {
        this.usuarioActual = usuarioActual;
        this.boletoSeleccionado = boletoSeleccionado;
        
        configurarVentana();
        initComponentes();
    }

    private void configurarVentana() {
        setTitle("Configurar Reventa");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
    }

    private void initComponentes() {
        
        JPanel panelSuperior = new JPanel(new GridLayout(3, 1, 5, 5));
        panelSuperior.setBorder(BorderFactory.createTitledBorder("Detalles del Boleto"));
        
        JLabel lblEvento = new JLabel("Evento: " + boletoSeleccionado.getEvento().getNombre());
        JLabel lblUbicacion = new JLabel("Ubicación: Fila " + boletoSeleccionado.getFila() + ", Asiento " + boletoSeleccionado.getAsiento());
        JLabel lblPrecio = new JLabel("Precio Original: $" + String.format("%.2f", boletoSeleccionado.getPrecioOriginal()));
        
        panelSuperior.add(lblEvento);
        panelSuperior.add(lblUbicacion);
        panelSuperior.add(lblPrecio);

        JPanel panelCentral = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelCentral.setBorder(BorderFactory.createTitledBorder("Comisión de Reventa"));
        
        cmbComision = new JComboBox<>(new String[]{"1%", "2%", "3%"});
        cmbComision.setSelectedIndex(1); 
        cmbComision.setPreferredSize(new Dimension(100, 25));
        
        panelCentral.add(new JLabel("Seleccione comisión:"));
        panelCentral.add(cmbComision);


        JPanel panelInferior = new JPanel();
        btnContinuar = new JButton("Continuar a Confirmación");
        btnContinuar.addActionListener(this::manejarContinuar);
        panelInferior.add(btnContinuar);

        add(panelSuperior, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }

    private void manejarContinuar(ActionEvent e) {
        if (cmbComision.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this,
                "¡Debes seleccionar una comisión válida!",
                "Configuración Incompleta",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
       
        double comision = convertirComision((String) cmbComision.getSelectedItem());
        
        new ConfirmarReventaFrame(
            usuarioActual,
            boletoSeleccionado,
            comision
        ).setVisible(true);
        
        dispose(); 
    }

    private double convertirComision(String textoComision) {
        return Double.parseDouble(textoComision.replace("%", "")) / 100.0;
    }
}
