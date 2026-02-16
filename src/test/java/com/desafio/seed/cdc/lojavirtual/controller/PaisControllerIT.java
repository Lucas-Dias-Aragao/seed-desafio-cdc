package com.desafio.seed.cdc.lojavirtual.controller;

import com.desafio.seed.cdc.lojavirtual.exception.config.ErrorResponse;
import com.desafio.seed.cdc.lojavirtual.model.entity.Pais;
import com.desafio.seed.cdc.lojavirtual.model.vo.PaisRequestVo;
import com.desafio.seed.cdc.lojavirtual.repository.PaisRepository;
import com.desafio.seed.cdc.lojavirtual.utils.MessageConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class PaisControllerIT extends BaseControllerIT {

    @Autowired
    protected TestRestTemplate restTemplate;

    private static final String URL_PAIS = "/pais";

    @Nested
    @DisplayName("POST /pais - 200 OK")
    class Success {

        @Test
        @DisplayName("Deve criar pais com sucesso")
        void deveCriarPaisComSucesso() {
            PaisRequestVo vo = new PaisRequestVo("Brasil");

            HttpEntity<PaisRequestVo> requestEntity = new HttpEntity<>(vo);
            ResponseEntity<Void> response = restTemplate.exchange(URL_PAIS, HttpMethod.POST, requestEntity, Void.class);

            assertNotNull(response);
            assertTrue(response.getStatusCode().is2xxSuccessful());
        }

    }

    @Nested
    @DisplayName("POST /pais - 400 Bad Request")
    class BadRequest {

        @Test
        @DisplayName("Não Deve criar pais com nome inválido")
        void naoDeveCriarPaisNomeInvalido() {
            PaisRequestVo vo = new PaisRequestVo(null);

            HttpEntity<PaisRequestVo> requestEntity = new HttpEntity<>(vo);
            ResponseEntity<ErrorResponse> response = restTemplate.exchange(URL_PAIS, HttpMethod.POST, requestEntity, ErrorResponse.class);

            assertNotNull(response);
            assertTrue(response.getStatusCode().is4xxClientError());
            assertEquals(MessageConstants.NOME_OBRIGATORIO, response.getBody().getFieldErrors().get("nome"));
        }

        @Test
        @DisplayName("Não Deve criar pais com nome repetido")
        void naoDeveCriarPaisNomeRepetido() {
            String nomePais = "Brasil";

            paisRepository.saveAndFlush(new Pais(nomePais));

            PaisRequestVo vo = new PaisRequestVo(nomePais);

            HttpEntity<PaisRequestVo> requestEntity = new HttpEntity<>(vo);
            ResponseEntity<ErrorResponse> response = restTemplate.exchange(URL_PAIS, HttpMethod.POST, requestEntity, ErrorResponse.class);

            assertNotNull(response);
            assertTrue(response.getStatusCode().is4xxClientError());
            String msgEsperada =  nomePais + " já está sendo utilizado, por favor, escolha outro nome";
            assertEquals(msgEsperada, response.getBody().getMessage());
        }

    }

    @BeforeEach
    void beforeEach() {
        paisRepository.deleteAll();
    }

}
