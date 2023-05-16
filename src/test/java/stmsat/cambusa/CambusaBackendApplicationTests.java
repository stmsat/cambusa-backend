package stmsat.cambusa;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc()
class CambusaBackendApplicationTests {
    
    @Autowired
    private MockMvc mockMvc;

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
                .param("tipo", UUID.randomUUID().toString())
                .param("posizione", UUID.randomUUID().toString())
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

}
