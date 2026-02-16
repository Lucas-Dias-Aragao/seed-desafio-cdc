package com.desafio.seed.cdc.lojavirtual.controller;

import com.desafio.seed.cdc.lojavirtual.model.entity.Autor;
import com.desafio.seed.cdc.lojavirtual.model.entity.Categoria;
import com.desafio.seed.cdc.lojavirtual.model.entity.Livro;
import com.desafio.seed.cdc.lojavirtual.model.factory.ItemPedidoFactory;
import com.desafio.seed.cdc.lojavirtual.repository.AutorRepository;
import com.desafio.seed.cdc.lojavirtual.repository.CategoriaRepository;
import com.desafio.seed.cdc.lojavirtual.repository.EstadoRepository;
import com.desafio.seed.cdc.lojavirtual.repository.ItemPedidoRepository;
import com.desafio.seed.cdc.lojavirtual.repository.LivroRepository;
import com.desafio.seed.cdc.lojavirtual.repository.PaisRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class BaseControllerIT {

    @Autowired
    protected TestRestTemplate restTemplate;

    @Autowired
    protected CategoriaRepository categoriaRepository;

    @Autowired
    protected AutorRepository autorRepository;

    @Autowired
    protected PaisRepository paisRepository;

    @Autowired
    protected EstadoRepository estadoRepository;

    @Autowired
    protected LivroRepository livroRepository;

    @Autowired
    protected ItemPedidoRepository itemPedidoRepository;

    protected Categoria createCategoria(final String nome) {
        Categoria categoria = Categoria.builder().nome(nome).build();
        categoria = categoriaRepository.save(categoria);
        assertNotNull(categoria.getId());

        return categoria;
    }

    protected Autor createAutor(final String nome, String email) {
        Autor autor = new Autor(nome, email, "Autor criado para testes");
        autor = autorRepository.save(autor);
        assertNotNull(autor.getId());

        return autor;
    }

    protected Livro createLivro(final String titulo, final String isbn, final Autor autor) {
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

    protected Livro createLivro() {
        Categoria categoria = createCategoria("Categoria Fake");
        Autor autor = createAutor("Joselino Jr", "joselino@email.com");

        Livro livro = Livro.builder()
                .titulo("Fake livro")
                .resumo("Fake resumo")
                .sumario(".. sumário válido")
                .preco(BigDecimal.valueOf(29.90))
                .qtdPaginas((short) 101)
                .isbn("156456889")
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
        itemPedidoRepository.deleteAll();
        livroRepository.deleteAll();
        autorRepository.deleteAll();
    }

}
