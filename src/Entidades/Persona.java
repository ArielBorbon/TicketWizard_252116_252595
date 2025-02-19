
package Entidades;

import java.util.Date;

/**
 *
 * @author Ariel Eduardo Borbon Izaguirre 252116
 * Alberto Jimenez Garcia 252595
 */
public class Persona {
    private int personaId;
    private String correo;
    private String nombreCompleto;
    private String domicilio;
    private Date fechaNacimiento;
    private int edad;
    private int totalBoletos;
    private double saldo;
    private String usuario;
    private String contrasena;

    public Persona() {
    }

    public Persona(int personaId, String correo, String nombreCompleto, String domicilio, Date fechaNacimiento, int edad, int totalBoletos, double saldo, String usuario, String contrasena) {
        this.personaId = personaId;
        this.correo = correo;
        this.nombreCompleto = nombreCompleto;
        this.domicilio = domicilio;
        this.fechaNacimiento = fechaNacimiento;
        this.edad = edad;
        this.totalBoletos = totalBoletos;
        this.saldo = saldo;
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    public int getPersonaId() {
        return personaId;
    }

    public void setPersonaId(int personaId) {
        this.personaId = personaId;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getTotalBoletos() {
        return totalBoletos;
    }

    public void setTotalBoletos(int totalBoletos) {
        this.totalBoletos = totalBoletos;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    
    
    
    
}
