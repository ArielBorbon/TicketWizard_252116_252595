/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Interfaces;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 *
 * @author Alberto Jimenez
 */
public class InterfazPerfilRevender3 extends JFrame{
    
    private static InterfazPerfilRevender3 instancia;
    
    // Método simulado para procesar la venta
    private static boolean procesarVenta() {
        return Math.random() > 0.2; 
    }
    
    public InterfazPerfilRevender3() {
        // JFrame
        JFrame frame = new JFrame("Revender boletos");
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

        JLabel texto1 = new JLabel("¿Seguro que deseas Seguir con la Transaccion?");
        JLabel texto2 = new JLabel("Mis boletos: ");

        texto1.setFont(new Font("Inter", Font.BOLD, 16));
        texto2.setFont(new Font("Inter", Font.BOLD, 16));
        texto1.setBounds(20, 50, 600, 70);
        texto2.setBounds(20, 150, 600, 70);

        // El combo box D:
        String[] transacciones = {"Transacción 1", "Transacción 2", "Transacción 3"}; // Ejemplo de datos
        JComboBox<String> boletos = new JComboBox<>(transacciones);
        boletos.setBounds(20, 210, 500, 30);

        // Boton
        botonRegresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        JButton confirmar = new JButton("Confirmar");
        confirmar.setBackground(new Color(0x14, 0xAE, 0x5C));
        confirmar.setForeground(Color.WHITE);
        confirmar.setFont(new Font("Arial", Font.BOLD, 12));
        confirmar.setSize(100, 36);
        confirmar.setLocation(frame.getWidth() - confirmar.getWidth() - 35, 200);

        confirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                // Simulación de venta
                boolean ventaExitosa = procesarVenta();
                
                if (ventaExitosa) {
                    JOptionPane.showMessageDialog(frame, 
                        "El boleto se ha puesto en venta con éxito.", 
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, 
                        "No se pudo completar la venta.", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, 
                    "Por favor, ingresa un número válido.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            } catch (HeadlessException ex) {
                JOptionPane.showMessageDialog(frame, 
                    ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
            }
        });

        

        frame.setVisible(true);

        frame.add(texto1);
        frame.add(texto2);
        frame.add(boletos);
        
        // Aquí es donde se puede escribir el monto de dinero
        JTextArea agregarSaldo = new JTextArea();
        agregarSaldo.setFont(new Font("Arial", Font.PLAIN, 14));
        agregarSaldo.setBounds(399, 180, 100, 30);
        
        frame.add(agregarSaldo);
        frame.add(confirmar);
        frame.setVisible(true);
    }
    public static InterfazPerfilRevender3 getInstance() {
        if (instancia == null) {
            instancia = new InterfazPerfilRevender3();
        }
        return instancia;
    }
    
}
