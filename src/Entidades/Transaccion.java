
package Entidades;

import java.time.LocalDate;



/**
 *
 * @author Ariel Eduardo Borbon Izaguirre 252116
 * Alberto Jimenez Garcia 252595
 */
public class Transaccion {
    private int transaccionId;
    private String numTransaccion;
    private LocalDate fechaHora;
    private String tipo;
    private double montoTotal;
    private double comision;
    private String estado;
    private LocalDate fechaExpiracion;
    private int personaId;

    public Transaccion() {
    }

    public Transaccion(int transaccionId, String numTransaccion, LocalDate fechaHora, String tipo, double montoTotal, double comision, String estado, LocalDate fechaExpiracion, int personaId) {
        this.transaccionId = transaccionId;
        this.numTransaccion = numTransaccion;
        this.fechaHora = fechaHora;
        this.tipo = tipo;
        this.montoTotal = montoTotal;
        this.comision = comision;
        this.estado = estado;
        this.fechaExpiracion = fechaExpiracion;
        this.personaId = personaId;
    }

    public int getTransaccionId() {
        return transaccionId;
    }

    public void setTransaccionId(int transaccionId) {
        this.transaccionId = transaccionId;
    }

    public String getNumTransaccion() {
        return numTransaccion;
    }

    public void setNumTransaccion(String numTransaccion) {
        this.numTransaccion = numTransaccion;
    }

    public LocalDate getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDate fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public double getComision() {
        return comision;
    }

    public void setComision(double comision) {
        this.comision = comision;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDate getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(LocalDate fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public int getPersonaId() {
        return personaId;
    }

    public void setPersonaId(int personaId) {
        this.personaId = personaId;
    }
    
    
    
    
    
    
    
}
