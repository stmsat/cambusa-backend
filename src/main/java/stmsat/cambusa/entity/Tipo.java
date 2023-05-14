package stmsat.cambusa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity che rappresenta i tipi di prodotto e le loro caratteristiche comuni.
 *
 * @author Matteo Steccolini
 */
@Entity
@Getter
@NoArgsConstructor
public class Tipo extends BaseEntity {

    @Setter
    @NotEmpty(message = "Indicare un nome per il tipo di prodotto")
    private String name;

    @NotNull(message = "Indicare se scadenza preferibile o inderogabile")
    private Boolean dataScadenzaPreferibile;

    private Integer giorniValiditaDopoScadenza;

    private Boolean apribile;

    private Integer giorniValiditaDopoApertura;
    
    public Tipo(String name, Boolean dataScadenzaPreferibile, Integer giorniValiditaDopoScadenza, Boolean apribile, Integer giornivaliditaDopoApertura) {
        this.name = name;
        this.dataScadenzaPreferibile = dataScadenzaPreferibile;
        this.giorniValiditaDopoScadenza = giorniValiditaDopoScadenza;
        this.apribile = apribile;
        this.giorniValiditaDopoApertura = giornivaliditaDopoApertura;
        this.setDefaultValues();
    }

    /**
     * Imposta valori predefiniti per i campi che possono avere un default.
     */
    @PrePersist
    @PreUpdate
    public final void setDefaultValues() {
        if (this.giorniValiditaDopoScadenza == null) {
            this.giorniValiditaDopoScadenza = 0;
        }
        if (this.giorniValiditaDopoApertura == null) {
            this.giorniValiditaDopoApertura = 0;
        }
        if (this.apribile == null) {
            this.apribile = true;
        }
    }
}
