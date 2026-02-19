package com.desafio.seed.cdc.lojavirtual.controller;

import com.desafio.seed.cdc.lojavirtual.model.dto.DetalheCompraResponse;
import com.desafio.seed.cdc.lojavirtual.model.entity.Compra;
import com.desafio.seed.cdc.lojavirtual.model.entity.CupomDesconto;
import com.desafio.seed.cdc.lojavirtual.model.entity.Estado;
import com.desafio.seed.cdc.lojavirtual.model.entity.ItemPedido;
import com.desafio.seed.cdc.lojavirtual.model.entity.Pais;
import com.desafio.seed.cdc.lojavirtual.model.entity.Pedido;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DetalheCompraControllerIT extends BaseControllerIT {

    private static final String BASE_URL_DETALHE_COMPRA = "/detalhe-compra";

    @Nested
    @DisplayName("POST /detalhe-compra - 200 OK")
    class Success {

        @Test
        @DisplayName("Deve retornar detalhes da compra com sucesso")
        void retornaDetalhesDaCompra() {

            Pedido pedido = createPedido();
            ItemPedido itemPedido = createItemPedido((short) 1, pedido);

            pedido.setItens(List.of(itemPedido));
            pedido.setTotal(itemPedido.getLivro().getPreco());
            pedidoRepository.saveAndFlush(pedido);

            Compra compra = createCompra(pedido, null, true);

            String url = BASE_URL_DETALHE_COMPRA + "/" + compra.getId();
            ResponseEntity<DetalheCompraResponse> response = restTemplate.exchange(url, HttpMethod.GET, null, DetalheCompraResponse.class);

            assertNotNull(response);
            assertTrue(response.getStatusCode().is2xxSuccessful());
            assertTrue(pedido.getTotal().compareTo(response.getBody().getTotal()) == 0);
            assertEquals(compra.getNome() + " " + compra.getSobrenome(), response.getBody().getNomeCompletoComprador());
            assertFalse(response.getBody().getCupomAplicado());

        }

        @Test
        @DisplayName("Deve retornar detalhes da compra com sucesso calculando total do pedido com desconto de cupom válido")
        void retornaDetalhesDaCompraComDescontoDoCupomValido() {

            Pedido pedido = createPedido();
            ItemPedido itemPedido = createItemPedido((short) 1, pedido);

            pedido.setItens(List.of(itemPedido));
            pedido.setTotal(itemPedido.getLivro().getPreco());
            pedidoRepository.saveAndFlush(pedido);

            CupomDesconto cupomDesconto = createCupomDesconto("TESTE", BigDecimal.valueOf(20), LocalDate.now().plusDays(1));
            Compra compra = createCompra(pedido, cupomDesconto, true);

            String url = BASE_URL_DETALHE_COMPRA + "/" + compra.getId();
            ResponseEntity<DetalheCompraResponse> response = restTemplate.exchange(url, HttpMethod.GET, null, DetalheCompraResponse.class);

            assertNotNull(response);
            assertTrue(response.getStatusCode().is2xxSuccessful());
            assertTrue(response.getBody().getCupomAplicado());

            BigDecimal desconto = cupomDesconto.getPercentual().divide(BigDecimal.valueOf(100)).multiply(pedido.getTotal());
            BigDecimal valorEsperadoComDesconto = pedido.getTotal().subtract(desconto);
            assertTrue(valorEsperadoComDesconto.compareTo(response.getBody().getValorFinalComDesconto()) == 0);

        }

        @Test
        @DisplayName("Deve retornar detalhes da compra com sucesso sem Estado")
        void retornaDetalhesDaCompraSemEstado() {

            Pedido pedido = createPedido();
            ItemPedido itemPedido = createItemPedido((short) 1, pedido);

            pedido.setItens(List.of(itemPedido));
            pedido.setTotal(itemPedido.getLivro().getPreco());
            pedidoRepository.saveAndFlush(pedido);

            Compra compra = createCompra(pedido, null, false);

            String url = BASE_URL_DETALHE_COMPRA + "/" + compra.getId();
            ResponseEntity<DetalheCompraResponse> response = restTemplate.exchange(url, HttpMethod.GET, null, DetalheCompraResponse.class);

            assertNotNull(response);
            assertTrue(response.getStatusCode().is2xxSuccessful());
            assertTrue(response.getBody().getEnderecoCompleto()
                    .contains(compra.getCidade() + " - " + compra.getPais().getNome()));

        }

    }

    @Nested
    @DisplayName("POST /detalhe-compra - 400 BadRequest")
    class BadRequest {

    }

    private Compra createCompra(final Pedido pedido, final CupomDesconto cupomDesconto, final Boolean estadoInformado) {
        Pais pais = createPais();
        Estado estado = estadoInformado ? createEstado(pais) : null;

        Compra compra = Compra.builder()
                .email("teste@email.com").nome("Joao").sobrenome("Soares")
                .documento("766.065.540-04").logradouro("Rua Fake")
                .numero("2").cep("12345-123").bairro("Jd. Teste")
                .complemento("Casa").cidade("São Paulo").pais(pais).estado(estado)
                .telefone("11 1234-1234")
                .pedido(pedido).cupom(cupomDesconto)
                .build();

        compra = compraRepository.save(compra);
        assertNotNull(compra.getId());
        return compra;
    }

}
