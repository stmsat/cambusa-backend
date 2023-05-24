
package stmsat.cambusa.repository;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import stmsat.cambusa.entity.Posizione;

/**
 *
 * @author Matteo Steccolini
 */
public interface PosizioneRepository extends CrudRepository<Posizione, UUID>{
    
}
