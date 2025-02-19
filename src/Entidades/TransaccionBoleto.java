
package Entidades;

/**
 *
 * @author Ariel Eduardo Borbon Izaguirre 252116
 * Alberto Jimenez Garcia 252595
 * v2
 */
public class TransaccionBoleto {
    private int transaccionId;
    private int boletoId;

    public TransaccionBoleto() {
    }

    public TransaccionBoleto(int transaccionId, int boletoId) {
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
