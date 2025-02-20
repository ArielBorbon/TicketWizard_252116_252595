/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dtos;

/**
 *
 * @author Ariel Eduardo Borbon Izaguirre 252116
 * Alberto Jimenez Garcia 252595
 */
public class transaccionBoletoDTO {
    private int transaccionId;
    private int boletoId;

    public transaccionBoletoDTO() {
    }

    public transaccionBoletoDTO(int transaccionId, int boletoId) {
        this.transaccionId = transaccionId;
        this.boletoId = boletoId;
    }

    
    
    
    public int getTransaccionId() {
        return transaccionId;
    }

    public void setTransaccionId(int transaccionId) {
        this.transaccionId = transaccionId;
    }

    public int getBoletoId() {
        return boletoId;
    }

    public void setBoletoId(int boletoId) {
        this.boletoId = boletoId;
    }


    
    
}