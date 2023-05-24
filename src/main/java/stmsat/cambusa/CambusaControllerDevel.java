package stmsat.cambusa;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stmsat.cambusa.entity.Prodotto;
import stmsat.cambusa.entity.Posizione;
import stmsat.cambusa.entity.Tipo;
import stmsat.cambusa.repository.ProdottoRepository;
import stmsat.cambusa.repository.TipoRepository;
import stmsat.cambusa.repository.PosizioneRepository;

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
    private PosizioneRepository posizioneRepository;
    
    @GetMapping(path = "/init", produces = "text/plain")
    @Operation(description = "Inizializza il dataset di test")
    public String init() {
        prodottoRepository.deleteAll();
        tipoProdottoRepository.deleteAll();
        posizioneRepository.deleteAll();
        
        String messaggio = "{\"message\": \"Database inizializzato\"}";
        
        Posizione freezer = posizioneRepository.save(new Posizione("freezer"));
        Posizione frigorifero = posizioneRepository.save(new Posizione("frigorifero"));
        Posizione pensili_cucina = posizioneRepository.save(new Posizione("pensili cucina"));
        Posizione scaffali_dispensa = posizioneRepository.save(new Posizione("scaffali dispensa"));
        
        Tipo latticino = tipoProdottoRepository.save(new Tipo("latticino", false, 0, true, 1));
        Tipo pane_lievitato = tipoProdottoRepository.save(new Tipo("pane lievitato fresco", true, 5, false, 0));
        Tipo pane_precotto = tipoProdottoRepository.save(new Tipo("pane lievitato precotto", true, 2, false, 0));
        Tipo pane_azzimo = tipoProdottoRepository.save(new Tipo("pane azzimo", true, 10, false, 0));
        
        prodottoRepository.save(new Prodotto("mozzarella", LocalDate.now().plusDays(5), latticino, frigorifero, null, false,1));
        prodottoRepository.save(new Prodotto("pane all'olio", LocalDate.now().plusDays(3), pane_lievitato, pensili_cucina, null, false,1));
        prodottoRepository.save(new Prodotto("rosetta", LocalDate.now().plusDays(1), pane_precotto, scaffali_dispensa, null, false,1));
        prodottoRepository.save(new Prodotto("pane non lievitato", LocalDate.now().plusDays(1), pane_azzimo, null, null, false,1));
        
        return messaggio;
    }
}
