package stmsat.cambusa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
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
    
    private String name;
    
    private LocalDate dataScadenza;
    
    @ManyToOne
    private Tipo tipo;
    
    @ManyToOne
    private Stato stato;
    
    private LocalDate dataApertura;
    
    private Boolean aperto;
    
    private Integer quantita;
}
