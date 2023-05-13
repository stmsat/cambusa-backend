package stmsat.cambusa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Entity che rappresenta il prodotto.
 *
 * @author Matteo Steccolini
 */
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Prodotto extends BaseEntity {
    
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
    
    /**
     * Imposta valori predefiniti per i campi che possono avere un default.
     */
    @PrePersist
    @PreUpdate
    public void setDefaultValues() {
        if (this.aperto == null) {
            this.aperto = false;
        }
        if (this.quantita == null) {
            this.quantita = 1;
        }
    }
}
