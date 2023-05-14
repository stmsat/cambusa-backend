package stmsat.cambusa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import stmsat.cambusa.entity.Prodotto;
import stmsat.cambusa.entity.Posizione;
import stmsat.cambusa.entity.Tipo;
import stmsat.cambusa.repository.ProdottoRepository;
import stmsat.cambusa.repository.TipoRepository;
import stmsat.cambusa.repository.PosizioneRepository;

/**
 *
 * @author Matteo Steccolini
 */
@RestController
@RequestMapping(path = "/cambusa")
@Transactional
public class CambusaController {
    
    @Autowired
    private ProdottoRepository prodottoRepository;
    
    @Autowired
    private TipoRepository tipoRepository;
    
    @Autowired
    private PosizioneRepository posizioneRepository;
    
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private CambusaService cambusaService;
    
    /**
     * Endpoint per la lista dei prodotti.
     * 
     * @param name Il nome del prodotto contiene questa stringa
     * @param dataScadenzaLt Il prodotto scade prima di questa data (YYYY-MM-DD)
     * @param dataScadenzaGenerataLt Il prodotto scade prima di questa data "estesa" (YYYY-MM-DD)
     * @param dataScadenzaGt Il prodotto scade dopo questa data (YYYY-MM-DD)
     * @param dataScadenzaGenerataGt Il prodotto scade dopo questa data "estesa" (YYYY-MM-DD)
     * @param tipo Id del Tipo di prodotto (parametro ripetibile, o valori separati da virgola)
     * @param posizione Id della Posizione del prodotto (parametro ripetibile, o valori separati da virgola)
     * @return Lista dei prodotti che corrispondono ai criteri di ricerca
     */
    @GetMapping(path = "/prodotti", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Prodotto> getProdotti(
            @RequestParam(name = "name", required = false, defaultValue = "") String name,
            @RequestParam(name = "dataScadenzaLt", required = false) LocalDate dataScadenzaLt,
            @RequestParam(name = "dataScadenzaGenerataLt", required = false) LocalDate dataScadenzaGenerataLt,
            @RequestParam(name = "dataScadenzaGt", required = false) LocalDate dataScadenzaGt,
            @RequestParam(name = "dataScadenzaGenerataGt", required = false) LocalDate dataScadenzaGenerataGt,
            @RequestParam(name = "tipo", required = false) List<Tipo> tipo,
            @RequestParam(name = "posizione", required = false) List<Posizione> posizione
    ) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Prodotto> cquery = cb.createQuery(Prodotto.class);
        Root<Prodotto> prodotto = cquery.from(Prodotto.class);
        ArrayList<Predicate> predicati = new ArrayList<>();
        if (name != null) {
            predicati.add(cb.like(prodotto.get("name").as(String.class), "%" + name + "%"));
        }
        if (tipo != null && !tipo.isEmpty()) {
            predicati.add(prodotto.get("tipo").in(tipo));
        }
        if (posizione != null && !posizione.isEmpty()) {
            predicati.add(prodotto.get("posizione").in(posizione));
        }
        if (dataScadenzaLt != null) {
            predicati.add(cb.lessThanOrEqualTo(prodotto.get("dataScadenza").as(LocalDate.class), dataScadenzaLt));
        }
        if (dataScadenzaGenerataLt != null) {
            predicati.add(cb.lessThanOrEqualTo(prodotto.get("dataScadenzaGenerata").as(LocalDate.class), dataScadenzaGenerataLt));
        }
        if (dataScadenzaGt != null) {
            predicati.add(cb.greaterThan(prodotto.get("dataScadenza").as(LocalDate.class), dataScadenzaGt));
        }
        if (dataScadenzaGenerataGt != null) {
            predicati.add(cb.greaterThan(prodotto.get("dataScadenzaGenerata").as(LocalDate.class), dataScadenzaGenerataGt));
        }
        cquery.select(prodotto).where(predicati.toArray(Predicate[]::new));
        return this.entityManager.createQuery(cquery).getResultList();
    }
    
    @GetMapping(path = "/prodotti/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<Prodotto> getProdotto(@PathVariable("id") UUID id) {
        return prodottoRepository.findById(id);
    }
    
