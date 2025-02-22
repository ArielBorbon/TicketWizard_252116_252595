/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces.Reventa;

import Control.ControlReventa;
import Entidades.Boleto;
import Entidades.Evento;
import Entidades.Persona;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author PC Gamer
 */

public class ConfirmarReventaFrame extends JFrame {
    private Persona personaChida;
    private Boleto boletoChido;
    private double comisionSeleccionada;
    
    // Componentes de la interfaz
    private JLabel lblEvento;
    private JLabel lblNumSerie;
    private JLabel lblPrecioReventa;
    private JButton btnContinuar;
    
    public ConfirmarReventaFrame(Persona personaChida, Boleto boletoChido, double comisionSeleccionada) {
        this.personaChida = personaChida;
        this.boletoChido = boletoChido;
        this.comisionSeleccionada = comisionSeleccionada;
        
        configurarVentana();
        initComponentes();
    }
    
    private void configurarVentana() {
        setTitle("Confirmar Reventa");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1, 10, 10));
    }
    
    private void initComponentes() {
        // Calcular el precio de reventa: precioOriginal + comisión
        double precioOriginal = boletoChido.getPrecioOriginal();
        double precioReventa = precioOriginal * (1 + comisionSeleccionada);
        DecimalFormat df = new DecimalFormat("#.00");
        
        // Obtener el nombre del evento del boleto. Se asume que boletoChido tiene método getEvento()
        String eventoNombre = (boletoChido.getEvento() != null) ? boletoChido.getEvento().getNombre() : "Desconocido";
        
        lblEvento = new JLabel("Evento: " + eventoNombre);
        lblNumSerie = new JLabel("N° de Serie: " + boletoChido.getNumSerie());
        lblPrecioReventa = new JLabel("Precio de Reventa: $" + df.format(precioReventa));
        
        btnContinuar = new JButton("Continuar");
        btnContinuar.addActionListener(this::accionContinuar);
        
        add(lblEvento);
        add(lblNumSerie);
        add(lblPrecioReventa);
        add(btnContinuar);
    }
    
    private void accionContinuar(ActionEvent e) {
        // Crear lista de IDs con el boleto seleccionado
        List<Integer> boletosIds = new ArrayList<>();
        boletosIds.add(boletoChido.getBoletoId());
        
        // Definir la fecha límite para la reventa.
        // Aquí la establecemos como la fecha actual. Si en el futuro se desea aplicar un plazo,
        // se puede modificar para sumar minutos o lo que se requiera.
        Date fechaLimite = new Date();
        
        // Llamar al control de reventa para publicar la reventa
        ControlReventa control = new ControlReventa();
        double precioOriginal = boletoChido.getPrecioOriginal();
        double precioReventa = precioOriginal * (1 + comisionSeleccionada);
        boolean exito = control.publicarEnReventa(personaChida.getPersonaId(), boletosIds, precioReventa, fechaLimite);
        
        if (exito) {
            JOptionPane.showMessageDialog(this,
                    "Reventa publicada exitosamente.\n" +
                    lblEvento.getText() + "\n" +
                    lblNumSerie.getText() + "\n" +
                    lblPrecioReventa.getText(),
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Error al publicar la reventa.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
}

