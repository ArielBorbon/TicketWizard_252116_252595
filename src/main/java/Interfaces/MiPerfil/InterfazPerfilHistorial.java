
package Interfaces.MiPerfil;

import Entidades.Persona;
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
private Persona personaChida;



    public InterfazPerfilHistorial(Persona personachila) {
        this.personaChida = personachila;

        JFrame frame = new JFrame("Historial");
        frame.setSize(630, 477);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
        

        int posX = frame.getWidth() - botonHistorialTransacciones.getWidth() - 375;
        

        botonHistorialTransacciones.setFont(new Font("Arial", Font.BOLD, 14));
        botonHistorialTransacciones.setBackground(new Color(0x65, 0x55, 0x8F)); 
        botonHistorialTransacciones.setForeground(Color.BLACK);
        botonHistorialTransacciones.setSize(160, 40);
        botonHistorialTransacciones.setLocation(posX, 150);
        

        botonHistorialBoletos.setFont(new Font("Arial", Font.BOLD, 14));
        botonHistorialBoletos.setBackground(new Color(0x65, 0x55, 0x8F)); 
        botonHistorialBoletos.setForeground(Color.BLACK);
        botonHistorialBoletos.setSize(160, 40);
        botonHistorialBoletos.setLocation(posX, 215);
        

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
                InterfazHistorialTransaccion iHT = new InterfazHistorialTransaccion(personachila);
                iHT.setVisible(true);
                frame.dispose();
            }
        });

        botonHistorialBoletos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InterfazHistorialBoletos iHB = new InterfazHistorialBoletos(personachila);
                iHB.setVisible(true);
                frame.dispose();
            }
        });

        botonHistorialReventas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InterfazHistorialReventas iHR = new InterfazHistorialReventas(personachila);
                iHR.setVisible(true);
                frame.dispose();
            }
        });
        

        frame.add(TicketWizard);
        frame.add(botonRegresar);
        frame.add(botonHistorialTransacciones);
        frame.add(botonHistorialBoletos);
        frame.add(botonHistorialReventas); 
        

        
        frame.setVisible(true);
    }
    
}