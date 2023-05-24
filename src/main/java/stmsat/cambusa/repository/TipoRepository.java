
package stmsat.cambusa.repository;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import stmsat.cambusa.entity.Tipo;

/**
 *
 * @author Matteo Steccolini
 */
public interface TipoRepository extends CrudRepository<Tipo, UUID>{
    
}
