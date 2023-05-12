package stmsat.cambusa.entity;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Entity che rappresenta i tipi di prodotto e le loro caratteristiche comuni.
 *
 * @author Matteo Steccolini
 */
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Tipo extends BaseEntity {
    
    @NotEmpty(message = "Indicare un nome per il tipo di prodotto")
    private String name;
    
    @NotNull(message = "Indicare se scadenza preferibile o inderogabile")
    private Boolean dataScadenzaPreferibile;
    
    private Integer giorniValiditaDopoScadenza = 0;
    
    private Boolean apribile = true;
    
    private Integer giorniValiditaDopoApertura = 0;
}
