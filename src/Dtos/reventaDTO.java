/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dtos;
import java.time.LocalDate;
/**
 *
 * @author Ariel Eduardo Borbon Izaguirre 252116
 * Alberto Jimenez Garcia 252595
 */

public class reventaDTO {
    private int boletoId;
    private double precioReventa;
    private LocalDate fechaLimite;
    private String estado;
    private String vendedor; 

    public reventaDTO() {
    }

    public reventaDTO(int boletoId, double precioReventa, LocalDate fechaLimite, String estado, String vendedor) {
        this.boletoId = boletoId;
        this.precioReventa = precioReventa;
        this.fechaLimite = fechaLimite;
        this.estado = estado;
        this.vendedor = vendedor;
    }

    public int getBoletoId() {
        return boletoId;
    }

    public void setBoletoId(int boletoId) {
        this.boletoId = boletoId;
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

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    
    
    
}
