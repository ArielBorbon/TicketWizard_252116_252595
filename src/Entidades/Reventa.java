
package Entidades;

import java.time.LocalDate;



/**
 *
 * @author Ariel Eduardo Borbon Izaguirre 252116
 * Alberto Jimenez Garcia 252595
 */
public class Reventa {
    private int reventaId;
    private double precioReventa;
    private LocalDate fechaLimite;
    private String estado;
    private int boletoId;
    private int personaIdVendedor;

    public Reventa() {
    }

    public Reventa(int reventaId, double precioReventa, LocalDate fechaLimite, String estado, int boletoId, int personaIdVendedor) {
        this.reventaId = reventaId;
        this.precioReventa = precioReventa;
        this.fechaLimite = fechaLimite;
        this.estado = estado;
        this.boletoId = boletoId;
        this.personaIdVendedor = personaIdVendedor;
    }

    
    
    
    
    public int getReventaId() {
        return reventaId;
    }

    public void setReventaId(int reventaId) {
        this.reventaId = reventaId;
    }

    public double getPrecioReventa() {
        return precioReventa;
    }

    public void setPrecioReventa(double precioReventa) {
        this.precioReventa = precioReventa;
    }

    public LocalDate getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(LocalDate fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getBoletoId() {
        return boletoId;
    }

    public void setBoletoId(int boletoId) {
        this.boletoId = boletoId;
    }

    public int getPersonaIdVendedor() {
        return personaIdVendedor;
    }

    public void setPersonaIdVendedor(int personaIdVendedor) {
        this.personaIdVendedor = personaIdVendedor;
    }
    
    
    
    
    
    
    
}
