
package Interfaces.MiPerfil;

import Entidades.Persona;
import Interfaces.AgregarSaldo.CantidadFondosFrame;
import Interfaces.Reventa.SeleccionarBoletoR;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author Ariel Eduardo Borbon Izaguirre 252116 
 * Alberto Jimenez Garcia 252595
 */
public class InterfazPerfil extends JFrame {
    private Persona personaChida; 

    public InterfazPerfil(Persona personachila) {
        this.personaChida = personachila;  

     
        setTitle("Perfil");
        setSize(630, 477);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

  
        JLabel TicketWizard = new JLabel("TicketWizard");
        JButton botonRegresar = new JButton("Regresar");
        JButton botonReventa = new JButton("Reventa Boleto");
        JButton botonHistorial = new JButton("Historial");
        JButton botonSaldo = new JButton("Agregar Saldo");

   
        TicketWizard.setFont(new Font("Racing Sans One", Font.BOLD, 40));
        TicketWizard.setForeground(Color.BLUE);
        TicketWizard.setBounds(20, 10, 600, 70);

     
        botonRegresar.setBackground(new Color(0xEC, 0x22, 0x1F));
        botonRegresar.setForeground(Color.WHITE);
        botonRegresar.setFont(new Font("Arial", Font.BOLD, 12));
        botonRegresar.setSize(90, 36);
        botonRegresar.setLocation(getWidth() - botonRegresar.getWidth() - 35, 30);

      
        Font labelFont = new Font("Arial", Font.BOLD, 12);
        String[] textos = {"Usuario:", "Nombre:", "Edad:", "Fecha Nacimiento:", "Correo:"};
        String[] textosDatosUsuario = {
            personaChida.getUsuario(),
            personaChida.getNombreCompleto(),
            String.valueOf(personaChida.getEdad()),
            personaChida.getFechaNacimiento().toString(),
            personaChida.getCorreo()
        };
        int startY = 150;

   
        for (int i = 0; i < textos.length; i++) {
            JLabel label = new JLabel(textos[i] + " " + textosDatosUsuario[i]);
            label.setFont(labelFont);
            label.setSize(300, 20);
            label.setLocation(20, startY);
            add(label); 
            startY += 30;
        }

  
        int posX = getWidth() - botonReventa.getWidth() - 200;

      
        botonReventa.setFont(new Font("Arial", Font.BOLD, 14));
        botonReventa.setBackground(new Color(0x65, 0x55, 0x8F));
        botonReventa.setForeground(Color.BLACK);
        botonReventa.setSize(160, 40);
        botonReventa.setLocation(posX, 130);

      
        botonHistorial.setFont(new Font("Arial", Font.BOLD, 14));
        botonHistorial.setBackground(new Color(0x65, 0x55, 0x8F));
        botonHistorial.setForeground(Color.BLACK);
        botonHistorial.setSize(120, 40);
        botonHistorial.setLocation(posX, 200);

      
        botonSaldo.setFont(new Font("Arial", Font.BOLD, 14));
        botonSaldo.setBackground(new Color(0x65, 0x55, 0x8F));
        botonSaldo.setForeground(Color.BLACK);
        botonSaldo.setSize(160, 40);
        botonSaldo.setLocation(posX, 270);

       
        botonRegresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();  
            }
        });

        botonReventa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    SeleccionarBoletoR pantallaReventa = new SeleccionarBoletoR(personaChida);
                    pantallaReventa.setVisible(true);
                    dispose();
                } catch (SQLException ex) {
                    Logger.getLogger(InterfazPerfil.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        botonHistorial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InterfazPerfilHistorial interfazPerfilHistorial = new InterfazPerfilHistorial(personachila);
                interfazPerfilHistorial.setVisible(true);
                dispose();  
            }
        });

        botonSaldo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CantidadFondosFrame fondosFrame = new CantidadFondosFrame(personaChida);
        fondosFrame.setVisible(true);
            }
        });


        add(TicketWizard);
        add(botonRegresar);
        add(botonReventa);
        add(botonHistorial);
        add(botonSaldo);


        setVisible(true);
    }
}