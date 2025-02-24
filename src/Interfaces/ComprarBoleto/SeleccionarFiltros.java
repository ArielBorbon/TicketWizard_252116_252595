
package Interfaces.ComprarBoleto;

import Entidades.Persona;
import com.toedter.calendar.JDateChooser;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Ariel Eduardo Borbon Izaguirre 252116
 * Alberto Jimenez Garcia 252595
 */
public class SeleccionarFiltros extends JFrame{
    private JDateChooser fechaFiltro;
    private JTextField nombreFiltro;
    private JButton botonContinuar;
    private Persona personachida;

    public SeleccionarFiltros(Persona personachida) {
        this.personachida = personachida;
        setTitle("Filtrar Eventos");
        setSize(300, 200);
        setLayout(new GridLayout(3, 2, 5, 5)); 
setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);                                        
      
        JLabel labelFecha = new JLabel("Filtrar por Fecha:");
        fechaFiltro = new JDateChooser();
        
        JLabel labelNombre = new JLabel("Filtrar por Nombre:");
        nombreFiltro = new JTextField();

        botonContinuar = new JButton("Continuar");

      
        botonContinuar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                Date fechaSeleccionada = fechaFiltro.getDate();
                String nombre = nombreFiltro.getText();

              
                String fechaString = "";
                if (fechaSeleccionada != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    fechaString = sdf.format(fechaSeleccionada);
                }

              
                new SeleccionarEventoFrame(nombre, fechaString, personachida).setVisible(true);
                dispose();
            }
        });


        add(labelFecha);
        add(fechaFiltro);
        add(labelNombre);
        add(nombreFiltro);
        add(new JLabel()); 
        add(botonContinuar);

        setLocationRelativeTo(null); 
        setVisible(true);
    }

}