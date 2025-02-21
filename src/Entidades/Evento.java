
package Entidades;

import java.time.LocalDate;


/**
 *
 * @author Ariel Eduardo Borbon Izaguirre 252116
 * Alberto Jimenez Garcia 252595
 */
public class Evento {
    private int eventoId;
    private String nombre;
    private LocalDate fecha;
    private String recinto;
    private String ciudad;
    private String estado;
    private String descripcion;
    private int totalBoletos;

    public Evento() {
    }

    public Evento(int eventoId, String nombre, LocalDate fecha, String recinto, String ciudad, String estado, String descripcion, int totalBoletos) {
        this.eventoId = eventoId;
        this.nombre = nombre;
        this.fecha = fecha;
        this.recinto = recinto;
        this.ciudad = ciudad;
        this.estado = estado;
        this.descripcion = descripcion;
        this.totalBoletos = totalBoletos;
    }

    public int getEventoId() {
        return eventoId;
    }

    public void setEventoId(int eventoId) {
        this.eventoId = eventoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getRecinto() {
        return recinto;
    }

    public void setRecinto(String recinto) {
        this.recinto = recinto;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getTotalBoletos() {
        return totalBoletos;
    }

    public void setTotalBoletos(int totalBoletos) {
        this.totalBoletos = totalBoletos;
    }

 @Override
public String toString() {
    return nombre + " - " + fecha.toString().substring(0, 16) + " - " + recinto;
}
    
    
    
    
}
