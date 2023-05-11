package stmsat.cambusa.entity;

import jakarta.persistence.Entity;
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
    
    private String name;
}
