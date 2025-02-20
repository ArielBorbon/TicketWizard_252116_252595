package Utileria;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String cadenaConexion = "jdbc:mysql://localhost/TicketWizard";
    private static final String usuario = "root";
    private static final String contrasenia = "Itson";

    // Método para crear y devolver una conexión
    public static Connection crearConexion() throws SQLException {
        return DriverManager.getConnection(cadenaConexion, usuario, contrasenia);
    }
}