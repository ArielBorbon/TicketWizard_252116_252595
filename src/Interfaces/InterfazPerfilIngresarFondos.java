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

public class InterfazPerfilIngresarFondos extends JFrame{
    
    private static InterfazPerfilIngresarFondos instancia;
    
    public InterfazPerfilIngresarFondos() {
        // JFrame
        JFrame frame = new JFrame("Agregar fondos");
        frame.setSize(630, 477);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        
        // Todo lo que se utiliza dentro del JFrame
        JLabel TicketWizard = new JLabel("TicketWizard");
        JButton botonRegresar = new JButton("Regresar");
        
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
        
        // Aún faltan las acciones de cada botón para conectarlos al principal
        botonRegresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                
                frame.dispose(); 
            }
        });
        
        // Agregarlos y ya :)
        frame.add(TicketWizard);
        frame.add(botonRegresar);
        
        JLabel texto1 = new JLabel("Ingresar los fondos que deseas ingresar");
        texto1.setFont(new Font("Inter", Font.BOLD, 16));
        texto1.setBounds(20, 120, 600, 70);
        
        frame.add(texto1);
        
        // Aquí es donde se puede escribir el monto de dinero
        JTextArea agregarSaldo = new JTextArea();
        agregarSaldo.setFont(new Font("Arial", Font.PLAIN, 14));
        agregarSaldo.setBounds(20, 180, 100, 30);
        
        frame.add(agregarSaldo);
        
        JButton continuar = new JButton("Continuar");
        continuar.setBackground(new Color(0x2C, 0x2C, 0x2C));
        continuar.setForeground(Color.WHITE);
        continuar.setFont(new Font("Arial", Font.BOLD, 12));
        continuar.setSize(100, 36);
        continuar.setLocation(frame.getWidth() - continuar.getWidth() - 35, 350);
        
        continuar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                
                InterfazPerfilIngresarFondos2.getInstance().setVisible(true);
                frame.dispose();
            }
        });
        
        frame.add(continuar);
        
        frame.setVisible(true);
    }
    public static InterfazPerfilIngresarFondos getInstance() {
        if (instancia == null) {
            instancia = new InterfazPerfilIngresarFondos();
        }
        return instancia;
    }
    
}
