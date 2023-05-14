package stmsat.cambusa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stmsat.cambusa.entity.Prodotto;
import stmsat.cambusa.entity.Tipo;

/**
 * Classe per operazioni complesse.
 * 
 * @author Matteo Steccolini
 */
@Service
@Transactional
public class CambusaService {
    
    @Autowired
    private EntityManager entityManager;
    
    public void ricalcolaDataScadenzaProdotti(Tipo tipo) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Prodotto> criteria = cb.createQuery(Prodotto.class);
        Root<Prodotto> root = criteria.from(Prodotto.class);
        criteria.select(root).where(root.get("tipo").in(tipo));
        List<Prodotto> prodotti = this.entityManager.createQuery(criteria).getResultList();
        for (Prodotto p : prodotti) {
            p.generaDataScadenza();
            this.entityManager.merge(p);
        }
    }
}
