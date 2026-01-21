package com.desafio.seed.cdc.lojavirtual.controller;

import com.desafio.seed.cdc.lojavirtual.exception.config.ErrorResponse;
import com.desafio.seed.cdc.lojavirtual.model.vo.LivroRequestVo;
import com.desafio.seed.cdc.lojavirtual.model.dto.LivroResponseDTO;
import com.desafio.seed.cdc.lojavirtual.model.entity.Autor;
import com.desafio.seed.cdc.lojavirtual.model.entity.Categoria;
import com.desafio.seed.cdc.lojavirtual.model.entity.Livro;
import com.desafio.seed.cdc.lojavirtual.repository.LivroRepository;
import com.desafio.seed.cdc.lojavirtual.utils.MessageConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class LivroControllerIT extends BaseControllerIT {

    @Autowired
    protected LivroRepository livroRepository;

    private static final String URL_LIVRO = "/livros";

    private static final String MSG_AUTOR_NAO_ENCONTRADO = "Não foi encontrado Autor com o id informado.";

    private static final String MSG_CATEGORIA_NAO_ENCONTRADA = "Não foi encontrado Categoria com o id informado.";

    @Nested
    @DisplayName("POST /livros - 200 OK")
    class PostSuccess {

        @Test
        @DisplayName("Deve criar livro com sucesso se dados forem válidos")
        void deveCriarLivroSeDadosForemValidos() {
            Autor autor = createAutor("Jose", "jose@email.com");
            Categoria categoria = createCategoria("Categoria Fake");

            LivroRequestVo novoLivroDTO = LivroRequestVo.builder()
                    .titulo("Um livro criado")
                    .resumo("Esse livro deve ser criado com sucesso!!")
                    .sumario(".. sumário válido")
                    .preco(BigDecimal.valueOf(29.90))
                    .qtdPaginas((short) 101)
                    .isbn("1234-258-36")
                    .dataPublicacao(LocalDate.now().plusMonths(1))
                    .autorId(autor.getId())
                    .categoriaId(categoria.getId())
                    .build();

            HttpEntity<LivroRequestVo> requestEntity = new HttpEntity<>(novoLivroDTO);
            ResponseEntity<Void> response = restTemplate.exchange(URL_LIVRO, HttpMethod.POST, requestEntity, Void.class);

            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
        }
    }

    @Nested
    @DisplayName("POST /livros - 400 BAD REQUEST")
    class BadRequest {

        @Test
        @DisplayName("Não deve criar livro com sucesso se dados forem inválidos")
        void naoDeveCriarLivroSeDadosForemInvalidos() {
            Autor autor = createAutor("Jose", "jose@email.com");
            Categoria categoria = createCategoria("Categoria Fake");

            LivroRequestVo novoLivroDTO = LivroRequestVo.builder()
                    .titulo("")
                    .resumo("")
                    .preco(null)
                    .qtdPaginas(null)
                    .isbn("")
                    .dataPublicacao(null)
                    .autorId(autor.getId())
                    .categoriaId(categoria.getId())
                    .build();

            HttpEntity<LivroRequestVo> requestEntity = new HttpEntity<>(novoLivroDTO);
            ResponseEntity<ErrorResponse> response = restTemplate.exchange(URL_LIVRO, HttpMethod.POST, requestEntity, ErrorResponse.class);

            assertNotNull(response);
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

            assertEquals(6, response.getBody().getCountErrors());

            var fieldErrors = response.getBody().getFieldErrors();
            assertEquals(MessageConstants.TITULO_OBRIGATORIO, fieldErrors.get("titulo"));
            assertEquals(MessageConstants.PRECO_OBRIGATORIO, fieldErrors.get("preco"));
            assertEquals(MessageConstants.QTD_PAGINAS_OBRIGATORIA, fieldErrors.get("qtdPaginas"));
            assertEquals(MessageConstants.ISBN_OBRIGATORIO, fieldErrors.get("isbn"));
            assertEquals(MessageConstants.DATA_NAO_INFORMADA, fieldErrors.get("dataPublicacao"));
            assertEquals(MessageConstants.RESUMO_OBRIGATORIO, fieldErrors.get("resumo"));

        }

        @Test
        @DisplayName("Não deve criar livro se autor não existir")
        void naoDeveCriarLivroSeIdAutorForInexistente() {
            Categoria categoria = createCategoria("Categoria Fake");

            LivroRequestVo novoLivroDTO = LivroRequestVo.builder()
                    .titulo("Um livro criado")
                    .resumo("Esse livro deve ser criado com sucesso!!")
                    .sumario(".. sumário válido")
                    .preco(BigDecimal.valueOf(29.90))
                    .qtdPaginas((short) 101)
                    .isbn("1234-258-36")
                    .dataPublicacao(LocalDate.now().plusMonths(1))
                    .autorId(12300)
                    .categoriaId(categoria.getId())
                    .build();

            HttpEntity<LivroRequestVo> requestEntity = new HttpEntity<>(novoLivroDTO);
            ResponseEntity<ErrorResponse> response = restTemplate.exchange(URL_LIVRO, HttpMethod.POST, requestEntity, ErrorResponse.class);

            assertNotNull(response);
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertEquals(MSG_AUTOR_NAO_ENCONTRADO, response.getBody().getMessage());
        }

        @Test
        @DisplayName("Não deve criar livro se categoria não existir")
        void naoDeveCriarLivroSeIdCategoriaForInexistente() {
            Autor autor = createAutor("Jose", "jose@email.com");

            LivroRequestVo novoLivroDTO = LivroRequestVo.builder()
                    .titulo("Um livro criado")
                    .resumo("Esse livro deve ser criado com sucesso!!")
                    .sumario(".. sumário válido")
                    .preco(BigDecimal.valueOf(29.90))
                    .qtdPaginas((short) 101)
                    .isbn("1234-258-36")
                    .dataPublicacao(LocalDate.now().plusMonths(1))
                    .autorId(autor.getId())
                    .categoriaId(12300)
                    .build();

            HttpEntity<LivroRequestVo> requestEntity = new HttpEntity<>(novoLivroDTO);
            ResponseEntity<ErrorResponse> response = restTemplate.exchange(URL_LIVRO, HttpMethod.POST, requestEntity, ErrorResponse.class);

            assertNotNull(response);
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertEquals(MSG_CATEGORIA_NAO_ENCONTRADA, response.getBody().getMessage());
        }

    }

    @Nested
    @DisplayName("GET /livros - 200 OK")
    class GetSuccess {

        @Test
        @DisplayName("Deve retornar uma lista de livros")
        void deveRetornarUmaListaDeLivros() {
            Autor autor = createAutor("Jose", "jose@email.com");

            Livro livro = createLivro("Livro 1", "12344-85-695", autor);
            Livro livro2 = createLivro("Livro 2", "12345-85-695", autor);

            ResponseEntity<List<LivroResponseDTO>> response = restTemplate.exchange(URL_LIVRO, HttpMethod.GET,
                    null, new ParameterizedTypeReference<List<LivroResponseDTO>>() {}
            );

            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(2, response.getBody().size());
            assertEquals(livro.getTitulo(), response.getBody().getFirst().getTitulo());
            assertEquals(livro2.getTitulo(), response.getBody().getLast().getTitulo());

        }

        @Test
        @DisplayName("Deve retornar 404 Not Found ao buscar livro por id inexistente")
        void deveRetornarUmLivroAoBuscarPeloIdSeExistir() {
            StringBuilder url = new StringBuilder(URL_LIVRO);
            url.append("/").append(15000);

            ResponseEntity<ErrorResponse> response = restTemplate.exchange(url.toString(), HttpMethod.GET,
                    null, ErrorResponse.class);

            assertNotNull(response);
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertEquals(MessageConstants.LIVRO_NAO_ENCONTRADO, response.getBody().getMessage());

        }

    }

    @Nested
    @DisplayName("GET /livros - 404 NOT FOUND")
    class NotFound {

        @Test
        @DisplayName("Deve retornar detalhes do livro ao buscar por ID")
        void deveRetornarUmLivroAoBuscarPeloIdSeExistir() {
            Autor autor = createAutor("Jose", "jose@email.com");
            Livro livro = createLivro("Livro 1", "12344-85-695", autor);

            StringBuilder url = new StringBuilder(URL_LIVRO);
            url.append("/").append(livro.getId());

            ResponseEntity<LivroResponseDTO> response = restTemplate.exchange(url.toString(), HttpMethod.GET,
                    null, LivroResponseDTO.class);

            assertNotNull(response.getBody());
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(livro.getTitulo(), response.getBody().getTitulo());
            assertEquals(autor.getNome(), response.getBody().getAutor().getNome());

        }

    }

    private Livro createLivro(final String titulo, final String isbn, final Autor autor) {
        Categoria categoria = createCategoria("Categoria Fake");

        Livro livro = Livro.builder()
                .titulo(titulo)
                .resumo("Esse livro deve ser criado com sucesso!!")
                .sumario(".. sumário válido")
                .preco(BigDecimal.valueOf(29.90))
                .qtdPaginas((short) 101)
                .isbn(isbn)
                .dataPublicacao(LocalDate.now().plusMonths(1))
                .autor(autor)
                .categoria(categoria)
                .build();

        livro = livroRepository.save(livro);
        assertNotNull(livro.getId());
        return livro;
    }

    @BeforeEach
    void beforeEach() {
        livroRepository.deleteAll();
        autorRepository.deleteAll();
        categoriaRepository.deleteAll();
    }

}
