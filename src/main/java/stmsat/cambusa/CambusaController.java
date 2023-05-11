package stmsat.cambusa;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stmsat.cambusa.entity.Prodotto;
import stmsat.cambusa.entity.Stato;
import stmsat.cambusa.entity.Tipo;
import stmsat.cambusa.repository.ProdottoRepository;
import stmsat.cambusa.repository.StatoRepository;
import stmsat.cambusa.repository.TipoRepository;

/**
 *
 * @author Matteo Steccolini
 */
@RestController
@RequestMapping(path = "/cambusa")
public class CambusaController {
    
    @Autowired
    private ProdottoRepository prodottoRepository;
    
    @Autowired
    private TipoRepository tipoProdottoRepository;
    
    @Autowired
    private StatoRepository statoRepository;
    
    @GetMapping(path = "/prodotti", produces = "application/json")
    public List<Prodotto> getProdotti() {
        return prodottoRepository.findAll();
    }
    
    @GetMapping(path = "/prodotti/{id}", produces = "application/json")
    public Optional<Prodotto> getProdotto(@PathVariable("id") UUID id) {
        return prodottoRepository.findById(id);
    }
    
    @GetMapping(path = "/tipi", produces = "application/json")
    public List<Tipo> getTipi() {
        return tipoProdottoRepository.findAll();
    }
    
    @GetMapping(path = "/tipi/{id}", produces = "application/json")
    public Optional<Tipo> getTipo(@PathVariable("id") UUID id) {
        return tipoProdottoRepository.findById(id);
    }
    
    @GetMapping(path = "/stati", produces = "application/json")
    public List<Stato> getStati() {
        return statoRepository.findAll();
    }
    
    @GetMapping(path = "/stati/{id}", produces = "application/json")
    public Optional<Stato> getStato(@PathVariable("id") UUID id) {
        return statoRepository.findById(id);
    }
}
