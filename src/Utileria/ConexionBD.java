package Utileria;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author Ariel Eduardo Borbon Izaguirre 252116
 * Alberto Jimenez Garcia 252595
 */


public class ConexionBD {

    private static final String cadenaConexion = "jdbc:mysql://localhost/ticketwizard";
    private static final String usuario = "root";
    private static final String contrasenia = "Ariel777";

    /*
    boletera         password123
    juanperez        password456
    mariagarcia      password789
    pedrolopez       password101
    anamartinez      password202 
    carlosrodriguez  password303

    */
    
    
    
    
    public ConexionBD() {
    }
    
    public static Connection crearConexion() throws SQLException {
        return DriverManager.getConnection(cadenaConexion, usuario, contrasenia);
    }

    
    
}