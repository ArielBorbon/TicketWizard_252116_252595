/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces;

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
 * @author PC Gamer
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
        setLayout(new GridLayout(3, 2, 5, 5)); // 3 filas, 2 columnas
setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);                                           //   ***************************
        // Componentes
        JLabel labelFecha = new JLabel("Filtrar por Fecha:");
        fechaFiltro = new JDateChooser();
        
        JLabel labelNombre = new JLabel("Filtrar por Nombre:");
        nombreFiltro = new JTextField();

        botonContinuar = new JButton("Continuar");

        // Acción del botón
        botonContinuar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener filtros
                Date fechaSeleccionada = fechaFiltro.getDate();
                String nombre = nombreFiltro.getText();

                // Formatear fecha si no es null
                String fechaString = "";
                if (fechaSeleccionada != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    fechaString = sdf.format(fechaSeleccionada);
                }

                // Abrir EventoGUI con los filtros
                new SeleccionarEventoFrame2(nombre, fechaString).setVisible(true);
                dispose(); // Cerrar ventana actual
            }
        });

        // Agregar componentes al frame
        add(labelFecha);
        add(fechaFiltro);
        add(labelNombre);
        add(nombreFiltro);
        add(new JLabel()); // Espacio vacío
        add(botonContinuar);

        setLocationRelativeTo(null); // Centrar ventana
        setVisible(true);
    }

    public static void main(String[] args) {
        Persona personaBeta = new Persona();
        new SeleccionarFiltros(personaBeta);
    }
}
