
package Interfaces.Reventa;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import Entidades.Boleto;
import Entidades.Persona;
import Daos.boletoDAO;
import java.sql.SQLException;

/**
 *
 * @author Ariel Eduardo Borbon Izaguirre 252116
 * Alberto Jimenez Garcia 252595
 */


public class SeleccionarBoletoR extends JFrame {
    private JComboBox<String> cmbBoletos;
    private boletoDAO boletoDAO;
    private Persona usuarioActual;
    private List<Boleto> boletosUsuario;

    public SeleccionarBoletoR(Persona usuarioActual) throws SQLException {
        this.usuarioActual = usuarioActual;
        this.boletoDAO = new boletoDAO();
        
        configurarVentana();
        initComponentes();
        cargarBoletosDisponibles();
    }

    private void configurarVentana() {
        setTitle("Mis Boletos - Reventa");
        setSize(500, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
    }

    private void initComponentes() {
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel lblInstruccion = new JLabel("Selecciona el boleto a revender:");
        cmbBoletos = new JComboBox<>();
        cmbBoletos.setPreferredSize(new Dimension(400, 25));
        panelSuperior.add(lblInstruccion);
        panelSuperior.add(cmbBoletos);

        JPanel panelInferior = new JPanel();
        JButton btnContinuar = new JButton("Siguiente");
        btnContinuar.addActionListener(this::manejarSeleccion);
        panelInferior.add(btnContinuar);

        add(panelSuperior, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }

    private void cargarBoletosDisponibles() throws SQLException {

        boletosUsuario = boletoDAO.obtenerBoletosPorPropietario(usuarioActual.getPersonaId());
        if (boletosUsuario.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                    "No tienes boletos disponibles para revender.",
                    "Inventario Vacío", 
                    JOptionPane.INFORMATION_MESSAGE);
            dispose();
            return;
        }
        DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();
        for (Boleto boleto : boletosUsuario) {
            String infoBoleto = String.format(
                    "Evento: %s | Fila: %s | Asiento: %s | Precio Original: $%.2f",
                    boleto.getEvento().getNombre(),
                    boleto.getFila(),
                    boleto.getAsiento(),
                    boleto.getPrecioOriginal()
            );
            modelo.addElement(infoBoleto);
        }
        cmbBoletos.setModel(modelo);
    }

    private void manejarSeleccion(ActionEvent e) {
        int indiceSeleccionado = cmbBoletos.getSelectedIndex();
        if (indiceSeleccionado == -1) {
            JOptionPane.showMessageDialog(this,
                "¡Selecciona un boleto primero!",
                "Selección Requerida",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        Boleto boletoSeleccionado = boletosUsuario.get(indiceSeleccionado);
        new ConfigurarReventaFrame(usuarioActual, boletoSeleccionado).setVisible(true);
        dispose();
    }
}