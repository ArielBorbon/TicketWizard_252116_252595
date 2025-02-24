
package Interfaces.MiPerfil;

import Entidades.Persona;
import Utileria.ConexionBD;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
/**
 *
 * @author Ariel Eduardo Borbon Izaguirre 252116
 * Alberto Jimenez Garcia 252595
 */

public class InterfazHistorialBoletos extends JFrame {
    private Persona personaChida;
    private JComboBox<String> historialTransacciones;

    public InterfazHistorialBoletos(Persona personachila) {
        this.personaChida = personachila;


        setTitle("Historial Boletos");
        setSize(630, 477);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);


        JLabel TicketWizard = new JLabel("TicketWizard");
        JButton botonRegresar = new JButton("Regresar");
        JLabel texto1 = new JLabel("Registro historial de boletos");
        JLabel texto2 = new JLabel("Historial:");


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
        cargarBoletos();


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

    
    
    /**
 * Carga los boletos asociados a la persona actual y los muestra en el combo box de historial de transacciones.
 * Si no hay boletos disponibles, se muestra un mensaje correspondiente.
 */
    
    
    private void cargarBoletos() {
        try (Connection conexion = ConexionBD.crearConexion()) {  
            String consulta = """
                SELECT B.num_serie, B.fila, B.asiento, E.nombre
                FROM Boletos B
                JOIN Eventos E ON B.evento_id = E.evento_id
                WHERE B.persona_id = ?;
            """;

            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setInt(1, personaChida.getPersonaId()); 
            ResultSet resultado = statement.executeQuery();

            ArrayList<String> boletos = new ArrayList<>();
            while (resultado.next()) {
                String tituloObra = resultado.getString("nombre");
                String numSerie = resultado.getString("num_serie");
                String fila = resultado.getString("fila");
                String asiento = resultado.getString("asiento");
                
                boletos.add("Obra: " + tituloObra + " | Serie: " + numSerie + " | Fila: " + fila + " | Asiento: " + asiento);
            }

            if (boletos.isEmpty()) {
                historialTransacciones.addItem("No tienes boletos.");
            } else {
                for (String boleto : boletos) {
                    historialTransacciones.addItem(boleto);
                }
            }

            resultado.close();
            statement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar boletos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}