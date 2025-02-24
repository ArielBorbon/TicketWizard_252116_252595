
package Interfaces.Reventa;

import Control.ControlReventa;
import Entidades.Boleto;
import Entidades.Persona;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Ariel Eduardo Borbon Izaguirre 252116
 * Alberto Jimenez Garcia 252595
 */

public class ConfirmarReventaFrame extends JFrame {
    private Persona usuarioActual;
    private Boleto boletoSeleccionado;
    private double comision;
    private JLabel lblResumen;
    private JButton btnConfirmar;

    public ConfirmarReventaFrame(Persona usuarioActual, Boleto boletoSeleccionado, double comision) {
        this.usuarioActual = usuarioActual;
        this.boletoSeleccionado = boletoSeleccionado;
        this.comision = comision;

        validarDatos();
        configurarVentana();
        initComponentes();
    }

    private void validarDatos() {
        if (usuarioActual == null || boletoSeleccionado == null) {
            throw new IllegalArgumentException("Datos de reventa inválidos");
        }
    }

    private void configurarVentana() {
        setTitle("Confirmar Reventa - TicketWizard");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
    }

private void initComponentes() {

    JPanel panelPrincipal = new JPanel();
    panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
    panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));


    JLabel lblTitulo = new JLabel("Resumen de Reventa");
    lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
    lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

    JLabel lblEvento = new JLabel("Evento: " + boletoSeleccionado.getEvento().getNombre());
    JLabel lblUbicacion = new JLabel("Ubicación: Fila " + boletoSeleccionado.getFila() + ", Asiento " + boletoSeleccionado.getAsiento());
    JLabel lblPrecio = new JLabel("Precio Original: $" + String.format("%.2f", boletoSeleccionado.getPrecioOriginal()));
    JLabel lblComision = new JLabel("Comisión (" + (comision * 100) + "%): $" + String.format("%.2f", boletoSeleccionado.getPrecioOriginal()*0.03));
    JLabel lblTotal = new JLabel("Total a Recibir: $" + String.format("%.2f", boletoSeleccionado.getPrecioOriginal()*1.03));
    

    lblTotal.setFont(new Font("Arial", Font.BOLD, 14));
    lblTotal.setForeground(new Color(0, 100, 0)); 

   
    Component[] componentes = {lblTitulo, lblEvento, lblUbicacion, lblPrecio, lblComision, lblTotal};
    for (Component comp : componentes) {
        ((JLabel) comp).setAlignmentX(Component.CENTER_ALIGNMENT);
    }


    panelPrincipal.add(lblTitulo);
    panelPrincipal.add(Box.createVerticalStrut(10));
    panelPrincipal.add(lblEvento);
    panelPrincipal.add(Box.createVerticalStrut(5));
    panelPrincipal.add(lblUbicacion);
    panelPrincipal.add(Box.createVerticalStrut(15));
    panelPrincipal.add(lblPrecio);
    panelPrincipal.add(lblComision);
    panelPrincipal.add(Box.createVerticalStrut(10));
    panelPrincipal.add(lblTotal);

 
    btnConfirmar = new JButton("Confirmar Reventa Definitiva");
    btnConfirmar.setAlignmentX(Component.CENTER_ALIGNMENT);
    btnConfirmar.addActionListener(e -> {
        try {
            ejecutarReventa();
        } catch (SQLException ex) {
            Logger.getLogger(ConfirmarReventaFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    });


    add(panelPrincipal, BorderLayout.CENTER);
    add(btnConfirmar, BorderLayout.SOUTH);
}

    private void ejecutarReventa() throws SQLException {
        ControlReventa control = new ControlReventa();
        double precioReventa = boletoSeleccionado.getPrecioOriginal() * (1 + comision);

        boolean exito = control.revenderABoletera(
                usuarioActual.getPersonaId(),
                boletoSeleccionado.getBoletoId(),
                precioReventa,
                comision
        );
        if (exito) {
            JOptionPane.showMessageDialog(this,
                    "Reventa exitosa! El boleto ahora está disponible en la plataforma.",
                    "Operación Completada",
                    JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Error al procesar la reventa. Intente nuevamente.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}


