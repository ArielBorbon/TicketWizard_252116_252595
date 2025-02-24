
package Interfaces.MiPerfil;

import Entidades.Persona;
import Utileria.ConexionBD;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;


/**
 *
 * @author Ariel Eduardo Borbon Izaguirre 252116
 * Alberto Jimenez Garcia 252595
 */


public class InterfazHistorialTransaccion extends JFrame {
    private Persona personaChida;
    private JComboBox<String> historialTransacciones;

    public InterfazHistorialTransaccion(Persona personachila) {
        this.personaChida = personachila;


        setTitle("Historial de Transacciones");
        setSize(630, 477);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);


        JLabel TicketWizard = new JLabel("TicketWizard");
        JButton botonRegresar = new JButton("Regresar");
        JLabel texto1 = new JLabel("Registro historial de transacciones");
        JLabel texto2 = new JLabel("Transacciones:");


        TicketWizard.setFont(new Font("Racing Sans One", Font.BOLD, 40));
        TicketWizard.setForeground(Color.BLUE);
        TicketWizard.setBounds(20, 10, 600, 70);


        botonRegresar.setBackground(new Color(0xEC, 0x22, 0x1F));
        botonRegresar.setForeground(Color.WHITE);
        botonRegresar.setFont(new Font("Arial", Font.BOLD, 12));
        botonRegresar.setSize(90, 36);

        botonRegresar.setLocation(getWidth() - botonRegresar.getWidth() - 35, 30);


        texto1.setFont(new Font("Inter", Font.BOLD, 16));
        texto2.setFont(new Font("Inter", Font.BOLD, 16));
        texto1.setBounds(20, 50, 600, 70);
        texto2.setBounds(20, 150, 600, 70);


        historialTransacciones = new JComboBox<>();
        historialTransacciones.setBounds(20, 210, 500, 30);
        cargarTransacciones(); 


        botonRegresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });


        add(TicketWizard);
        add(botonRegresar);
        add(texto1);
        add(texto2);
        add(historialTransacciones);

        setVisible(true);
    }


    private void cargarTransacciones() {
        try (Connection conexion = ConexionBD.crearConexion()) {

            String consulta = "SELECT transaccion_id, num_transaccion, fecha_hora, tipo, monto_total, estado " +
                              "FROM Transacciones WHERE persona_id = ?";
            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setInt(1, personaChida.getPersonaId());
            ResultSet resultado = statement.executeQuery();

            ArrayList<String> transacciones = new ArrayList<>();
            while (resultado.next()) {
                int transaccionId = resultado.getInt("transaccion_id");
                String numTransaccion = resultado.getString("num_transaccion");
                Timestamp fechaHora = resultado.getTimestamp("fecha_hora");
                String tipo = resultado.getString("tipo");
                double montoTotal = resultado.getDouble("monto_total");
                String estado = resultado.getString("estado");


                String transaccion = "ID: " + transaccionId + " | " + numTransaccion + " | " +
                                     fechaHora.toString() + " | " + tipo + " | $" + montoTotal + " | " + estado;
                transacciones.add(transaccion);
            }

            if (transacciones.isEmpty()) {
                historialTransacciones.addItem("No tienes transacciones.");
            } else {
                for (String t : transacciones) {
                    historialTransacciones.addItem(t);
                }
            }

            resultado.close();
            statement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar transacciones: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}