package stmsat.cambusa.entity;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 *
 * @author Matteo Steccolini
 */
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Stato extends BaseEntity {
    
    @NotEmpty(message = "Indicare un nome per lo stato del prodotto")
    private String name;
}
