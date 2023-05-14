package stmsat.cambusa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity che rappresenta il prodotto.
 *
 * @author Matteo Steccolini
 */
@Entity
@Getter

public class Prodotto extends BaseEntity {
    
    /**
     * Costruttore da non usare per la persistenza.
     */
    public Prodotto() {
    }
    
    public Prodotto(String name, LocalDate dataScadenza, Tipo tipo, Stato stato, LocalDate dataApertura, Boolean aperto, Integer quantita) {
        this.name = Objects.requireNonNull(name, "Specificare name");
        this.dataScadenza = Objects.requireNonNull(dataScadenza, "Specificare dataScadenza");
        this.tipo = Objects.requireNonNull(tipo, "Specificare tipo");
        this.stato = Objects.requireNonNull(stato, "Specificare stato");
        this.dataApertura = dataApertura;
        this.aperto = aperto;
        this.quantita = quantita;
        this.setDefaultValues();
    }
    
    @Setter
    @NotEmpty(message = "Indicare un nome per il prodotto")
    private String name;
    
    @NotNull(message = "Indicare la data di scadenza del prodotto")
    private LocalDate dataScadenza;
    
    @ManyToOne
    @NotNull(message = "Indicare il tipo di prodotto")
    private Tipo tipo;
    
    @ManyToOne
    @NotNull(message = "Indicare lo stato del prodotto")
    private Stato stato;
    
    private LocalDate dataApertura;
    
    private Boolean aperto;
    
    @Min(1)
    private Integer quantita;
    
    private LocalDate dataScadenzaGenerata;
    
    /**
     * Imposta la dataScadenzaGenerata per il prodotto in base al fatto che quella definita sia solo preferibile, e se il prodotto sia aperto o meno.
     * 
     * @return 
     */
    private void generaDataScadenza() {
        LocalDate dataGenerataPostScadenza = this.dataScadenza;
        LocalDate dataGenerataPostApertura = this.dataApertura;
        if (tipo.getDataScadenzaPreferibile()) {
            dataGenerataPostScadenza = dataGenerataPostScadenza.plusDays(tipo.getGiorniValiditaDopoScadenza());
        }
        if (tipo.getApribile() && this.getAperto()) {
            if (dataGenerataPostApertura == null) {
                //non dovrebbe succedere, ma in caso succeda metto oggi come data apertura
                dataGenerataPostApertura = LocalDate.now();
            }
            dataGenerataPostApertura = dataGenerataPostApertura.plusDays(tipo.getGiorniValiditaDopoApertura());
        }
        //restituisco la data piu' bassa calcolata
        if (dataGenerataPostApertura == null) {
            this.dataScadenzaGenerata = dataGenerataPostScadenza;
        } else {
            this.dataScadenzaGenerata = dataGenerataPostScadenza.compareTo(dataGenerataPostApertura) <= 0 ? dataGenerataPostScadenza : dataGenerataPostApertura;
        }
    }
    
    /**
     * Imposta valori predefiniti per i campi che possono avere un default.
     */
    @PrePersist
    @PreUpdate
    public final void setDefaultValues() {
        if (this.aperto == null) {
            this.aperto = false;
        }
        if (this.aperto && this.dataApertura == null) {
            this.dataApertura = LocalDate.now();
        }
        if (this.quantita == null) {
            this.quantita = 1;
        }
        if (this.dataScadenzaGenerata == null) {
            this.generaDataScadenza();
        }
    }
}
