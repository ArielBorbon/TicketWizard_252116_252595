
package Encriptador;

import Daos.personaDAO;
import Interfaces.LoginFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

/**
 *
 * @author Ariel Eduardo Borbon Izaguirre 252116
 * Alberto Jimenez Garcia 252595
 */


public class EncriptadorFrame extends JFrame {
    /*
    private personaDAO personadaos;

    public EncriptadorFrame() {
        personadaos = new personaDAO();
        
        // Verificamos si ya se realizó la encriptación
        if (personadaos.estaEncriptado()) {
            new LoginFrame(); // Ir directo al login
            dispose();
            return;
        }

        configurarVentana();
        initComponentes();
    }

    private void configurarVentana() {
        setTitle("Encriptador de Contraseñas");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
    }

    private void initComponentes() {
        JPanel panel = new JPanel();
        JLabel lblMensaje = new JLabel("Botón Encriptador");
        JButton btnEncriptar = new JButton("Encriptar Contraseñas");

        btnEncriptar.addActionListener(this::accionEncriptar);

        panel.add(lblMensaje);
        panel.add(btnEncriptar);
        add(panel, BorderLayout.CENTER);
    }

    private void accionEncriptar(ActionEvent e) {
        try {
            // Llamar al método de encriptado de contraseñas
            personadaos.encriptarContraseñas();

            // Notificar al usuario que las contraseñas fueron encriptadas
           JOptionPane.showMessageDialog(this, "Contraseñas encriptadas correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);


            // Ir al login después de encriptar
            LoginFrame inicioSesion = new LoginFrame();
                    inicioSesion.setVisible(true);
            dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al encriptar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
        personaDAO personadaos = new personaDAO();

        if (personadaos.estaEncriptado()) {
            // Si ya están encriptadas, se muestra directamente el LoginFrame
            new LoginFrame().setVisible(true);
        } else {
            // Si no están encriptadas, se muestra el EncriptadorFrame para procesar la encriptación
            new EncriptadorFrame().setVisible(true);
        }
    });
}

*/

}  
    
    
    
    
    


