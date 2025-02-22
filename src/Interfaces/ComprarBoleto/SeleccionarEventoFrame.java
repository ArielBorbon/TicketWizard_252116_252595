/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces.ComprarBoleto;

import Daos.boletoDAO;
import Daos.eventoDAO;
import Entidades.Boleto;
import Entidades.Evento;
import Entidades.Persona;
import Utileria.ConexionBD;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PC Gamer
 */
public class SeleccionarEventoFrame extends JFrame {
     private JComboBox<String> comboEventos;
    private eventoDAO eventoDAO; // Declarar como variable de instancia
    private JButton botonContinuar;
    private String nombreFiltro;
    private String fechaFiltro;
    private Persona personachida;
    private List<Evento> listaEventos; // Lista para almacenar los eventos recuperados

    public SeleccionarEventoFrame(String nombreFiltro, String fechaFiltro, Persona personachida) {
        this.personachida = personachida;
        this.nombreFiltro = nombreFiltro;
        this.fechaFiltro = fechaFiltro;
   setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);                                                               ////////////////////
        setTitle("Seleccionar Evento");
        setSize(400, 200);
        setLocationRelativeTo(null); // Centrar la ventana

        eventoDAO = new eventoDAO(); // Instanciar el DAO
        comboEventos = new JComboBox<>();
        botonContinuar = new JButton("Continuar");
        listaEventos = new ArrayList<>(); // Inicializamos la lista antes de cargar eventos
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
        
        
        
botonContinuar.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedIndex = comboEventos.getSelectedIndex();
        
        if (selectedIndex != -1) { // Verifica que se haya seleccionado un evento
            Evento eventoSeleccionado = listaEventos.get(selectedIndex); // Obtiene el evento correspondiente
            
            boletoDAO boletoDao = new boletoDAO(); // Instancia del DAO
            List<Boleto> boletosChidos = null;
            try {
                boletosChidos = boletoDao.obtenerBoletosPorEvento(eventoSeleccionado.getEventoId());
            } catch (SQLException ex) {
                Logger.getLogger(SeleccionarEventoFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                boletosChidos = boletoDao.obtenerBoletosPorEvento(eventoSeleccionado.getEventoId()); // Obtener boletos
            } catch (SQLException ex) {
                Logger.getLogger(SeleccionarEventoFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if (boletosChidos.isEmpty()) { // Verifica si la lista de boletos está vacía
                JOptionPane.showMessageDialog(null, "No hay boletos disponibles para este evento.");
            } else {
                // Si hay boletos disponibles, abre el siguiente frame
                new SeleccionarBoletosFrame(eventoSeleccionado, boletosChidos, personachida).setVisible(true);
                dispose(); // Cierra la ventana actual
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, selecciona un evento.");
        }
    }
        });
    }
        
        
        
        
        
    

    private void cargarEventosEnComboBox() {
    try {
        listaEventos = eventoDAO.listarEventosConFiltro(nombreFiltro, fechaFiltro); // Llenar la lista
        comboEventos.removeAllItems();

        for (Evento evento : listaEventos) { // Iterar sobre la lista llena
            comboEventos.addItem(evento.getNombre() + "      " + evento.getFecha());
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
    
    
    

