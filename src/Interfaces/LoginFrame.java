
package Interfaces;

/**
 *
 * @author Ariel Eduardo Borbon Izaguirre 252116
 * Alberto Jimenez Garcia 252595
 */
import Control.ControlReservas;
import Utileria.ConexionBD;
import Daos.personaDAO;
import Entidades.Persona;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginFrame extends JFrame {
    private final ConexionBD conexionBD = new ConexionBD();
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private personaDAO personaDAO;

    public LoginFrame() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.personaDAO = new personaDAO();
        configurarVentana();
        initComponentes();
    }

    private void configurarVentana() {
        setTitle("Inicio de Sesión - TicketWizard");
        setSize(300, 200);

        setLocationRelativeTo(null);
    }

    private void initComponentes() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

 
        JLabel lblUsuario = new JLabel("Usuario:");
        JLabel lblContrasena = new JLabel("Contraseña:");
        txtUsuario = new JTextField();
        txtContrasena = new JPasswordField();
        JButton btnLogin = new JButton("Iniciar Sesión");


        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validarCredenciales();
            }
        });


        panel.add(lblUsuario);
        panel.add(txtUsuario);
        panel.add(lblContrasena);
        panel.add(txtContrasena);
        panel.add(new JLabel()); 
        panel.add(btnLogin);

        add(panel);
    }

    private void validarCredenciales() {
        String usuario = txtUsuario.getText();
        String contrasena = new String(txtContrasena.getPassword());

        try {
            if (personaDAO.autenticar(usuario, contrasena)) {
                JOptionPane.showMessageDialog(this, "¡Inicio de sesión exitoso!");

                
                Persona personaChida = personaDAO.obtenerPorUsuario(usuario);
                
                this.dispose();
                
                new Interfaz1(personaChida).setVisible(true);
                
                
                
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Credenciales incorrectas", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                "Error al conectar con la base de datos",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    ConexionBD.crearConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                ControlReservas.iniciarLiberacionAutomatica(); 
                new LoginFrame().setVisible(true);
                
                
                
            }
        });
    }
}