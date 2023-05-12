package stmsat.cambusa;

import jakarta.validation.Valid;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
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
    private TipoRepository tipoRepository;
    
    @Autowired
    private StatoRepository statoRepository;
    
    @GetMapping(path = "/prodotti", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Prodotto> getProdotti() {
        return prodottoRepository.findAll();
    }
    
    @GetMapping(path = "/prodotti/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<Prodotto> getProdotto(@PathVariable("id") UUID id) {
        return prodottoRepository.findById(id);
    }
    
    @GetMapping(path = "/tipi", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Tipo> getTipi() {
        return tipoRepository.findAll();
    }
    
    @GetMapping(path = "/tipi/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<Tipo> getTipo(@PathVariable("id") UUID id) {
        return tipoRepository.findById(id);
    }
    
    @GetMapping(path = "/stati", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Stato> getStati() {
        return statoRepository.findAll();
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
            return new ResponseEntity<>(tipoRepository.save(tipo), HttpStatus.CREATED);
        }
    }
    
    @PutMapping(path = "/prodotti", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> putProdotto(@Valid @RequestBody Prodotto prodotto, BindingResult br) {
        if (br.hasErrors()) {
            return new ResponseEntity<>(formatBindingErrors(br), HttpStatus.BAD_REQUEST);
        } else {
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
