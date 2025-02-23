/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

/**
 *
 * @author PC Gamer
 */
public class PersonasTransacciones {

    private int transaccionId;
    private int compradorId;
    private int vendedorId;

    // Constructor
    public PersonasTransacciones(int transaccionId, int compradorId, int vendedorId) {
        this.transaccionId = transaccionId;
        this.compradorId = compradorId;
        this.vendedorId = vendedorId;
    }

    // Getters y Setters
    public int getTransaccionId() {
        return transaccionId;
    }

    public void setTransaccionId(int transaccionId) {
        this.transaccionId = transaccionId;
    }

    public int getCompradorId() {
        return compradorId;
    }

    public void setCompradorId(int compradorId) {
        this.compradorId = compradorId;
    }

    public int getVendedorId() {
        return vendedorId;
    }

    public void setVendedorId(int vendedorId) {
        this.vendedorId = vendedorId;
    }

    @Override
    public String toString() {
        return "PersonasTransacciones{" +
                "transaccionId=" + transaccionId +
                ", compradorId=" + compradorId +
                ", vendedorId=" + vendedorId +
                '}';
    }
}

