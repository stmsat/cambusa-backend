package stmsat.cambusa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.time.LocalDate;
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
@Transactional
public class CambusaController {
    
    @Autowired
    private ProdottoRepository prodottoRepository;
    
    @Autowired
    private TipoRepository tipoRepository;
    
    @Autowired
    private StatoRepository statoRepository;
    
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private CambusaService cambusaService;
    
    @GetMapping(path = "/prodotti", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Prodotto> getProdotti(
            @RequestParam(name = "name", required = false, defaultValue = "") String name,
            @RequestParam(name = "dataScadenza", required = false) LocalDate dataScadenza,
            @RequestParam(name = "scadenzaEstesa", required = false, defaultValue = "true") Boolean scadenzaEstesa) {
        Query queryProdotti;
        StringBuilder sqlQueryProdotti = new StringBuilder("select p from Prodotto p ");
        sqlQueryProdotti.append("join Tipo t on p.tipo = t.id ");
        sqlQueryProdotti.append("join Stato s on p.stato = s.id ");
        sqlQueryProdotti.append("where p.name like '%' || :name || '%' ");
        if (dataScadenza != null) {
            if (scadenzaEstesa) {
                sqlQueryProdotti.append("and p.dataScadenzaGenerata < :dataScadenza");
            } else {
                sqlQueryProdotti.append("and p.dataScadenza < :dataScadenza");
            }
        }
        queryProdotti = this.entityManager.createQuery(sqlQueryProdotti.toString());
        queryProdotti.setParameter("name", name);
        if (dataScadenza != null) {
            queryProdotti.setParameter("dataScadenza", dataScadenza);
        }
        return queryProdotti.getResultList();
    }
    
    @GetMapping(path = "/prodotti/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<Prodotto> getProdotto(@PathVariable("id") UUID id) {
        return prodottoRepository.findById(id);
    }
    
    @GetMapping(path = "/tipi", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Tipo> getTipi(@RequestParam(name = "name", required = false) String name, @RequestParam(name = "sortby", required = false, defaultValue = "name") String[] sortby) {
        if (name != null) {
            Tipo example = new Tipo();
            example.setName(name); //name e' l'unico campo non null di questo Example e quindi l'unico che viene verificato
            ExampleMatcher matcher = ExampleMatcher.matchingAll().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnoreCase();
            Example<Tipo> e = Example.of(example, matcher);
            return tipoRepository.findAll(e, Sort.by(sortby));
        } else {
            return tipoRepository.findAll(Sort.by(sortby));
        }
    }
    
    @GetMapping(path = "/tipi/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<Tipo> getTipo(@PathVariable("id") UUID id) {
        return tipoRepository.findById(id);
    }
    
    @GetMapping(path = "/stati", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Stato> getStati(@RequestParam(name = "name", required = false) String name, @RequestParam(name = "sortby", required = false, defaultValue = "name") String[] sortby) {
        if (name != null) {
            Stato example = new Stato();
            example.setName(name); //name e' l'unico campo non null di questo Example e quindi l'unico che viene verificato
            ExampleMatcher matcher = ExampleMatcher.matchingAll().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnoreCase();
            Example<Stato> e = Example.of(example, matcher);
            return statoRepository.findAll(e, Sort.by(sortby));
        } else {
            return statoRepository.findAll(Sort.by(sortby));
        }
    }
    
    @GetMapping(path = "/stati/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<Stato> getStato(@PathVariable("id") UUID id) {
        return statoRepository.findById(id);
    }
    
    @PutMapping(path = "/stati", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> putStato(@Valid @RequestBody Stato stato, BindingResult br) {
        if (br.hasErrors()) {
            return new ResponseEntity<>(formatBindingErrors(br), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(statoRepository.save(stato), HttpStatus.CREATED);
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
}
