package com.desafio.seed.cdc.lojavirtual.controller;

import com.desafio.seed.cdc.lojavirtual.model.entity.Estado;
import com.desafio.seed.cdc.lojavirtual.model.entity.Livro;
import com.desafio.seed.cdc.lojavirtual.model.entity.Pais;
import com.desafio.seed.cdc.lojavirtual.model.vo.ItemRequestVo;
import com.desafio.seed.cdc.lojavirtual.model.vo.NovaCompraRequestVo;
import com.desafio.seed.cdc.lojavirtual.model.vo.NovoPedidoRequestVo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProcessaCompraControllerIT extends BaseControllerIT {

    private static final String BASE_URL_PROCESSA_COMPRA = "/processa-compra";

    @Nested
    @DisplayName("POST /processa-compra - 200 OK")
    class Success {

        @Test
        @DisplayName("Deve criar compra com sucesso")
        void deveCriarCompraComSucesso() {
            Pais pais = paisRepository.saveAndFlush(new Pais("Brasil"));
            Estado estado = estadoRepository.saveAndFlush(new Estado("Sao Paulo", pais));
            NovoPedidoRequestVo pedidoRequest = createNovoPedidoRequest();

            NovaCompraRequestVo vo = createNovaCompraRequest(pais.getId(), estado.getId(), pedidoRequest, null);

            HttpEntity<NovaCompraRequestVo> requestEntity = new HttpEntity<>(vo);
            ResponseEntity<String> response = restTemplate.exchange(BASE_URL_PROCESSA_COMPRA, HttpMethod.POST, requestEntity, String.class);

            assertNotNull(response);
            assertTrue(response.getStatusCode().is2xxSuccessful());
            assertTrue(response.getBody().contains("Compra efetuada"));

        }

        //TODO: criar compra usando cupom

    }

    @Nested
    @DisplayName("POST /processa-compra - 400 Bad Request")
    class BadRequest {
        //TODO: não criar compra com dados inválidos

    }

    private NovoPedidoRequestVo createNovoPedidoRequest() {
        Livro livro = createLivro();
        ItemRequestVo item = new ItemRequestVo(livro.getId(), (short) 1);

        return NovoPedidoRequestVo.builder()
                .total(livro.getPreco())
                .itens(List.of(item))
                .build();
    }

    private NovaCompraRequestVo createNovaCompraRequest(final Integer paisId, final Integer estadoId,
                                                        final NovoPedidoRequestVo pedido, final String codigCupom) {
        return new NovaCompraRequestVo("teste@email.com",
                "Joao", "Soares", "766.065.540-04", "Rua Fake", "2", "12345-123",
                "Jd. Teste", "Casa", "São Paulo", paisId, estadoId, "11 1234-1234",
                pedido, codigCupom);

    }

    @BeforeEach
    void beforeEach() {
        paisRepository.deleteAll();
    }
}
