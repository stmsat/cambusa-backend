package stmsat.cambusa;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
@OpenAPIDefinition(
        info = @Info(
                title = "cambusa-controller",
                description = "Servizio per la tracciatura delle scadenze dei prodotti",
                contact = @Contact(
                        name = "/stmsat/cambusa-backend",
                        url = "https://github.com/stmsat/cambusa-backend"
                ),
                version = "0.0.3"
        )
)
@Transactional
@Slf4j
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
     * Lista dei prodotti, ricercabile per vari parametri.
     * 
     * @param name Il nome del prodotto contiene questa stringa
     * @param dataScadenzaLt Il prodotto scade prima di questa data (YYYY-MM-DD)
     * @param dataScadenzaGenerataLt Il prodotto scade prima di questa data "estesa" (YYYY-MM-DD)
     * @param dataScadenzaGt Il prodotto scade dopo questa data (YYYY-MM-DD)
     * @param dataScadenzaGenerataGt Il prodotto scade dopo questa data "estesa" (YYYY-MM-DD)
     * @param tipo Id del Tipo di prodotto (parametro ripetibile, o valori separati da virgola)
     * @param posizione Id della Posizione del prodotto (parametro ripetibile, o valori separati da virgola)
     * @param sortby Elenco campi per cui ordinare.
     * @param sortdirection Elenco direzione ordinamento (ASC/DESC/asc/desc); se la lunghezza dell'array non corrisponde a quella di sortby viene usato il primo valore.
     * @return Lista dei prodotti che corrispondono ai criteri di ricerca
     */
    @GetMapping(path = "/prodotti", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Lista dei prodotti, ricercabile per vari parametri")
    public List<Prodotto> getProdotti(
            @RequestParam(name = "name", required = false, defaultValue = "") String name,
            @RequestParam(name = "dataScadenzaLt", required = false) LocalDate dataScadenzaLt,
            @RequestParam(name = "dataScadenzaGenerataLt", required = false) LocalDate dataScadenzaGenerataLt,
            @RequestParam(name = "dataScadenzaGt", required = false) LocalDate dataScadenzaGt,
            @RequestParam(name = "dataScadenzaGenerataGt", required = false) LocalDate dataScadenzaGenerataGt,
            @RequestParam(name = "tipo", required = false) List<Tipo> tipo,
            @RequestParam(name = "posizione", required = false) List<Posizione> posizione,
            @RequestParam(name = "sortby", required = false, defaultValue = "name") String[] sortby,
            @RequestParam(name = "sortdirection", required = false, defaultValue = "ASC") String[] sortdirection
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
        cquery.select(prodotto).where(predicati.toArray(Predicate[]::new)).orderBy(parseOrdinamento(sortby, sortdirection, cb, prodotto));
        return this.entityManager.createQuery(cquery).getResultList();
    }
    
    /**
     * Ricerca il prodotto per Id.
     * 
     * @param id
     * @return 
     */
    @GetMapping(path = "/prodotti/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Prodotto per Id")
    public Optional<Prodotto> getProdotto(@PathVariable("id") UUID id) {
        return prodottoRepository.findById(id);
    }
    
    /**
     * Lista dei tipi, ricercabili per name.
     * 
     * @param name Nome del tipo (anche parziale).
     * @param sortby Elenco campi per cui ordinare.
     * @param sortdirection Elenco direzione ordinamento (ASC/DESC/asc/desc); se la lunghezza dell'array non corrisponde a quella di sortby viene usato il primo valore.
     * @return 
     */
    @GetMapping(path = "/tipi", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Lista dei tipi, ricercabile per nome e ordinabile")
    public List<Tipo> getTipi(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "sortby", required = false, defaultValue = "name") String[] sortby,
            @RequestParam(name = "sortdirection", required = false, defaultValue = "ASC") String[] sortdirection
    ) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tipo> cquery = cb.createQuery(Tipo.class);
        Root<Tipo> tipo = cquery.from(Tipo.class);
        ArrayList<Predicate> predicati = new ArrayList<>();
        if (name != null) {
            predicati.add(cb.like(tipo.get("name").as(String.class), "%" + name + "%"));
        }
        cquery.select(tipo).where(predicati.toArray(Predicate[]::new)).orderBy(parseOrdinamento(sortby, sortdirection, cb, tipo));
        return this.entityManager.createQuery(cquery).getResultList();
    }
    
    /**
     * Ricerca il tipo per Id.
     * 
     * @param id
     * @return 
     */
    @GetMapping(path = "/tipi/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Tipo per Id")
    public Optional<Tipo> getTipo(@PathVariable("id") UUID id) {
        return tipoRepository.findById(id);
    }
    
    /**
     * Lista delle posizioni, ricercabili per name.
     * 
     * @param name Nome della posizione (anche parziale).
     * @param sortby Elenco campi per cui ordinare.
     * @param sortdirection Elenco direzione ordinamento (ASC/DESC/asc/desc); se la lunghezza dell'array non corrisponde a quella di sortby viene usato il primo valore.
     * @return 
     */
    @GetMapping(path = "/posizioni", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Lista delle posizioni, ricercabile per nome e ordinabile")
    public List<Posizione> getPosizioni(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "sortby", required = false, defaultValue = "name") String[] sortby,
            @RequestParam(name = "sortdirection", required = false, defaultValue = "ASC") String[] sortdirection
    ) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Posizione> cquery = cb.createQuery(Posizione.class);
        Root<Posizione> posizione = cquery.from(Posizione.class);
        ArrayList<Predicate> predicati = new ArrayList<>();
        if (name != null) {
            predicati.add(cb.like(posizione.get("name").as(String.class), "%" + name + "%"));
        }
        cquery.select(posizione).where(predicati.toArray(Predicate[]::new)).orderBy(parseOrdinamento(sortby, sortdirection, cb, posizione));
        return this.entityManager.createQuery(cquery).getResultList();
    }
    
    /**
     * Ricerca la posizione per Id.
     * 
     * @param id
     * @return 
     */
    @GetMapping(path = "/posizioni/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Posizione per Id")
    public Optional<Posizione> getPosizione(@PathVariable("id") UUID id) {
        return posizioneRepository.findById(id);
    }
    
    /**
     * Salva una Posizione (update se l'id &egrave; esistente).
     * 
     * @param posizione
     * @param br
     * @return 
     */
    @PutMapping(path = "/posizioni", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Salva una Posizione (insert o update)")
    public ResponseEntity<Object> putPosizione(@Valid @RequestBody Posizione posizione, BindingResult br) {
        if (br.hasErrors()) {
            return new ResponseEntity<>(formatBindingErrors(br), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(posizioneRepository.save(posizione), HttpStatus.CREATED);
        }
    }
    
    /**
     * Salva un Tipo (update se l'id &egrave; esistente).
     * 
     * @param tipo
     * @param br
     * @return 
     */
    @PutMapping(path = "/tipi", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Salva un TIpo (insert o update)")
    public ResponseEntity<Object> putTipo(@Valid @RequestBody Tipo tipo, BindingResult br) {
        if (br.hasErrors()) {
            return new ResponseEntity<>(formatBindingErrors(br), HttpStatus.BAD_REQUEST);
        } else {
            tipo = tipoRepository.save(tipo);
            this.cambusaService.ricalcolaDataScadenzaProdotti(tipo);
            return new ResponseEntity<>(tipo, HttpStatus.CREATED);
        }
    }
    
    /**
     * Salva un Prodotto (update se l'id &egrave; esistente).
     * 
     * @param prodotto
     * @param br
     * @return 
     */
    @PutMapping(path = "/prodotti", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Salva un Prodotto (insert o update)")
    public ResponseEntity<Object> putProdotto(@Valid @RequestBody Prodotto prodotto, BindingResult br) {
        if (br.hasErrors()) {
            return new ResponseEntity<>(formatBindingErrors(br), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(prodottoRepository.save(prodotto), HttpStatus.CREATED);
        }
    }
    
    /**
     * Cancellazione Posizione (va in errore se viola un vincolo foreign key).
     * 
     * @param id
     * @return 
     */
    @DeleteMapping(path = "/posizioni/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
    @Operation(description = "Elimina una Posizione")
    public ResponseEntity<String> deletePosizione(@PathVariable(name = "id") UUID id) {
        this.posizioneRepository.deleteById(id);
        return new ResponseEntity<>("Cancellazione effettuata", HttpStatus.OK);
    }
    
    /**
     * Cancellazione Tipo (va in errore se viola un vincolo foreign key).
     * 
     * @param id
     * @return 
     */
    @DeleteMapping(path = "/tipi/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
    @Operation(description = "Elimina un Tipo")
    public ResponseEntity<String> deleteTipo(@PathVariable(name = "id") UUID id) {
        this.tipoRepository.deleteById(id);
        return new ResponseEntity<>("Cancellazione effettuata", HttpStatus.OK);
    }
    
    /**
     * Cancellazione Prodotto.
     * 
     * @param id
     * @return 
     */
    @DeleteMapping(path = "/prodotti/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
    @Operation(description = "Elimina un Prodotto")
    public ResponseEntity<String> deleteProdotto(@PathVariable(name = "id") UUID id) {
        this.prodottoRepository.deleteById(id);
        return new ResponseEntity<>("Cancellazione effettuata", HttpStatus.OK);
    }
    
    /**
     * Imposta lo stato e data di apertura del prodotto e rigenera la data di scadenza.
     * 
     * @param id
     * @param aperto
     * @param dataApertura Opzionale, altrimenti oggi.
     * @param quantita
     * @return 
     */
    @PatchMapping(path = "/prodotti/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Aggiorna singoli campi di un Prodotto")
    public ResponseEntity<Object> patchProdotto(
            @PathVariable("id") UUID id,
            @RequestParam(name = "aperto", required = false) Boolean aperto,
            @RequestParam(name = "dataApertura", required = false) LocalDate dataApertura,
            @RequestParam(name = "quantita", required = false) Integer quantita) {
        Prodotto prodotto = entityManager.find(Prodotto.class, id);
        if (!prodotto.getTipo().getApribile() && aperto) {
            return new ResponseEntity<>(new String[]{"Prodotto non apribile"}, HttpStatus.BAD_REQUEST);
        } else {
            if (quantita != null) {
                prodotto.setQuantita(quantita);
            }
            if (aperto != null) {
                prodotto.setAperto(aperto);
            }
            // la logica di aperto+dataApertura e' gestita dal setter
            if (dataApertura != null) {
                prodotto.setDataApertura(dataApertura);
            }
            prodotto.generaDataScadenza();
            return new ResponseEntity<>(entityManager.merge(prodotto), HttpStatus.OK);
        }
    }
    
    /**
     * Legge gli errori di validazione e li restituisce in un array restituibile al client.
     * 
     * @param br
     * @return 
     */
    private List<String> formatBindingErrors(BindingResult br) {
        List<String> listaErrori = new LinkedList<>();
        for (ObjectError oe : br.getAllErrors()) {
            listaErrori.add(oe.getDefaultMessage());
        }
        return listaErrori;
    }
    
    /**
     * Metodo di ordinamento dove si usa CriteriaBuilder.
     * 
     * @param sortby Elenco campi per cui ordinare.
     * @param sortdirection Elenco direzione ordinamento (ASC/DESC/asc/desc); se la lunghezza dell'array non corrisponde a quella di sortby viene usato il primo valore.
     * @param cb
     * @param root
     * @return 
     */
    private List<Order> parseOrdinamento(String[] sortby, String[] sortdirection, CriteriaBuilder cb, Root root) {
        List<Order> ordinamenti = new LinkedList<>();
        if (sortby != null) {
            for (int s = 0; s < sortby.length; s++) {
                boolean asc = sortdirection == null ? true 
                        : sortdirection.length > s ? "ASC".equalsIgnoreCase(sortdirection[s])
                        : "ASC".equalsIgnoreCase(sortdirection[0]);
                ordinamenti.add(asc ? cb.asc(root.get(sortby[s])) : cb.desc(root.get(sortby[s])));
            }
        }
        return ordinamenti;
    }
}
