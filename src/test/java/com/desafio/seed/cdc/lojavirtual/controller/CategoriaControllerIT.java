package com.desafio.seed.cdc.lojavirtual.controller;

import com.desafio.seed.cdc.lojavirtual.exception.config.ErrorResponse;
import com.desafio.seed.cdc.lojavirtual.model.vo.CategoriaRequestVo;
import com.desafio.seed.cdc.lojavirtual.model.entity.Categoria;
import com.desafio.seed.cdc.lojavirtual.utils.MessageConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CategoriaControllerIT extends BaseControllerIT {

    private static final String URL_CATEGORIA = "/categoria";

    private static final String FINAL_MSG_NOME_JA_UTILIZADO = " já está sendo utilizado, por favor, escolha outro nome";

    @Nested
    @DisplayName("POST /categoria - 200 OK")
    class Success {
        @Test
        @DisplayName("Deve criar categoria se nome for válido")
        void deveCriarCategoriaSeNomeForValido() {
            String nome = "Teste";
            CategoriaRequestVo dto = new CategoriaRequestVo(nome);

            HttpEntity<CategoriaRequestVo> requestEntity = new HttpEntity<>(dto);
            ResponseEntity<Void> response = restTemplate.exchange(URL_CATEGORIA, HttpMethod.POST, requestEntity, Void.class);

            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());

            Boolean categoriaCriada = categoriaRepository.existsCategoriaByNome(nome);
            assertTrue(categoriaCriada);

        }

    }

    @Nested
    @DisplayName("POST /categoria - 400 BAD REQUEST")
    class BadRequest {

        @Test
        @DisplayName("Não deve criar categoria repetido")
        void naoDeveCriarCategoriaComNomeRepetido() {
            Categoria novaCategoria = createCategoria("Teste");

            CategoriaRequestVo dto = new CategoriaRequestVo(novaCategoria.getNome());

            HttpEntity<CategoriaRequestVo> requestEntity = new HttpEntity<>(dto);
            ResponseEntity<ErrorResponse> response = restTemplate.exchange(URL_CATEGORIA, HttpMethod.POST, requestEntity, ErrorResponse.class);

            assertNotNull(response);
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

            String msgEsperada = novaCategoria.getNome() + FINAL_MSG_NOME_JA_UTILIZADO;
            assertEquals(msgEsperada, response.getBody().getMessage());

        }

        @Test
        @DisplayName("Não deve criar categoria com nome inválido")
        void naoDeveCriarCategoriaComNomeInvalido() {
            CategoriaRequestVo dto = new CategoriaRequestVo("");

            HttpEntity<CategoriaRequestVo> requestEntity = new HttpEntity<>(dto);
            ResponseEntity<ErrorResponse> response = restTemplate.exchange(URL_CATEGORIA, HttpMethod.POST, requestEntity, ErrorResponse.class);

            assertNotNull(response);
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertEquals(MessageConstants.NOME_OBRIGATORIO, response.getBody().getFieldErrors().get("nome"));

        }

    }

    @BeforeEach
    void beforeEach() {
        categoriaRepository.deleteAll();
    }

}
