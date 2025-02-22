/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces.ComprarBoleto;

/**
 *
 * @author PC Gamer
 */
import javax.swing.*;
import java.awt.*;
import Entidades.Boleto;
import Entidades.Evento;
import Entidades.Persona;
import control.ControlCompra;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfirmarCompraFrame extends JFrame {
    private Evento eventoChido;
    private Persona personaChida;
    private Boleto boletoChido;

    
    public ConfirmarCompraFrame(Evento eventoChido, Persona personaChida, Boleto boletoChido) {
        this.eventoChido = eventoChido;
        this.personaChida = personaChida;
        this.boletoChido = boletoChido;
        
        configurarVentana();
        initComponentes();
    }
    
    
    
        private void confirmarCompra() throws SQLException {
        ControlCompra control = new ControlCompra();
        
        // Crear lista con el ID del boleto seleccionado (CORRECTO)
        List<Integer> boletosIds = Collections.singletonList(this.boletoChido.getBoletoId());
        
        boolean exito = control.comprarBoletosDirectos(
                this.personaChida.getPersonaId(), 
                boletosIds
        );
        if (exito) {
            // Actualizar saldo localmente
            double nuevoSaldo = this.personaChida.getSaldo() - this.boletoChido.getPrecioOriginal();
            this.personaChida.setSaldo(nuevoSaldo);
            
            JOptionPane.showMessageDialog(this, 
                    "¡Compra confirmada!\nNuevo saldo: $" + this.personaChida.getSaldo(),
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Saldo insuficiente. Boleto reservado por 10 minutos.",
                    "Advertencia", 
                    JOptionPane.WARNING_MESSAGE);
        }
    }


    private void configurarVentana() {
        setTitle("Confirmar Compra");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1, 10, 10));
    }

    private void initComponentes() {
        JLabel lblEvento = new JLabel("Evento: " + eventoChido.getNombre());
        JLabel lblFila = new JLabel("Fila: " + boletoChido.getFila());
        JLabel lblAsiento = new JLabel("Asiento: " + boletoChido.getAsiento());
        JLabel lblCosto = new JLabel("Costo: $" + boletoChido.getPrecioOriginal());
        
        JButton btnConfirmar = new JButton("Confirmar Compra");
        btnConfirmar.addActionListener(e -> {
            try {
                confirmarCompra();
            } catch (SQLException ex) {
                Logger.getLogger(ConfirmarCompraFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        add(lblEvento);
        add(lblFila);
        add(lblAsiento);
        add(lblCosto);
        add(btnConfirmar);
    }


}
