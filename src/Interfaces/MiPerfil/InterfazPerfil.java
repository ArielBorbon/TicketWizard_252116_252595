/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Interfaces.MiPerfil;

import Entidades.Persona;
import Interfaces.AgregarSaldo.CantidadFondosFrame;
import Interfaces.Reventa.SeleccionarBoletoR;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author Ariel Eduardo Borbon Izaguirre 252116 
 * Alberto Jimenez Garcia 252595
 */
public class InterfazPerfil extends JFrame {
    private Persona personaChida; // Asegúrate de que este objeto sea pasado en el constructor

    public InterfazPerfil(Persona personachila) {
        this.personaChida = personachila;  // Recibimos el objeto Persona

        // Configuración básica de la ventana (JFrame)
        setTitle("Perfil");
        setSize(630, 477);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Crear y agregar los componentes
        JLabel TicketWizard = new JLabel("TicketWizard");
        JButton botonRegresar = new JButton("Regresar");
        JButton botonReventa = new JButton("Reventa Boleto");
        JButton botonHistorial = new JButton("Historial");
        JButton botonSaldo = new JButton("Agregar Saldo");

        // Título de la aplicación (TicketWizard)
        TicketWizard.setFont(new Font("Racing Sans One", Font.BOLD, 40));
        TicketWizard.setForeground(Color.BLUE);
        TicketWizard.setBounds(20, 10, 600, 70);

        // Botón regresar
        botonRegresar.setBackground(new Color(0xEC, 0x22, 0x1F));
        botonRegresar.setForeground(Color.WHITE);
        botonRegresar.setFont(new Font("Arial", Font.BOLD, 12));
        botonRegresar.setSize(90, 36);
        botonRegresar.setLocation(getWidth() - botonRegresar.getWidth() - 35, 30);

        // Fuente para las etiquetas de información del usuario
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

        // Crear y agregar etiquetas con los datos del usuario
        for (int i = 0; i < textos.length; i++) {
            JLabel label = new JLabel(textos[i] + " " + textosDatosUsuario[i]);
            label.setFont(labelFont);
            label.setSize(300, 20);
            label.setLocation(20, startY);
            add(label);  // Se agrega al JFrame
            startY += 30;
        }

        // Posición de los botones en el JFrame
        int posX = getWidth() - botonReventa.getWidth() - 200;

        // Botón de reventa
        botonReventa.setFont(new Font("Arial", Font.BOLD, 14));
        botonReventa.setBackground(new Color(0x65, 0x55, 0x8F));
        botonReventa.setForeground(Color.BLACK);
        botonReventa.setSize(160, 40);
        botonReventa.setLocation(posX, 130);

        // Botón de historial
        botonHistorial.setFont(new Font("Arial", Font.BOLD, 14));
        botonHistorial.setBackground(new Color(0x65, 0x55, 0x8F));
        botonHistorial.setForeground(Color.BLACK);
        botonHistorial.setSize(120, 40);
        botonHistorial.setLocation(posX, 200);

        // Botón de agregar saldo
        botonSaldo.setFont(new Font("Arial", Font.BOLD, 14));
        botonSaldo.setBackground(new Color(0x65, 0x55, 0x8F));
        botonSaldo.setForeground(Color.BLACK);
        botonSaldo.setSize(160, 40);
        botonSaldo.setLocation(posX, 270);

        // ActionListeners para cada botón
        botonRegresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();  // Cierra la ventana
            }
        });

        botonReventa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SeleccionarBoletoR pantallaReventa = new SeleccionarBoletoR(personaChida);
                pantallaReventa.setVisible(true);
                dispose();
            }
        });

        botonHistorial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InterfazPerfilHistorial interfazPerfilHistorial = new InterfazPerfilHistorial(personachila);
                interfazPerfilHistorial.setVisible(true);
                dispose();  // Cierra la ventana actual
            }
        });

        botonSaldo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CantidadFondosFrame fondosFrame = new CantidadFondosFrame(personaChida);
        fondosFrame.setVisible(true);
            }
        });

        // Agregar los componentes al JFrame
        add(TicketWizard);
        add(botonRegresar);
        add(botonReventa);
        add(botonHistorial);
        add(botonSaldo);

        // Hacer visible el JFrame
        setVisible(true);
    }
}
