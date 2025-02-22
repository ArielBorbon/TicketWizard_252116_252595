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
 * @author Alberto Jimenez
 */
public class InterfazPerfilHistorial {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Frame
        JFrame frame = new JFrame("Historial");
        frame.setSize(630, 477);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        
        JLabel TicketWizard = new JLabel("TicketWizard");
        JButton botonRegresar = new JButton("Regresar");
        JButton botonHistorialTransacciones = new JButton("Transacciones");
        JButton botonHistorialBoletos = new JButton("Boletos");
        JButton botonHistorialReventas = new JButton("Reventas");
        
        TicketWizard.setFont(new Font("Racing Sans One", Font.BOLD, 58));
        TicketWizard.setForeground(Color.BLUE);
        TicketWizard.setBounds(20, 10, 600, 70);
        
        botonRegresar.setBackground(new Color(0xEC, 0x22, 0x1F));
        botonRegresar.setForeground(Color.WHITE);
        botonRegresar.setFont(new Font("Arial", Font.BOLD, 12));
        botonRegresar.setSize(90, 36);
        botonRegresar.setLocation(frame.getWidth() - botonRegresar.getWidth() - 35, 30);
        
        // Porque dio webis acomodar bien 
        int posX = frame.getWidth() - botonHistorialTransacciones.getWidth() - 375;
        
        // Boton de la reventa a pesar de que revender es ilegal :0
        botonHistorialTransacciones.setFont(new Font("Arial", Font.BOLD, 14));
        botonHistorialTransacciones.setBackground(new Color(0x65, 0x55, 0x8F)); 
        botonHistorialTransacciones.setForeground(Color.BLACK);
        botonHistorialTransacciones.setSize(160, 40);
        botonHistorialTransacciones.setLocation(posX, 150);
        
        // boton del historial locochon
        botonHistorialBoletos.setFont(new Font("Arial", Font.BOLD, 14));
        botonHistorialBoletos.setBackground(new Color(0x65, 0x55, 0x8F)); 
        botonHistorialBoletos.setForeground(Color.BLACK);
        botonHistorialBoletos.setSize(160, 40);
        botonHistorialBoletos.setLocation(posX, 215);
        
        // Boton de saldo para el pobreton ese :(
        botonHistorialReventas.setFont(new Font("Arial", Font.BOLD, 14));
        botonHistorialReventas.setBackground(new Color(0x65, 0x55, 0x8F)); 
        botonHistorialReventas.setForeground(Color.BLACK);
        botonHistorialReventas.setSize(160, 40);
        botonHistorialReventas.setLocation(posX, 285);
        
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
        
        frame.setVisible(true);
    }
    
}
