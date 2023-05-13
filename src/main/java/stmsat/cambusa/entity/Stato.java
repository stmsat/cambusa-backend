package stmsat.cambusa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Matteo Steccolini
 */
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Stato extends BaseEntity {
    
    @Setter
    @NotEmpty(message = "Indicare un nome per lo stato del prodotto")
    private String name;
    
    
    /**
     * Imposta valori predefiniti per i campi che possono avere un default.
     */
    @PrePersist
    @PreUpdate
    public void setDefaultValues() {
    }
}