    @GetMapping(path = "/tipi", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Tipo> getTipi(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "sortby", required = false, defaultValue = "name") String[] sortby,
            @RequestParam(name = "sortdirection", required = false, defaultValue = "ASC") String[] sortdirection
    ) {
        List<Sort.Order> ordinamenti = parseOrdinamento(sortby, sortdirection);
        if (name != null) {
            Tipo example = new Tipo();
            example.setName(name); //name e' l'unico campo non null di questo Example e quindi l'unico che viene verificato
            ExampleMatcher matcher = ExampleMatcher.matchingAll().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnoreCase();
            Example<Tipo> e = Example.of(example, matcher);
            return tipoRepository.findAll(e, Sort.by(ordinamenti));
        } else {
            return tipoRepository.findAll(Sort.by(ordinamenti));
        }
    }
    
    @GetMapping(path = "/tipi/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<Tipo> getTipo(@PathVariable("id") UUID id) {
        return tipoRepository.findById(id);
    }
    
    @GetMapping(path = "/posizioni", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Posizione> getPosizioni(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "sortby", required = false, defaultValue = "name") String[] sortby,
            @RequestParam(name = "sortdirection", required = false, defaultValue = "ASC") String[] sortdirection
    ) {
        List<Sort.Order> ordinamenti = parseOrdinamento(sortby, sortdirection);
        if (name != null) {
            Posizione example = new Posizione();
            example.setName(name); //name e' l'unico campo non null di questo Example e quindi l'unico che viene verificato
            ExampleMatcher matcher = ExampleMatcher.matchingAll().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnoreCase();
            Example<Posizione> e = Example.of(example, matcher);
            return posizioneRepository.findAll(e, Sort.by(ordinamenti));
        } else {
            return posizioneRepository.findAll(Sort.by(ordinamenti));
        }
    }
    
    @GetMapping(path = "/posizioni/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<Posizione> getPosizione(@PathVariable("id") UUID id) {
        return posizioneRepository.findById(id);
    }
    
    @PutMapping(path = "/posizioni", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> putPosizione(@Valid @RequestBody Posizione posizione, BindingResult br) {
        if (br.hasErrors()) {
            return new ResponseEntity<>(formatBindingErrors(br), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(posizioneRepository.save(posizione), HttpStatus.CREATED);
        }
    }
    
    @PutMapping(path = "/tipi", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> putTipo(@Valid @RequestBody Tipo tipo, BindingResult br) {
        if (br.hasErrors()) {
            return new ResponseEntity<>(formatBindingErrors(br), HttpStatus.BAD_REQUEST);
        } else {
            tipo = tipoRepository.save(tipo);
            this.cambusaService.ricalcolaDataScadenzaProdotti(tipo);
            return new ResponseEntity<>(tipo, HttpStatus.CREATED);
        }
    }
    
    @PutMapping(path = "/prodotti", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> putProdotto(@Valid @RequestBody Prodotto prodotto, BindingResult br) {
        if (br.hasErrors()) {
            return new ResponseEntity<>(formatBindingErrors(br), HttpStatus.BAD_REQUEST);
        } else {
            //prodotto.setDefaultValues(); // gestito da PrePersist
            return new ResponseEntity<>(prodottoRepository.save(prodotto), HttpStatus.CREATED);
        }
    }
    
    private List<String> formatBindingErrors(BindingResult br) {
        List<String> listaErrori = new LinkedList<>();
        for (ObjectError oe : br.getAllErrors()) {
            listaErrori.add(oe.getDefaultMessage());
        }
        return listaErrori;
    }
    
    /**
     * Ipotesi di metodo di ordinamento comune laddove si usa findAll.
     * 
     * @param sortby Elenco campi per cui ordinare.
     * @param sortdirection Elenco direzione ordinamento (ASC/DESC); se la lunghezza dell'array non corrisponde a quella di sortby viene usato il primo.
     * @return 
     */
    private List<Sort.Order> parseOrdinamento(String[] sortby, String[] sortdirection) {
        List<Sort.Order> ordinamenti = new LinkedList<>();
        if (sortby != null) {
            for (int s = 0; s < sortby.length; s++) {
                Sort.Direction dir = sortdirection == null ? Sort.Direction.ASC 
                        : sortdirection.length > s ? Sort.Direction.valueOf(sortdirection[s])
                        : Sort.Direction.valueOf(sortdirection[0]);
                ordinamenti.add(new Sort.Order(dir, sortby[s]));
            }
        }
        return ordinamenti;
    }
}
