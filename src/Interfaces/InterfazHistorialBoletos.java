/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Ariel Eduardo Borbon Izaguirre 252116
 * Alberto Jimenez Garcia 252595
 */
public class InterfazHistorialBoletos extends JFrame{
    public InterfazHistorialBoletos() {
        // JFrame
        JFrame frame = new JFrame("Historial Boletos");
        frame.setSize(630, 477);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        // Todo lo que se utiliza dentro del JFrame
        JLabel TicketWizard = new JLabel("TicketWizard");
        JButton botonRegresar = new JButton("Regresar");
        JLabel texto1 = new JLabel("Registro historial de boletos");
        JLabel texto2 = new JLabel("Historial: ");

        // Nombre de la app
        TicketWizard.setFont(new Font("Racing Sans One", Font.BOLD, 58));
        TicketWizard.setForeground(Color.BLUE);
        TicketWizard.setBounds(20, 10, 600, 70);

        // Solo es el formato para que se vean bonitos
        botonRegresar.setBackground(new Color(0xEC, 0x22, 0x1F));
        botonRegresar.setForeground(Color.WHITE);
        botonRegresar.setFont(new Font("Arial", Font.BOLD, 12));
        botonRegresar.setSize(90, 36);
        botonRegresar.setLocation(frame.getWidth() - botonRegresar.getWidth() - 35, 30);

        // Textos para dar a entender que es
        texto1.setFont(new Font("Inter", Font.BOLD, 16));
        texto2.setFont(new Font("Inter", Font.BOLD, 16));
        texto1.setBounds(20, 50, 600, 70);
        texto2.setBounds(20, 150, 600, 70);

        // El combo box D:
        String[] transacciones = {"Transacción 1", "Transacción 2", "Transacción 3"}; // Ejemplo de datos
        JComboBox<String> historialTransacciones = new JComboBox<>(transacciones);
        historialTransacciones.setBounds(20, 210, 500, 30);

        // Boton
        botonRegresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        // Añadir todo al frame
        frame.add(TicketWizard);
        frame.add(botonRegresar);
        frame.add(texto1);
        frame.add(texto2);
        frame.add(historialTransacciones);

        // Visible :)
        frame.setVisible(true);
    }
}
