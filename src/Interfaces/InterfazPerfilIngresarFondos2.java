/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Interfaces;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Alberto Jimenez
 */
public class InterfazPerfilIngresarFondos2 extends JFrame{
    private static InterfazPerfilIngresarFondos2 instancia;
    // Método simulado para procesar el pago
    private static boolean procesarPago() {
        // Simulación de éxito o fallo (50% de probabilidad)
        return Math.random() > 0.2; // 80% de éxito, 20% de fallo
    }
    public static InterfazPerfilIngresarFondos2 getInstance() {
        if (instancia == null) {
            instancia = new InterfazPerfilIngresarFondos2();
        }
        return instancia;
    }
    public InterfazPerfilIngresarFondos2() {
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
        
        JLabel texto1 = new JLabel("Los fondos seran restados de tu tarjeta de credito, ¿continuar?");
        texto1.setFont(new Font("Inter", Font.BOLD, 16));
        texto1.setBounds(20, 120, 600, 70);
        
        frame.add(texto1);
        
        JButton confirmar = new JButton("Confirmar");
        confirmar.setBackground(new Color(0x14, 0xAE, 0x5C));
        confirmar.setForeground(Color.WHITE);
        confirmar.setFont(new Font("Arial", Font.BOLD, 12));
        confirmar.setSize(100, 36);
        confirmar.setLocation(frame.getWidth() - confirmar.getWidth() - 35, 350);
        
        confirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                
                try {
                    // Simulación de una transacción exitosa
                    boolean transaccionExitosa = procesarPago(); 
                    
                    if (transaccionExitosa) {
                        JOptionPane.showMessageDialog(frame, 
                            "Transacción realizada con éxito", 
                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(frame, 
                            "No se ha podido realizar la transacción", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, 
                        "Ha ocurrido un error inesperado", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        frame.add(confirmar);
        
        frame.setVisible(true);
    }
    
}
