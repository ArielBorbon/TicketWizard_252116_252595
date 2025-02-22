
package Entidades;

import java.time.LocalDate;
import java.time.LocalDateTime;



/**
 *
 * @author Ariel Eduardo Borbon Izaguirre 252116
 * Alberto Jimenez Garcia 252595
 */
public class Transaccion {
    private int transaccionId;
    private String numTransaccion;
    private LocalDate fechaHora;
    private String tipo = "compra_directa"; // Valor por defecto
    private double montoTotal;
    private double comision;
    private String estado;
   private LocalDateTime fechaExpiracion;
    private int personaId;

    public Transaccion() {
    }

    public Transaccion(int transaccionId, String numTransaccion, LocalDate fechaHora, String tipo, double montoTotal, double comision, String estado, LocalDateTime fechaExpiracion, int personaId) {
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
        this.tipo = tipo != null ? tipo : "compra_directa"; // Evita asignar null
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

    public LocalDateTime getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(LocalDateTime fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public int getPersonaId() {
        return personaId;
    }

    public void setPersonaId(int personaId) {
        this.personaId = personaId;
    }
    
    
    
    
    
    
    
}
