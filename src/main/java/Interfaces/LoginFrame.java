
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mindrot.jbcrypt.BCrypt;

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

    
    
    
    /**
 * Valida las credenciales del usuario ingresadas en los campos de texto.
 * Si las credenciales son correctas, se inicia la sesión y se abre la interfaz correspondiente.
 * Si las credenciales son incorrectas o ocurre un error, se muestra un mensaje de error.
 */
private void validarCredenciales() {
        String usuario = txtUsuario.getText();
        String contrasena = new String(txtContrasena.getPassword());

        String sql = "SELECT contrasena, contrasena_encriptada FROM Personas WHERE usuario = ?";
        
        try (Connection conn = ConexionBD.crearConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, usuario);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("contrasena");
                String encriptadaFlag = rs.getString("contrasena_encriptada");

                if ("Y".equals(encriptadaFlag)) {
                    // Verificar hash BCrypt
                    if (BCrypt.checkpw(contrasena, storedHash)) {
                        JOptionPane.showMessageDialog(this, "¡Login exitoso!. Las Contraseñas Fueron Encriptadas");
                 Persona personaChida = personaDAO.obtenerPorUsuario(usuario);
                
                this.dispose();
                
                new Interfaz1(personaChida).setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(this, "Contraseña incorrecta. Las Contraseñas Fueron Encriptadas");
                    }
                } else {
                    // Comparación directa (solo durante transición)
                    if (contrasena.equals(storedHash)) {
                        JOptionPane.showMessageDialog(this, "¡Login exitoso! Las Contraseñas Fueron Encriptadas");
                    } else {
                        JOptionPane.showMessageDialog(this, "Contraseña incorrecta, Las Contraseñas Fueron Encriptadas" );
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Usuario no encontrado");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error de base de datos");
        }
    }
}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
/*
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
*/
