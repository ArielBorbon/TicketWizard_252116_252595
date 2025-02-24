
package Interfaces.MiPerfil;

import Entidades.Persona;
import Utileria.ConexionBD;
import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.sql.*;

/**
 *
 * @author Ariel Eduardo Borbon Izaguirre 252116
 * Alberto Jimenez Garcia 252595
 */
public class InterfazHistorialReventas extends JFrame {
    private Persona personaChida;
    private JComboBox<String> historialReventas;

    public InterfazHistorialReventas(Persona personachila) {
        this.personaChida = personachila;
        JFrame frame = new JFrame("Historial Reventas");
        frame.setSize(630, 477);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);


        JLabel TicketWizard = new JLabel("TicketWizard");
        JButton botonRegresar = new JButton("Regresar");
        JLabel texto1 = new JLabel("Registro historial reventas");
        JLabel texto2 = new JLabel("Historial: ");


        TicketWizard.setFont(new Font("Racing Sans One", Font.BOLD, 58));
        TicketWizard.setForeground(Color.BLUE);
        TicketWizard.setBounds(20, 10, 600, 70);

        botonRegresar.setBackground(new Color(0xEC, 0x22, 0x1F));
        botonRegresar.setForeground(Color.WHITE);
        botonRegresar.setFont(new Font("Arial", Font.BOLD, 12));
        botonRegresar.setSize(90, 36);
        botonRegresar.setLocation(frame.getWidth() - botonRegresar.getWidth() - 35, 30);

        texto1.setFont(new Font("Inter", Font.BOLD, 16));
        texto2.setFont(new Font("Inter", Font.BOLD, 16));
        texto1.setBounds(20, 50, 600, 70);
        texto2.setBounds(20, 150, 600, 70);


        historialReventas = new JComboBox<>();
        historialReventas.setBounds(20, 210, 500, 30);
        cargarReventasBoletera(); 


        botonRegresar.addActionListener(e -> frame.dispose());


        frame.add(TicketWizard);
        frame.add(botonRegresar);
        frame.add(texto1);
        frame.add(texto2);
        frame.add(historialReventas);

        frame.setVisible(true);
    }

/**
 * Carga las reventas disponibles en la boletera y las muestra en un combo box.
 * Si no hay reventas registradas, se muestra un mensaje correspondiente.
 */
    
    private void cargarReventasBoletera() {
        String sql = "SELECT r.reventa_id, r.precio_reventa, r.fecha_limite, r.estado, "
                   + "b.num_serie, e.nombre AS evento "
                   + "FROM Reventas r "
                   + "JOIN Boletos b ON r.boleto_id = b.boleto_id "
                   + "JOIN Eventos e ON b.evento_id = e.evento_id "
                   + "WHERE r.persona_id_vendedor = 1"; 

        try (Connection conn = ConexionBD.crearConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            DecimalFormat priceFormat = new DecimalFormat("$###,###.00");

            while (rs.next()) {
                int id = rs.getInt("reventa_id");
                double precio = rs.getDouble("precio_reventa");
                LocalDateTime fechaLimite = rs.getTimestamp("fecha_limite").toLocalDateTime();
                String estado = rs.getString("estado");
                String numSerie = rs.getString("num_serie");
                String evento = rs.getString("evento");

                String item = String.format(
                    "ID: %d | Serie: %s | Evento: %s | Precio: %s | Válido hasta: %s | Estado: %s",
                    id, numSerie, evento, priceFormat.format(precio), 
                    fechaLimite.format(dateFormatter), estado
                );

                modelo.addElement(item);
            }

            if (modelo.getSize() == 0) {
                modelo.addElement("No hay reventas registradas.");
            }

            historialReventas.setModel(modelo);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                "Error al cargar reventas: " + ex.getMessage(),
                "Error de BD",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}