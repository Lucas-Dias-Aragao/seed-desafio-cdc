package com.desafio.seed.cdc.lojavirtual.controller;

import com.desafio.seed.cdc.lojavirtual.model.dto.DetalheCompraResponse;
import com.desafio.seed.cdc.lojavirtual.model.entity.Compra;
import com.desafio.seed.cdc.lojavirtual.model.entity.CupomDesconto;
import com.desafio.seed.cdc.lojavirtual.model.entity.Estado;
import com.desafio.seed.cdc.lojavirtual.model.entity.ItemPedido;
import com.desafio.seed.cdc.lojavirtual.model.entity.Pais;
import com.desafio.seed.cdc.lojavirtual.model.entity.Pedido;
import com.desafio.seed.cdc.lojavirtual.model.vo.NovaCompraRequestVo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
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

            Compra compra = createCompra(pedido, null);

            String url = BASE_URL_DETALHE_COMPRA + "/" + compra.getId();
            ResponseEntity<DetalheCompraResponse> response = restTemplate.exchange(url, HttpMethod.GET, null, DetalheCompraResponse.class);

            assertNotNull(response);
            assertTrue(response.getStatusCode().is2xxSuccessful());
            assertTrue(pedido.getTotal().compareTo(response.getBody().getTotal()) == 0);
            assertEquals(compra.getNome() + " " + compra.getSobrenome(), response.getBody().getNomeCompletoComprador());
            assertFalse(response.getBody().getCupomAplicado());

        }

    }

    @Nested
    @DisplayName("POST /detalhe-compra - 400 BadRequest")
    class BadRequest {

    }

    private Compra createCompra(final Pedido pedido, final CupomDesconto cupomDesconto) {
        Pais pais = createPais();
        Estado estado = createEstado(pais);

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
