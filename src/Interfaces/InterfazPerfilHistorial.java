/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Interfaces;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author Ariel Eduardo Borbon Izaguirre 252116
 * Alberto Jimenez Garcia 252595
 */
public class InterfazPerfilHistorial extends JFrame{

    public InterfazPerfilHistorial() {
        // JFrame
        JFrame frame = new JFrame("Historial");
        frame.setSize(630, 477);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        
        // Todo lo que se utiliza dentro del JFrame
        JLabel TicketWizard = new JLabel("TicketWizard");
        JButton botonRegresar = new JButton("Regresar");
        JButton botonHistorialTransacciones = new JButton("Transacciones");
        JButton botonHistorialBoletos = new JButton("Boletos");
        JButton botonHistorialReventas = new JButton("Reventas");
        
        // Igual que se vea bien el programa
        TicketWizard.setFont(new Font("Racing Sans One", Font.BOLD, 58));
        TicketWizard.setForeground(Color.BLUE);
        TicketWizard.setBounds(20, 10, 600, 70);
        
        // Solo es el formato para que se vean bonitos
        botonRegresar.setBackground(new Color(0xEC, 0x22, 0x1F));
        botonRegresar.setForeground(Color.WHITE);
        botonRegresar.setFont(new Font("Arial", Font.BOLD, 12));
        botonRegresar.setSize(90, 36);
        botonRegresar.setLocation(frame.getWidth() - botonRegresar.getWidth() - 35, 30);
        
        // Lo que hace es que los acomoda por debajo del principal que en este caso es el 
        // boton historial transacciones
        int posX = frame.getWidth() - botonHistorialTransacciones.getWidth() - 375;
        
        // Boton del historial de transacciones hechas
        botonHistorialTransacciones.setFont(new Font("Arial", Font.BOLD, 14));
        botonHistorialTransacciones.setBackground(new Color(0x65, 0x55, 0x8F)); 
        botonHistorialTransacciones.setForeground(Color.BLACK);
        botonHistorialTransacciones.setSize(160, 40);
        botonHistorialTransacciones.setLocation(posX, 150);
        
        // boton del historial de boletos comprados
        botonHistorialBoletos.setFont(new Font("Arial", Font.BOLD, 14));
        botonHistorialBoletos.setBackground(new Color(0x65, 0x55, 0x8F)); 
        botonHistorialBoletos.setForeground(Color.BLACK);
        botonHistorialBoletos.setSize(160, 40);
        botonHistorialBoletos.setLocation(posX, 215);
        
        // Boton de historial de reventas 
        botonHistorialReventas.setFont(new Font("Arial", Font.BOLD, 14));
        botonHistorialReventas.setBackground(new Color(0x65, 0x55, 0x8F)); 
        botonHistorialReventas.setForeground(Color.BLACK);
        botonHistorialReventas.setSize(160, 40);
        botonHistorialReventas.setLocation(posX, 285);
        
        // Aún faltan las acciones de cada botón para conectarlos al principal
        botonRegresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                
                frame.dispose(); 
            }
        });

        botonHistorialTransacciones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        botonHistorialBoletos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        botonHistorialReventas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        
        // Agregarlos y ya :)
        frame.add(TicketWizard);
        frame.add(botonRegresar);
        frame.add(botonHistorialTransacciones);
        frame.add(botonHistorialBoletos);
        frame.add(botonHistorialReventas); 
        
        //frame.getContentPane().setBackground(new Color(173, 216, 230)); "Solo era para probar"
        
        frame.setVisible(true);
    }
    
}
