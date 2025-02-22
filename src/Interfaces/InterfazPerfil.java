/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Interfaces;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author Ariel Eduardo Borbon Izaguirre 252116
 * Alberto Jimenez Garcia 252595
 */
public class InterfazPerfil {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // El frame loco
        JFrame frame = new JFrame("Perfil");
        frame.setSize(630, 477);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        
        // Todo lo que se agrego al frame
        JLabel TicketWizard = new JLabel("TicketWizard");
        JButton botonRegresar = new JButton("Regresar");
        JButton botonReventa = new JButton("Reventa Boleto");
        JButton botonHistorial = new JButton("Historial");
        JButton botonSaldo = new JButton("Agregar Saldo");

        // Ticketwizard titulo me la rife en no hacerlo feo >:)
        TicketWizard.setFont(new Font("Racing Sans One", Font.BOLD, 40));
        TicketWizard.setForeground(Color.BLUE);
        TicketWizard.setBounds(20, 10, 600, 70);

        // Boton regresar para sumbarle :O
        botonRegresar.setBackground(new Color(0xEC, 0x22, 0x1F));
        botonRegresar.setForeground(Color.WHITE);
        botonRegresar.setFont(new Font("Arial", Font.BOLD, 12));
        botonRegresar.setSize(90, 36);
        botonRegresar.setLocation(frame.getWidth() - botonRegresar.getWidth() - 35, 30);

        // Recuadros para que se vea más aca B)
        Font labelFont = new Font("Arial", Font.BOLD, 12);

        String[] textos = {"Usuario:", "Nombre:", "Edad:", "Fecha Nacimiento:", "Correo:"};
        int startY = 150;

        for (String texto : textos) {
            JLabel label = new JLabel(texto);
            label.setFont(labelFont);
            label.setSize(150, 20);
            label.setLocation(20, startY);
            frame.add(label);
            startY += 30;
        }
        
        // Porque dio webis acomodar bien 
        int posX = frame.getWidth() - botonReventa.getWidth() - 200;
        
        // Boton de la reventa a pesar de que revender es ilegal :0
        botonReventa.setFont(new Font("Arial", Font.BOLD, 14));
        botonReventa.setBackground(new Color(0x65, 0x55, 0x8F)); 
        botonReventa.setForeground(Color.BLACK);
        botonReventa.setSize(160, 40);
        botonReventa.setLocation(posX, 130);
        
        // boton del historial locochon
        botonHistorial.setFont(new Font("Arial", Font.BOLD, 14));
        botonHistorial.setBackground(new Color(0x65, 0x55, 0x8F)); 
        botonHistorial.setForeground(Color.BLACK);
        botonHistorial.setSize(120, 40);
        botonHistorial.setLocation(posX, 200);
        
        // Boton de saldo para el pobreton ese :(
        botonSaldo.setFont(new Font("Arial", Font.BOLD, 14));
        botonSaldo.setBackground(new Color(0x65, 0x55, 0x8F)); 
        botonSaldo.setForeground(Color.BLACK);
        botonSaldo.setSize(160, 40);
        botonSaldo.setLocation(posX, 270);
        
        // ActionListeners los que hacen el aca
        botonRegresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                
                frame.dispose(); 
            }
        });

        botonReventa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        botonHistorial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        botonSaldo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        
        // Agregarlos y ya :)
        frame.add(TicketWizard);
        frame.add(botonRegresar);
        frame.add(botonReventa);
        frame.add(botonHistorial);
        frame.add(botonSaldo);
        // Que se vea la wea esta
        frame.setVisible(true);
    }

}
