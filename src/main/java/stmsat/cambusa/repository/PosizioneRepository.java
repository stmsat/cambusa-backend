
package stmsat.cambusa.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import stmsat.cambusa.entity.Posizione;

/**
 *
 * @author Matteo Steccolini
 */
public interface PosizioneRepository extends JpaRepository<Posizione, UUID>{
    
}
