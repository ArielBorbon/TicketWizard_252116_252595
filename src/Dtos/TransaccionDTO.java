/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dtos;
import java.time.LocalDate;
import java.util.List;
/**
 *
 * @author Ariel Eduardo Borbon Izaguirre 252116
 * Alberto Jimenez Garcia 252595
 */

public class TransaccionDTO {
    private String numTransaccion;
    private LocalDate fechaHora;
    private String tipo;
    private double montoTotal;
    private String estado;
    private List<Integer> boletosIds;

    public TransaccionDTO() {
    }

    public TransaccionDTO(String numTransaccion, LocalDate fechaHora, String tipo, double montoTotal, String estado, List<Integer> boletosIds) {
        this.numTransaccion = numTransaccion;
        this.fechaHora = fechaHora;
        this.tipo = tipo;
        this.montoTotal = montoTotal;
        this.estado = estado;
        this.boletosIds = boletosIds;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<Integer> getBoletosIds() {
        return boletosIds;
    }

    public void setBoletosIds(List<Integer> boletosIds) {
        this.boletosIds = boletosIds;
    }

    


}