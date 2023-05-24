
package stmsat.cambusa.repository;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import stmsat.cambusa.entity.Prodotto;

/**
 *
 * @author Matteo Steccolini
 */
public interface ProdottoRepository extends CrudRepository<Prodotto, UUID> {
    
}
