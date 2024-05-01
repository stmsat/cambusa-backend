package stmsat.cambusa;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.UUID;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import stmsat.cambusa.entity.Posizione;
import stmsat.cambusa.entity.Prodotto;
import stmsat.cambusa.entity.Tipo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc()
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CambusaBackendApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void contextLoads() {
    }

    @Test
    void testListaTipi() throws Exception {
        mockMvc.perform(get("/cambusa/tipi")).andExpect(status().isOk());
    }

    @Test
    void testListaPosizioni() throws Exception {
        mockMvc.perform(get("/cambusa/posizioni")).andExpect(status().isOk());
    }

    @Test
    void testListaProdotti() throws Exception {
        mockMvc.perform(get("/cambusa/prodotti")).andExpect(status().isOk());
    }

    @Test
    void testListaProdottiFiltrata() throws Exception {
        mockMvc.perform(get("/cambusa/prodotti")
                .param("name", "testname")
                .param("tipo", "")
                .param("posizione", "")
                .param("dataScadenzaLt", "2024-12-31")
                .param("dataScadenzaGt", "2022-01-01")
                .param("dataScadenzaGenerataLt", "2024-12-31")
                .param("dataScadenzaGenerataGt", "2022-01-01")
                .param("sortby", "name,tipo,posizione,dataScadenza,dataScadenzaGenerata")
        ).andExpect(status().isOk());
    }

    @Test
    void testTipoPerIdNotFound() throws Exception {
        mockMvc.perform(get("/cambusa/tipi/" + UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    void testTipoPerBadId() throws Exception {
        mockMvc.perform(get("/cambusa/tipi/malformedUUID")).andExpect(status().isBadRequest());
    }

    @Test
    void testPosizionePerIdNotFound() throws Exception {
        mockMvc.perform(get("/cambusa/posizioni/" + UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    void testPosizionePerBadId() throws Exception {
        mockMvc.perform(get("/cambusa/posizioni/malformedUUID")).andExpect(status().isBadRequest());
    }

    @Test
    void testProdottoPerIdNotFound() throws Exception {
        mockMvc.perform(get("/cambusa/prodotti/" + UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    void testProdottoPerBadId() throws Exception {
        mockMvc.perform(get("/cambusa/prodotti/malformedUUID")).andExpect(status().isBadRequest());
    }

    /**
     * Tipo usato per test inserimento e delete.
     */
    private static Tipo tipoTest;

    /**
     * Posizione usata per test inserimento e delete.
     */
    private static Posizione posizioneTest;

    /**
     * Prodotto usato per test inserimento e delete.
     */
    private static Prodotto prodottoTest;

    @Test
    @Order(1)
    void testTipoInsert() throws Exception {
        Tipo tipo = new Tipo("tipo di test", true, 2, true, 1);
        MvcResult result = mockMvc.perform(
                put("/cambusa/tipi")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tipo)))
                .andExpect(status().isOk())
                .andReturn();
        tipoTest = this.objectMapper.readValue(result.getResponse().getContentAsString(), Tipo.class);
    }

    @Test
    @Order(1)
    void testPosizioneInsert() throws Exception {
        Posizione posizione = new Posizione("posizione di test");
        MvcResult result = mockMvc.perform(
                put("/cambusa/posizioni")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(posizione)))
                .andExpect(status().isOk())
                .andReturn();
        posizioneTest = this.objectMapper.readValue(result.getResponse().getContentAsString(), Posizione.class);
    }

    @Test
    @Order(2)
    void testProdottoInsert() throws Exception {
        Prodotto prodotto = new Prodotto("prodotto di test", LocalDate.now(), tipoTest, posizioneTest, null, false, null);
        MvcResult result = mockMvc.perform(
                put("/cambusa/prodotti")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(prodotto)))
                .andExpect(status().isOk())
                .andReturn();
        prodottoTest = this.objectMapper.readValue(result.getResponse().getContentAsString(), Prodotto.class);
    }

    @Test
    @Order(3)
    void testProdottoPatch() throws Exception {
        mockMvc.perform(
                patch("/cambusa/prodotti/" + prodottoTest.getId().toString())
                        .param("aperto", "true")
                        .param("dataApertura", "2023-03-03")
                        .param("quantita", "2"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(4)
    void testTipoChange() throws Exception {
        tipoTest.setApribile(false);
        tipoTest.setDataScadenzaPreferibile(false);
        mockMvc.perform(
                put("/cambusa/tipi")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tipoTest)))
                .andExpect(status().isOk());
    }

    /**
     * Imposta lo stato di apertura al prodotto ma il prodotto non &egrave; apribile.
     * 
     * @throws Exception 
     */
    @Test
    @Order(5)
    void testProdottoPatchWithError() throws Exception {
        mockMvc.perform(
                patch("/cambusa/prodotti/" + prodottoTest.getId().toString())
                        .param("aperto", "true")
                        .param("dataApertura", "2023-03-03"))
                .andExpect(status().isBadRequest());
    }

    /**
     * Delete del tipo con vincolo foreign key deve fallire.
     *
     * @throws Exception
     */
    @Test
    @Order(5)
    void testTipoDeleteGivingError() throws Exception {
        mockMvc.perform(delete("/cambusa/tipi/" + tipoTest.getId().toString())).andExpect(status().isConflict());
    }

    /**
     * Delete della posizione con vincolo foreign key deve fallire.
     *
     * @throws Exception
     */
    @Test
    @Order(5)
    void testPosizioneDeleteGivingError() throws Exception {
        mockMvc.perform(delete("/cambusa/posizioni/" + posizioneTest.getId().toString())).andExpect(status().isConflict());
    }

    @Test
    @Order(6)
    void testProdottoDelete() throws Exception {
        mockMvc.perform(delete("/cambusa/prodotti/" + prodottoTest.getId().toString())).andExpect(status().isOk());
    }

    @Test
    @Order(7)
    void testTipoDelete() throws Exception {
        mockMvc.perform(delete("/cambusa/tipi/" + tipoTest.getId().toString())).andExpect(status().isOk());
    }

    @Test
    @Order(7)
    void testPosizioneDelete() throws Exception {
        mockMvc.perform(delete("/cambusa/posizioni/" + posizioneTest.getId().toString())).andExpect(status().isOk());
    }
}
