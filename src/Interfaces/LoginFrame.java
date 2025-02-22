/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces;

/**
 *
 * @author PC Gamer
 */
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

        // Componentes de la interfaz
        JLabel lblUsuario = new JLabel("Usuario:");
        JLabel lblContrasena = new JLabel("Contraseña:");
        txtUsuario = new JTextField();
        txtContrasena = new JPasswordField();
        JButton btnLogin = new JButton("Iniciar Sesión");

        // Acción del botón
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validarCredenciales();
            }
        });

        // Agregar componentes al panel
        panel.add(lblUsuario);
        panel.add(txtUsuario);
        panel.add(lblContrasena);
        panel.add(txtContrasena);
        panel.add(new JLabel()); // Espacio vacío
        panel.add(btnLogin);

        add(panel);
    }

    private void validarCredenciales() {
        String usuario = txtUsuario.getText();
        String contrasena = new String(txtContrasena.getPassword());

        try {
            if (personaDAO.autenticar(usuario, contrasena)) {
                JOptionPane.showMessageDialog(this, "¡Inicio de sesión exitoso!");
                /////pendiente interfaz 1 parametro el usuario para que se lo lleve
                // new MenuPrincipal(usuario).setVisible(true);
                System.out.println("so far so good");
                
                Persona personaChida = personaDAO.obtenerPorUsuario(usuario);
                System.out.println(personaChida);
                
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
                new LoginFrame().setVisible(true);
            }
        });
    }
}