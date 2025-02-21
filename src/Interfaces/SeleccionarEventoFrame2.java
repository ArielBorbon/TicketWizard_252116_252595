/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces;

import Daos.eventoDAO;
import Entidades.Evento;
import Utileria.ConexionBD;
import java.awt.FlowLayout;
import java.sql.SQLException;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import static javax.swing.BoxLayout.Y_AXIS;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

/**
 *
 * @author PC Gamer
 */
public class SeleccionarEventoFrame2 extends JFrame {
     private JComboBox<String> comboEventos;
    private eventoDAO eventoDAO; // Declarar como variable de instancia
    private JButton botonContinuar;
    private String nombreFiltro;
    private String fechaFiltro;

    public SeleccionarEventoFrame2(String nombreFiltro, String fechaFiltro) {
        this.nombreFiltro = nombreFiltro;
        this.fechaFiltro = fechaFiltro;
   setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);                                                               ////////////////////
        setTitle("Seleccionar Evento");
        setSize(400, 200);
        setLocationRelativeTo(null); // Centrar la ventana

        eventoDAO = new eventoDAO(); // Instanciar el DAO
        comboEventos = new JComboBox<>();
        botonContinuar = new JButton("Continuar");

        cargarEventosEnComboBox();

        // Establecer layout en vertical
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Agregar espacio entre los componentes
    //    add(Box.createVerticalStrut(20)); // Espacio arriba
        add(comboEventos);
        add(Box.createVerticalStrut(10)); // Espacio entre el ComboBox y el Botón
        add(botonContinuar);
        add(Box.createVerticalStrut(20)); // Espacio abajo

        setVisible(true);
    }

    private void cargarEventosEnComboBox() {
        try {
            List<Evento> eventos = eventoDAO.listarEventosConFiltro(nombreFiltro, fechaFiltro); // Llamar al método con filtros
            comboEventos.removeAllItems(); // Limpiar la lista

            for (Evento evento : eventos) {
                comboEventos.addItem(evento.getNombre() + "      " + evento.getFecha()); // Agregar nombres al ComboBox
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar eventos");
        }
    }

    public JComboBox<String> getComboEventos() {
        return comboEventos;
    }
    
    
        private void cargarEventos(JComboBox<String> comboEventos) {
        // Conexión a la base de datos
        String url = "jdbc:mysql://localhost:3306/tu_base_de_datos";  // Asegúrate de usar la URL correcta de tu base de datos
        String usuario = "tu_usuario";
        String contraseña = "tu_contraseña";

StringBuilder sql = new StringBuilder("SELECT * FROM Eventos WHERE 1=1");
if (!fechaFiltro.isEmpty()) {
    sql.append(" AND DATE(fecha) = ?");
}
if (!nombreFiltro.isEmpty()) {
    sql.append(" AND nombre LIKE ?");
}

        
        
                try (Connection conn = ConexionBD.crearConexion();  // Usamos la conexión desde ConexionBD
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            int index = 1; // Índice para el PreparedStatement
            // Establecer parámetros de fecha si es necesario
           if (!fechaFiltro.isEmpty()) {
    LocalDate fecha = LocalDate.parse(fechaFiltro);
    pstmt.setDate(index++, java.sql.Date.valueOf(fecha)); // Pasar la fecha correctamente
}

           
                    // Establecer parámetros de nombre si es necesario
        if (nombreFiltro != null && !nombreFiltro.trim().isEmpty()) {
            pstmt.setString(index++, "%" + nombreFiltro + "%");
        }
        
        
            // Ejecutar la consulta
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    // Obtener los eventos de la base de datos
                    String nombreEvento = rs.getString("nombre");
                    comboEventos.addItem(nombreEvento); // Añadir cada evento al JComboBox
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    
    
    
    
    
    
    
    
}
    
    
    

