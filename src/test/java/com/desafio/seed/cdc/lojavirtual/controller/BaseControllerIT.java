package com.desafio.seed.cdc.lojavirtual.controller;

import com.desafio.seed.cdc.lojavirtual.model.entity.Autor;
import com.desafio.seed.cdc.lojavirtual.model.entity.Categoria;
import com.desafio.seed.cdc.lojavirtual.model.entity.CupomDesconto;
import com.desafio.seed.cdc.lojavirtual.model.entity.Estado;
import com.desafio.seed.cdc.lojavirtual.model.entity.ItemPedido;
import com.desafio.seed.cdc.lojavirtual.model.entity.Livro;
import com.desafio.seed.cdc.lojavirtual.model.entity.Pais;
import com.desafio.seed.cdc.lojavirtual.model.entity.Pedido;
import com.desafio.seed.cdc.lojavirtual.model.factory.ItemPedidoFactory;
import com.desafio.seed.cdc.lojavirtual.repository.AutorRepository;
import com.desafio.seed.cdc.lojavirtual.repository.CategoriaRepository;
import com.desafio.seed.cdc.lojavirtual.repository.CompraRepository;
import com.desafio.seed.cdc.lojavirtual.repository.CupomDescontoRepository;
import com.desafio.seed.cdc.lojavirtual.repository.EstadoRepository;
import com.desafio.seed.cdc.lojavirtual.repository.ItemPedidoRepository;
import com.desafio.seed.cdc.lojavirtual.repository.LivroRepository;
import com.desafio.seed.cdc.lojavirtual.repository.PaisRepository;
import com.desafio.seed.cdc.lojavirtual.repository.PedidoRepository;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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

    @Autowired
    protected CupomDescontoRepository cupomDescontoRepository;

    @Autowired
    protected CompraRepository compraRepository;

    @Autowired
    protected PedidoRepository pedidoRepository;

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

    public CupomDesconto createCupomDesconto(final String codigo, final BigDecimal percentual, final LocalDate validade) {
        CupomDesconto cupom = CupomDesconto.builder().codigo(codigo)
                .percentual(percentual)
                .validade(validade)
                .build();
        cupom = cupomDescontoRepository.save(cupom);

        assertNotNull(cupom.getId());
        return cupom;
    }

    protected Pais createPais() {
        Pais pais = paisRepository.saveAndFlush(new Pais("Brasil"));
        assertNotNull(pais.getId());

        return pais;
    }

    protected Estado createEstado(final Pais pais) {
        Estado estado = estadoRepository.saveAndFlush(new Estado("Sao Paulo", pais));
        assertNotNull(estado.getId());

        return estado;
    }

    protected ItemPedido createItemPedido(final Short quantidade, final Pedido pedido) {
        Livro livro = createLivro();
        ItemPedido item = ItemPedido.builder().livro(livro).quantidade(quantidade).pedido(pedido).build();
        item = itemPedidoRepository.save(item);

        assertNotNull(item.getId());
        return item;

    }

    protected Pedido createPedido() {
        Pedido pedido = Pedido.builder().build();
        pedido = pedidoRepository.save(pedido);

        assertNotNull(pedido.getId());
        return pedido;
    }



    @BeforeEach
    void beforeEach() {
        itemPedidoRepository.deleteAll();
        compraRepository.deleteAll();
        pedidoRepository.deleteAll();
        livroRepository.deleteAll();
        autorRepository.deleteAll();
        cupomDescontoRepository.deleteAll();
        estadoRepository.deleteAll();
        paisRepository.deleteAll();

    }

}
