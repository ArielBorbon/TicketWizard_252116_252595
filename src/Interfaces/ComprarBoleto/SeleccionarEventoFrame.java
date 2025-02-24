
package Interfaces.ComprarBoleto;

import Daos.boletoDAO;
import Daos.eventoDAO;
import Entidades.Boleto;
import Entidades.Evento;
import Entidades.Persona;
import Utileria.ConexionBD;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Ariel Eduardo Borbon Izaguirre 252116
 * Alberto Jimenez Garcia 252595
 */
public class SeleccionarEventoFrame extends JFrame {
     private JComboBox<String> comboEventos;
    private eventoDAO eventoDAO;
    private JButton botonContinuar;
    private String nombreFiltro;
    private String fechaFiltro;
    private Persona personachida;
    private List<Evento> listaEventos; 

    public SeleccionarEventoFrame(String nombreFiltro, String fechaFiltro, Persona personachida) {
        this.personachida = personachida;
        this.nombreFiltro = nombreFiltro;
        this.fechaFiltro = fechaFiltro;
   setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);                                                               
        setTitle("Seleccionar Evento");
        setSize(400, 200);
        setLocationRelativeTo(null); 

        eventoDAO = new eventoDAO();
        comboEventos = new JComboBox<>();
        botonContinuar = new JButton("Continuar");
        listaEventos = new ArrayList<>();
        cargarEventosEnComboBox();


        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));


        add(comboEventos);
        add(Box.createVerticalStrut(10));
        add(botonContinuar);
        add(Box.createVerticalStrut(20));

        setVisible(true);
        
        
        
botonContinuar.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedIndex = comboEventos.getSelectedIndex();
        
        if (selectedIndex != -1) {
            Evento eventoSeleccionado = listaEventos.get(selectedIndex);
            boletoDAO boletoDao = new boletoDAO();
            List<Boleto> boletosChidos = new ArrayList<>(); 

            try {
                boletosChidos = boletoDao.obtenerBoletosPorEvento(eventoSeleccionado.getEventoId(), personachida.getPersonaId());

                if (boletosChidos.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No hay boletos disponibles para este evento.");
                } else {
                    new SeleccionarBoletosFrame(eventoSeleccionado, boletosChidos, personachida).setVisible(true);
                    dispose();
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, 
                    "Error al consultar los boletos. Intente nuevamente.", 
                    "Error de conexión", 
                    JOptionPane.ERROR_MESSAGE
                );
                JOptionPane.showMessageDialog(null, "No hay boletos disponibles para este evento.");
            }

        } else {
            JOptionPane.showMessageDialog(null, "Por favor, selecciona un evento.");
        }
    }
});
    }
        
        
        
        
        
    

    private void cargarEventosEnComboBox() {
    try {
        listaEventos = eventoDAO.listarEventosConFiltro(nombreFiltro, fechaFiltro); 
        comboEventos.removeAllItems();

        for (Evento evento : listaEventos) { 
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

StringBuilder sql = new StringBuilder("SELECT * FROM Eventos WHERE 1=1");
if (!fechaFiltro.isEmpty()) {
    sql.append(" AND DATE(fecha) = ?");
}
if (!nombreFiltro.isEmpty()) {
    sql.append(" AND nombre LIKE ?");
}

        
        
                try (Connection conn = ConexionBD.crearConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            int index = 1;
           
           if (!fechaFiltro.isEmpty()) {
    LocalDate fecha = LocalDate.parse(fechaFiltro);
    pstmt.setDate(index++, java.sql.Date.valueOf(fecha));
}

           
                   
        if (nombreFiltro != null && !nombreFiltro.trim().isEmpty()) {
            pstmt.setString(index++, "%" + nombreFiltro + "%");
        }
        
        
       
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                  
                    String nombreEvento = rs.getString("nombre");
                    comboEventos.addItem(nombreEvento);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    
    
    
    
    
    
    
    
}
    
    
    
