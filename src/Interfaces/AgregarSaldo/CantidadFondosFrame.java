
package Interfaces.AgregarSaldo;

/**
 *
 * @author Ariel Eduardo Borbon Izaguirre 252116
 * Alberto Jimenez Garcia 252595
 */
import Entidades.Persona;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CantidadFondosFrame extends JFrame {
    private Persona personaChila;
    private JTextField cantidadField;
    private JButton btnContinuar;

    public CantidadFondosFrame(Persona personaChila) {
        this.personaChila = personaChila;
        
        setTitle("Ingresar Fondos");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        JLabel lblCantidad = new JLabel("Cantidad a ingresar:");
        cantidadField = new JTextField(10);
        btnContinuar = new JButton("Continuar");

        btnContinuar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double cantidad = Double.parseDouble(cantidadField.getText());
                    
                    ConfirmarSaldoFrame framesaldo = new ConfirmarSaldoFrame(personaChila, cantidad);
                    framesaldo.setVisible(true);
                    dispose();
                    
                    
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(CantidadFondosFrame.this,
                            "Por favor, ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(lblCantidad);
        add(cantidadField);
        add(btnContinuar);
    }
}
