package com.desafio.seed.cdc.lojavirtual.controller;

import com.desafio.seed.cdc.lojavirtual.exception.config.ErrorResponse;
import com.desafio.seed.cdc.lojavirtual.model.dto.AutorRequestDTO;
import com.desafio.seed.cdc.lojavirtual.model.dto.CategoriaRequestDTO;
import com.desafio.seed.cdc.lojavirtual.model.entity.Autor;
import com.desafio.seed.cdc.lojavirtual.model.entity.Categoria;
import com.desafio.seed.cdc.lojavirtual.repository.AutorRepository;
import com.desafio.seed.cdc.lojavirtual.repository.CategoriaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CategoriaControllerIT {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String URL_CATEGORIA = "/categoria";

    private static final String FINAL_MSG_NOME_JA_UTILIZADO = " já está sendo utilizado, por favor, escolha outro nome";

    @Test
    @DisplayName("Deve criar categoria se nome for válido")
    void deveCriarCategoriaSeNomeForValido() {
        String nome = "Teste";
        CategoriaRequestDTO dto = new CategoriaRequestDTO(nome);

        HttpEntity<CategoriaRequestDTO> requestEntity = new HttpEntity<>(dto);
        ResponseEntity<ErrorResponse> response = restTemplate.exchange(URL_CATEGORIA, HttpMethod.POST, requestEntity, ErrorResponse.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Boolean categoriaCriada = categoriaRepository.existsCategoriaByNome(nome);
        assertTrue(categoriaCriada);

    }

    @Test
    @DisplayName("Não deve criar categoria repetido")
    void naoDeveCriarCategoriaComNomeRepetido() {
        Categoria novaCategoria = createCategoria("Teste");

        CategoriaRequestDTO dto = new CategoriaRequestDTO(novaCategoria.getNome());

        HttpEntity<CategoriaRequestDTO> requestEntity = new HttpEntity<>(dto);
        ResponseEntity<ErrorResponse> response = restTemplate.exchange(URL_CATEGORIA, HttpMethod.POST, requestEntity, ErrorResponse.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        String msgEsperada = novaCategoria.getNome() + FINAL_MSG_NOME_JA_UTILIZADO;
        assertEquals(msgEsperada, response.getBody().getMessage());

    }

    @Test
    @DisplayName("Não deve criar categoria com nome inválido")
    void naoDeveCriarCategoriaComNomeInvalido() {
        CategoriaRequestDTO dto = new CategoriaRequestDTO("");

        HttpEntity<CategoriaRequestDTO> requestEntity = new HttpEntity<>(dto);
        ResponseEntity<ErrorResponse> response = restTemplate.exchange(URL_CATEGORIA, HttpMethod.POST, requestEntity, ErrorResponse.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("O nome da categoria é obrigatório", response.getBody().getFieldErrorsList().getFirst().getErrorMessage());

    }

    private Categoria createCategoria(final String nome) {
        Categoria categoria = Categoria.builder().nome(nome).build();
        categoria = categoriaRepository.save(categoria);
        assertNotNull(categoria.getId());

        return categoria;
    }

    @BeforeEach
    void beforeEach() {
        categoriaRepository.deleteAll();
    }

}
