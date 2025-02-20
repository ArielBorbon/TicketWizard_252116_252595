package Pruebas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PruebaGUI extends JFrame {

    public PruebaGUI() {
 
        setTitle("TicketWizard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null); 
       
        JPanel fondoPanel = new JPanel();
        fondoPanel.setBackground(Color.WHITE); 
        fondoPanel.setBounds(0, 0, 800, 600); 
        fondoPanel.setLayout(null); 
        add(fondoPanel); 
           
        JLabel titulo = new JLabel("TICKETWIZARD");
        titulo.setForeground(Color.BLUE);
        titulo.setFont(new Font("Arial", Font.BOLD, 90));
        titulo.setBounds(20, 20, 700, 100); 
        add(titulo);
        fondoPanel.add(titulo); 
        
        JButton btnComprar = new JButton("Comprar Boletos");
        btnComprar.setBounds(550, 250, 200, 50); // Centro derecha
        btnComprar.setBackground(new Color(0, 120, 215));
        btnComprar.setForeground(Color.WHITE);
        btnComprar.setFocusPainted(false);
        add(btnComprar);
        fondoPanel.add(btnComprar);
        setBackground(Color.WHITE);

        
        btnComprar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
        
                JFrame nuevaVentana = new JFrame("TicketWizard - Comprar Boletos");
                nuevaVentana.setSize(800, 600);
                nuevaVentana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                nuevaVentana.setLocationRelativeTo(null);
                nuevaVentana.setLayout(null); 

        
                JLabel nuevoTitulo = new JLabel("TICKETWIZARD");
                nuevoTitulo.setForeground(Color.BLUE);
                nuevoTitulo.setFont(new Font("Arial", Font.BOLD, 72));
                nuevoTitulo.setBounds(20, 20, 700, 100); 
                nuevaVentana.add(nuevoTitulo);

           
                nuevaVentana.getContentPane().setBackground(Color.GRAY);

            
                    JButton btnRegresar = new JButton("Regresar");
                    btnRegresar.setBounds(600, 20, 150, 50); 
                    btnRegresar.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            nuevaVentana.dispose(); 
                        }
                    });
                    nuevaVentana.add(btnRegresar); 
                
             
                nuevaVentana.setVisible(true);
            }
        });








   
        JButton btnPerfil = new JButton("Mi perfil");
        btnPerfil.setBounds(550, 450, 200, 50); 
        btnPerfil.setBackground(new Color(50, 50, 50));
        btnPerfil.setForeground(Color.WHITE);
        btnPerfil.setFocusPainted(false);
        add(btnPerfil);
    fondoPanel.add(btnPerfil);
     
        ImageIcon imagen = new ImageIcon("Boletos.png"); 
        Image imgEscalada = imagen.getImage().getScaledInstance(300, 400, Image.SCALE_SMOOTH); 
        JLabel imagenpng = new JLabel(new ImageIcon(imgEscalada));
        imagenpng.setBounds(100, 150, 300, 400);
        add(imagenpng);
fondoPanel.add(imagenpng);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PruebaGUI());
    }
}