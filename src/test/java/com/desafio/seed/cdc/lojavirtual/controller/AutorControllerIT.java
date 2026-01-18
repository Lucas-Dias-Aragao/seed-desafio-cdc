package com.desafio.seed.cdc.lojavirtual.controller;

import com.desafio.seed.cdc.lojavirtual.exception.config.ErrorResponse;
import com.desafio.seed.cdc.lojavirtual.exception.config.FieldErrors;
import com.desafio.seed.cdc.lojavirtual.model.dto.AutorRequestDTO;
import com.desafio.seed.cdc.lojavirtual.model.entity.Autor;
import com.desafio.seed.cdc.lojavirtual.repository.AutorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AutorControllerIT {

    @Autowired
    protected AutorRepository autorRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String URL_AUTOR = "/autores";

    @Test
    @DisplayName("Não deve criar autores com e-mail repetidos")
    void naoDeveCriarAutoresComEmailsIguais() {
        Autor autor = createAutor("Juninho", "juninho@email.com");

        AutorRequestDTO novoAutor = builderAutorRequest("Autor dois", autor.getEmail());

        HttpEntity<AutorRequestDTO> requestEntity = new HttpEntity<>(novoAutor);
        ResponseEntity<ErrorResponse> response = restTemplate.exchange(URL_AUTOR, HttpMethod.POST, requestEntity, ErrorResponse.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Já existe autor cadastrado com esse e-mail", response.getBody().getMessage());

    }

    @Test
    @DisplayName("Não deve criar autores se dados forem inválidos")
    void naoDeveCriarAutoresComDadosInvalidos() {
       AutorRequestDTO novoAutor = builderAutorRequest("", "");

        HttpEntity<AutorRequestDTO> requestEntity = new HttpEntity<>(novoAutor);
        ResponseEntity<ErrorResponse> response = restTemplate.exchange(URL_AUTOR, HttpMethod.POST, requestEntity, ErrorResponse.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        List<FieldErrors> errors = response.getBody().getFieldErrorsList();
        assertEquals(2, response.getBody().getCountErrors());
    }

    @Test
    @DisplayName("Deve criar autor se dados forem válidos")
    void deveCriarAutoresSeDadosForemValidos() {
        AutorRequestDTO novoAutor = builderAutorRequest("Autor da Silva", "autor@email.com");

        HttpEntity<AutorRequestDTO> requestEntity = new HttpEntity<>(novoAutor);
        ResponseEntity<ErrorResponse> response = restTemplate.exchange(URL_AUTOR, HttpMethod.POST, requestEntity, ErrorResponse.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    private AutorRequestDTO builderAutorRequest(final String nome, final String email) {
        return AutorRequestDTO.builder().nome(nome).email(email).descricao("Autor criado para testes").build();
    }

    private Autor createAutor(final String nome, String email) {
        Autor autor = new Autor(nome, email, "Autor criado para testes");
        autor = autorRepository.save(autor);
        assertNotNull(autor.getId());

        return autor;
    }

    @BeforeEach
    void beforeEach() {
        autorRepository.deleteAll();
    }

}
