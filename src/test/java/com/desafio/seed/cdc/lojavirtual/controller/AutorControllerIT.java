package com.desafio.seed.cdc.lojavirtual.controller;

import com.desafio.seed.cdc.lojavirtual.exception.config.ErrorResponse;
import com.desafio.seed.cdc.lojavirtual.model.vo.AutorRequestVo;
import com.desafio.seed.cdc.lojavirtual.model.entity.Autor;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AutorControllerIT extends BaseControllerIT {

    private static final String FINAL_MSG_EMAIL_JA_SENDO_UTILIZADO = " já está sendo utilizado, por favor, escolha outro email";

    private static final String URL_AUTOR = "/autores";

    @Nested
    @DisplayName("POST /autores - 200 OK")
    class Success {

        @Test
        @DisplayName("Deve criar autor se dados forem válidos")
        void deveCriarAutoresSeDadosForemValidos() {
            AutorRequestVo novoAutor = builderAutorRequest("Autor da Silva", "autor@email.com");

            HttpEntity<AutorRequestVo> requestEntity = new HttpEntity<>(novoAutor);
            ResponseEntity<Void> response = restTemplate.exchange(URL_AUTOR, HttpMethod.POST, requestEntity, Void.class);

            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
        }

    }

    @Nested
    @DisplayName("POST /autores - 400 BAD REQUEST")
    class BadRequest {

        @Test
        @DisplayName("Não deve criar autores com e-mail repetidos")
        void naoDeveCriarAutoresComEmailsIguais() {
            Autor autor = createAutor("Juninho", "juninho@email.com");

            AutorRequestVo novoAutor = builderAutorRequest("Autor dois", autor.getEmail());

            HttpEntity<AutorRequestVo> requestEntity = new HttpEntity<>(novoAutor);
            ResponseEntity<ErrorResponse> response = restTemplate.exchange(URL_AUTOR, HttpMethod.POST, requestEntity, ErrorResponse.class);

            assertNotNull(response);
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

            String msgEsperada = novoAutor.getEmail() + FINAL_MSG_EMAIL_JA_SENDO_UTILIZADO;
            assertEquals(msgEsperada, response.getBody().getMessage());

        }

        @Test
        @DisplayName("Não deve criar autores se dados forem inválidos")
        void naoDeveCriarAutoresComDadosInvalidos() {
            AutorRequestVo novoAutor = builderAutorRequest("", "");

            HttpEntity<AutorRequestVo> requestEntity = new HttpEntity<>(novoAutor);
            ResponseEntity<ErrorResponse> response = restTemplate.exchange(URL_AUTOR, HttpMethod.POST, requestEntity, ErrorResponse.class);

            assertNotNull(response);
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

            assertEquals(2, response.getBody().getCountErrors());
            assertEquals(MessageConstants.NOME_OBRIGATORIO, response.getBody().getFieldErrors().get("nome"));
            assertEquals(MessageConstants.EMAIL_OBRIGATORIO, response.getBody().getFieldErrors().get("email"));
        }

    }

    private AutorRequestVo builderAutorRequest(final String nome, final String email) {
        return AutorRequestVo.builder().nome(nome).email(email).descricao("Autor criado para testes").build();
    }

    @BeforeEach
    void beforeEach() {
        autorRepository.deleteAll();
    }

}
