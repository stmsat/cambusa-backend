
package stmsat.cambusa.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import stmsat.cambusa.entity.Prodotto;

/**
 *
 * @author Matteo Steccolini
 */
public interface ProdottoRepository extends JpaRepository<Prodotto, UUID> {
    
}
