package com.desafio.seed.cdc.lojavirtual.controller;

import com.desafio.seed.cdc.lojavirtual.model.entity.Autor;
import com.desafio.seed.cdc.lojavirtual.model.entity.Categoria;
import com.desafio.seed.cdc.lojavirtual.repository.AutorRepository;
import com.desafio.seed.cdc.lojavirtual.repository.CategoriaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;

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

}
