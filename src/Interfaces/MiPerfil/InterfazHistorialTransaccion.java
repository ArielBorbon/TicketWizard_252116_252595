/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Interfaces.MiPerfil;

import Entidades.Persona;
import Utileria.ConexionBD;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.*;
import java.awt.*;
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

        // Configuración del JFrame
        setTitle("Historial de Transacciones");
        setSize(630, 477);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Componentes de la interfaz
        JLabel TicketWizard = new JLabel("TicketWizard");
        JButton botonRegresar = new JButton("Regresar");
        JLabel texto1 = new JLabel("Registro historial de transacciones");
        JLabel texto2 = new JLabel("Transacciones:");

        // Configuración del título de la app
        TicketWizard.setFont(new Font("Racing Sans One", Font.BOLD, 40));
        TicketWizard.setForeground(Color.BLUE);
        TicketWizard.setBounds(20, 10, 600, 70);

        // Configuración del botón regresar
        botonRegresar.setBackground(new Color(0xEC, 0x22, 0x1F));
        botonRegresar.setForeground(Color.WHITE);
        botonRegresar.setFont(new Font("Arial", Font.BOLD, 12));
        botonRegresar.setSize(90, 36);
        // Usamos getWidth() para posicionar el botón
        botonRegresar.setLocation(getWidth() - botonRegresar.getWidth() - 35, 30);

        // Configuración de las etiquetas
        texto1.setFont(new Font("Inter", Font.BOLD, 16));
        texto2.setFont(new Font("Inter", Font.BOLD, 16));
        texto1.setBounds(20, 50, 600, 70);
        texto2.setBounds(20, 150, 600, 70);

        // ComboBox para el historial de transacciones
        historialTransacciones = new JComboBox<>();
        historialTransacciones.setBounds(20, 210, 500, 30);
        cargarTransacciones();  // Llenamos el combo con las transacciones del usuario

        // Acción del botón regresar
        botonRegresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        // Agregar componentes al JFrame
        add(TicketWizard);
        add(botonRegresar);
        add(texto1);
        add(texto2);
        add(historialTransacciones);

        setVisible(true);
    }

    // Método para cargar las transacciones del usuario en el JComboBox
    private void cargarTransacciones() {
        try (Connection conexion = ConexionBD.crearConexion()) {
            // Consulta para obtener transacciones del usuario
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

                // Formateamos la transacción en una cadena
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

