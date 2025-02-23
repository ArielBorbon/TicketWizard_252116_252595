
package Entidades;

/**
 *
 * @author Ariel Eduardo Borbon Izaguirre 252116
 * Alberto Jimenez Garcia 252595
 */
public class Boleto {
    private int boletoId;
    private String numSerie;
    private String fila;
    private String asiento;
    private String numControl;
    private double precioOriginal;
    private int eventoId;
    private int personaId;
    private Evento evento; // Objeto Evento completo (para la lógica)

    public Boleto() {
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public Boleto(int boletoId, String numSerie, String fila, String asiento, String numControl, double precioOriginal, int eventoId, int personaId) {
        this.boletoId = boletoId;
        this.numSerie = numSerie;
        this.fila = fila;
        this.asiento = asiento;
        this.numControl = numControl;
        this.precioOriginal = precioOriginal;
        this.eventoId = eventoId;
        this.personaId = personaId;
    }

    
    
    
    public int getBoletoId() {
        return boletoId;
    }

    public void setBoletoId(int boletoId) {
        this.boletoId = boletoId;
    }

    public String getNumSerie() {
        return numSerie;
    }

    public void setNumSerie(String numSerie) {
        this.numSerie = numSerie;
    }

    public String getFila() {
        return fila;
    }

    public void setFila(String fila) {
        this.fila = fila;
    }

    public String getAsiento() {
        return asiento;
    }

    public void setAsiento(String asiento) {
        this.asiento = asiento;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public double getPrecioOriginal() {
        return precioOriginal;
    }

    public void setPrecioOriginal(double precioOriginal) {
        this.precioOriginal = precioOriginal;
    }

    public int getEventoId() {
        return eventoId;
    }

    public void setEventoId(int eventoId) {
        this.eventoId = eventoId;
    }

    public int getPersonaId() {
        return personaId;
    }

    public void setPersonaId(int personaId) {
        this.personaId = personaId;
    }
    
    
    
    
    
}
