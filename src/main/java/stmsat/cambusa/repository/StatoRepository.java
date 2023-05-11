
package stmsat.cambusa.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import stmsat.cambusa.entity.Stato;

/**
 *
 * @author Matteo Steccolini
 */
public interface StatoRepository extends JpaRepository<Stato, UUID>{
    
}
