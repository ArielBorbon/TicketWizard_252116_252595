
package Interfaces;

import Control.ControlCompra;
import Daos.transaccionDAO;
import Entidades.Persona;
import Entidades.Transaccion;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.sql.*;

/**
 *
 * @author Ariel Eduardo Borbon Izaguirre 252116
 * Alberto Jimenez Garcia 252595
 */
public class CompletarCompraPendienteFrame extends JFrame {
    private JComboBox<Transaccion> comboTransacciones;
    private Persona usuario;
    private transaccionDAO transaccionDAO = new transaccionDAO();

    public CompletarCompraPendienteFrame(Persona usuario) {
        this.usuario = usuario;
        setTitle("Completar Compra Pendiente");
        setSize(400, 200);
        cargarTransaccionesPendientes();
        initComponents();
    }

    private void cargarTransaccionesPendientes() {
        try {
            List<Transaccion> transacciones = transaccionDAO.obtenerTransaccionesPendientes(usuario.getPersonaId());
            DefaultComboBoxModel<Transaccion> model = new DefaultComboBoxModel<>();
            
            for (Transaccion t : transacciones) {
                model.addElement(t);
            }
            
            comboTransacciones.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar transacciones");
        }
    }

    private void initComponents() {
        comboTransacciones = new JComboBox<>();
        JButton btnCompletar = new JButton("Completar Compra");
        
        btnCompletar.addActionListener(e -> {
            Transaccion seleccionada = (Transaccion) comboTransacciones.getSelectedItem();
            if (seleccionada != null) {
                try {
                    boolean exito = new ControlCompra().completarCompraPendiente(seleccionada.getTransaccionId());
                    
                    if (exito) {
                        JOptionPane.showMessageDialog(this, "Compra completada!");
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "No se pudo completar. La reserva pudo haber expirado.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

    }
}