package stmsat.cambusa;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stmsat.cambusa.entity.Prodotto;
import stmsat.cambusa.entity.Stato;
import stmsat.cambusa.entity.Tipo;
import stmsat.cambusa.repository.ProdottoRepository;
import stmsat.cambusa.repository.StatoRepository;
import stmsat.cambusa.repository.TipoRepository;

/**
 * Controller per funzionalit&agrave; disponibili solo in ambiente di sviluppo.
 * 
 * @author Matteo Steccolini
 */
@RestController()
@RequestMapping("/cambusaDevel")
@ConditionalOnProperty(name="stmsat.cambusa.devel", havingValue = "true", matchIfMissing = false)
@Transactional
public class CambusaControllerDevel {
    
    @Autowired
    private ProdottoRepository prodottoRepository;
    
    @Autowired
    private TipoRepository tipoProdottoRepository;
    
    @Autowired
    private StatoRepository statoRepository;
    
    @GetMapping(path = "/init", produces = "application/json")
    public ResponseEntity<String> init() {
        prodottoRepository.deleteAll();
        tipoProdottoRepository.deleteAll();
        statoRepository.deleteAll();
        
        String messaggio = "{\"message\": \"Database inizializzato\"}";
        
        Stato freezer = statoRepository.save(new Stato("freezer"));
        Stato frigorifero = statoRepository.save(new Stato("frigorifero"));
        Stato ambiente = statoRepository.save(new Stato("ambiente"));
        Stato contenitore_ermetico = statoRepository.save(new Stato("contenitore ermetico"));
        
        Tipo latticino = tipoProdottoRepository.save(new Tipo("latticino", false, 0, true, 1));
        Tipo pane = tipoProdottoRepository.save(new Tipo("pane lievitato fresco", true, 5, false, 0));
        Tipo pane_precotto = tipoProdottoRepository.save(new Tipo("pane lievitato precotto", true, 2, false, 0));
        Tipo pane_azzimo = tipoProdottoRepository.save(new Tipo("pane azzimo", true, 10, false, 0));
        
        prodottoRepository.save(new Prodotto("mozzarella", LocalDate.now().plusDays(5), latticino, frigorifero, null, false,1));
        prodottoRepository.save(new Prodotto("rosetta", LocalDate.now().plusDays(1), pane_precotto, ambiente, null, false,1));
        
        return new ResponseEntity<>(messaggio, HttpStatus.CREATED);
    }
}
