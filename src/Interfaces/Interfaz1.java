
package Interfaces;

import Daos.transaccionDAO;
import Interfaces.ComprarBoleto.SeleccionarFiltros;
import Interfaces.MiPerfil.InterfazPerfil;
import Entidades.Persona;
import Entidades.Transaccion;
import java.awt.Color;
import java.awt.Font;
import java.util.List;
import javax.swing.*;
import java.sql.*;
/**
 *
 * @author Ariel Eduardo Borbon Izaguirre 252116
 * Alberto Jimenez Garcia 252595
 */
public class Interfaz1 extends javax.swing.JFrame {
Persona personachida;

    public Interfaz1(Persona personachida) {
        this.personachida = personachida;
        verificarComprasPendientes();
        initComponents();
        
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        botonComprarBoletos = new javax.swing.JButton();
        botonMiPerfil = new javax.swing.JButton();
        menuTexto = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Seleccionar Evento");

        botonComprarBoletos.setText("Comprar Boleto");
        botonComprarBoletos.setBackground(new Color(0x65, 0x55, 0x8F)); // Color #65558F (RGB)
        botonComprarBoletos.setForeground(Color.BLACK);
        botonComprarBoletos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonComprarBoletosActionPerformed(evt);
            }
        });

        botonMiPerfil.setText("Mi Perfil");
        botonMiPerfil.setBackground(new Color(0x65, 0x55, 0x8F)); // Color #65558F (RGB)
        botonMiPerfil.setForeground(Color.BLACK);
        botonMiPerfil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonMiPerfilActionPerformed(evt);
            }
        });

        menuTexto.setText("MENU");
        menuTexto.setFont(new Font("Arial", Font.BOLD, 20));

        jLabel1.setText("TicketWizard");
        jLabel1.setFont(new Font("Racing Sans One", Font.BOLD, 58)); // Cambia la fuente, estilo y tamaño
        jLabel1.setForeground(Color.BLUE);

        ImageIcon icono = new ImageIcon("1c8e47f7d9b0cc60689a1016c4cd4b5e.png");
        JLabel jLabel2 = new JLabel(icono);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(botonMiPerfil, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(botonComprarBoletos, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE))
                        .addGap(62, 62, 62))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(menuTexto, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
                .addGap(30, 30, 30)
                .addComponent(menuTexto, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(botonComprarBoletos, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(92, 92, 92)
                        .addComponent(botonMiPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(72, 72, 72))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonComprarBoletosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonComprarBoletosActionPerformed

        
        SeleccionarFiltros seleccionarFiltros = new SeleccionarFiltros(personachida);
        seleccionarFiltros.setVisible(true);

    }//GEN-LAST:event_botonComprarBoletosActionPerformed

    private void botonMiPerfilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonMiPerfilActionPerformed
        
        
                InterfazPerfil interfazPerfil = new InterfazPerfil(personachida);
                 interfazPerfil.setVisible(true);
        
        
        
    }//GEN-LAST:event_botonMiPerfilActionPerformed

  
    private void verificarComprasPendientes() {
        try {
            List<Transaccion> pendientes = new transaccionDAO().obtenerTransaccionesPendientes(personachida.getPersonaId());
            
            if (!pendientes.isEmpty()) {
                JOptionPane.showMessageDialog(
                    null, 
                    "Tienes " + pendientes.size() + " Compras pendientes.. Favor de añadir Fondos para continuar con estas",
                    "Compras Pendientes",
                    JOptionPane.WARNING_MESSAGE
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonComprarBoletos;
    private javax.swing.JButton botonMiPerfil;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel menuTexto;
    // End of variables declaration//GEN-END:variables
}
