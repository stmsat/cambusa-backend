
package stmsat.cambusa.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import stmsat.cambusa.entity.Tipo;

/**
 *
 * @author Matteo Steccolini
 */
public interface TipoRepository extends JpaRepository<Tipo, UUID>{
    
}
