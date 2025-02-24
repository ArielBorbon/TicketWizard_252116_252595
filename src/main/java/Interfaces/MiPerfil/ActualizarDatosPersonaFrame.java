
package Interfaces.MiPerfil;

import Entidades.Persona;
import Utileria.ConexionBD;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Ariel Eduardo Borbon Izaguirre 252116
 * Alberto Jimenez Garcia 252595
 */
public class ActualizarDatosPersonaFrame extends JFrame {
    private Persona persona;
    private JTextField txtCorreo, txtNombre, txtDomicilio, txtFechaNacimiento, txtUsuario;

    public ActualizarDatosPersonaFrame(Persona persona) {
        this.persona = persona;
        configurarVentana();
        initComponentes();
        cargarDatosActuales();
    }

    private void configurarVentana() {
        setTitle("Actualizar Datos Personales");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 2, 10, 10)); // 7 filas (5 campos + 2 botones)
    }

    private void initComponentes() {
        // Campos de texto
        add(new JLabel("Correo:"));
        txtCorreo = new JTextField();
        add(txtCorreo);

        add(new JLabel("Nombre completo:"));
        txtNombre = new JTextField();
        add(txtNombre);

        add(new JLabel("Domicilio:"));
        txtDomicilio = new JTextField();
        add(txtDomicilio);

        add(new JLabel("Fecha nacimiento (YYYY-MM-DD):"));
        txtFechaNacimiento = new JTextField();
        add(txtFechaNacimiento);

        add(new JLabel("Usuario:"));
        txtUsuario = new JTextField();
        add(txtUsuario);

        // Botones
        JButton btnConfirmar = new JButton("Confirmar");
        btnConfirmar.addActionListener(e -> actualizarDatos());
        add(btnConfirmar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        add(btnCancelar);
    }

    private void cargarDatosActuales() {
        txtCorreo.setText(persona.getCorreo());
        txtNombre.setText(persona.getNombreCompleto());
        txtDomicilio.setText(persona.getDomicilio());
        txtFechaNacimiento.setText(persona.getFechaNacimiento().toString());
        txtUsuario.setText(persona.getUsuario());
    }

    private void actualizarDatos() {
        try {
            // Construir consulta dinámica
            StringBuilder sql = new StringBuilder("UPDATE Personas SET ");
            List<Object> parametros = new ArrayList<>();
            
            // Verificar campos modificados
            if (!txtCorreo.getText().isEmpty()) {
                sql.append("correo = ?, ");
                parametros.add(txtCorreo.getText());
            }
            if (!txtNombre.getText().isEmpty()) {
                sql.append("nombre_completo = ?, ");
                parametros.add(txtNombre.getText());
            }
            if (!txtDomicilio.getText().isEmpty()) {
                sql.append("domicilio = ?, ");
                parametros.add(txtDomicilio.getText());
            }
            if (!txtFechaNacimiento.getText().isEmpty()) {
                sql.append("fecha_nacimiento = ?, ");
                parametros.add(LocalDate.parse(txtFechaNacimiento.getText()));
            }
            if (!txtUsuario.getText().isEmpty()) {
                sql.append("usuario = ?, ");
                parametros.add(txtUsuario.getText());
            }
            
            // Eliminar última coma y espacio
            sql.delete(sql.length() - 2, sql.length());
            sql.append(" WHERE persona_id = ?");
            parametros.add(persona.getPersonaId());

            // Ejecutar actualización
            try (Connection conn = ConexionBD.crearConexion();
                 PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
                
                for (int i = 0; i < parametros.size(); i++) {
    Object param = parametros.get(i);
    if (param instanceof String) {
        pstmt.setString(i + 1, (String) param);
    } else if (param instanceof LocalDate) {
        pstmt.setDate(i + 1, Date.valueOf((LocalDate) param));
    } else if (param instanceof Integer) { // ¡Nueva condición para el ID!
        pstmt.setInt(i + 1, (Integer) param);
    }
}
                
                int filasAfectadas = pstmt.executeUpdate();
                
                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(this, 
                        "¡Tus Nuevos Datos se veran Reflejados la proxima vez que vuelvas!", 
                        "Éxito", 
                        JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                }
            }
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, 
                "Formato de fecha inválido. Use YYYY-MM-DD", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Error al actualizar: " + e.getMessage(), 
                "Error de BD", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
