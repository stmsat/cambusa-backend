package stmsat.cambusa.entity;

import jakarta.persistence.Entity;
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
    
    private String name;
    
    private Boolean dataScadenzaPreferibile;
    
    private Integer giorniValiditaDopoScadenza;
    
    private Boolean apribile;
    
    private Integer giorniValiditaDopoApertura;
}
