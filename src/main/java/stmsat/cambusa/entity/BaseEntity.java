package stmsat.cambusa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.UUID;
import lombok.Getter;

/**
 * Male necessario per escludere il campo id dal costruttore predefinito generato da lombok.
 *
 * @author Matteo Steccolini
 */
@Entity
@Getter
public class BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
}
