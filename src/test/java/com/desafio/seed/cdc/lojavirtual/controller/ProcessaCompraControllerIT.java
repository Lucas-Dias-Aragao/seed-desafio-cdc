package com.desafio.seed.cdc.lojavirtual.controller;

import com.desafio.seed.cdc.lojavirtual.exception.config.ErrorResponse;
import com.desafio.seed.cdc.lojavirtual.model.entity.CupomDesconto;
import com.desafio.seed.cdc.lojavirtual.model.entity.Estado;
import com.desafio.seed.cdc.lojavirtual.model.entity.Livro;
import com.desafio.seed.cdc.lojavirtual.model.entity.Pais;
import com.desafio.seed.cdc.lojavirtual.model.vo.ItemRequestVo;
import com.desafio.seed.cdc.lojavirtual.model.vo.NovaCompraRequestVo;
import com.desafio.seed.cdc.lojavirtual.model.vo.NovoPedidoRequestVo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
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

        @Test
        @DisplayName("Deve criar compra aplicando cupom com sucesso")
        void deveCriarCompraAplicandoCupomComSucesso() {
            Pais pais = paisRepository.saveAndFlush(new Pais("Brasil"));
            Estado estado = estadoRepository.saveAndFlush(new Estado("Sao Paulo", pais));
            NovoPedidoRequestVo pedidoRequest = createNovoPedidoRequest();

            CupomDesconto cupom = createCupomDesconto("TESTE", BigDecimal.TEN, LocalDate.now().plusDays(1));

            NovaCompraRequestVo vo = createNovaCompraRequest(pais.getId(), estado.getId(), pedidoRequest, cupom.getCodigo());

            HttpEntity<NovaCompraRequestVo> requestEntity = new HttpEntity<>(vo);
            ResponseEntity<String> response = restTemplate.exchange(BASE_URL_PROCESSA_COMPRA, HttpMethod.POST, requestEntity, String.class);

            assertNotNull(response);
            assertTrue(response.getStatusCode().is2xxSuccessful());
            assertTrue(response.getBody().contains("Compra efetuada"));

        }

    }

    @Nested
    @DisplayName("POST /processa-compra - 400 Bad Request")
    class BadRequest {

        @Test
        @DisplayName("Não deve criar compra aplicando cupom se o mesmo já estiver expirado")
        void naoDeveCriarCompraAplicandoCupomExpirado() {
            Pais pais = paisRepository.saveAndFlush(new Pais("Brasil"));
            Estado estado = estadoRepository.saveAndFlush(new Estado("Sao Paulo", pais));
            NovoPedidoRequestVo pedidoRequest = createNovoPedidoRequest();

            CupomDesconto cupom = createCupomDesconto("TESTE", BigDecimal.TEN, LocalDate.now().minusDays(1));

            NovaCompraRequestVo vo = createNovaCompraRequest(pais.getId(), estado.getId(), pedidoRequest, cupom.getCodigo());

            HttpEntity<NovaCompraRequestVo> requestEntity = new HttpEntity<>(vo);
            ResponseEntity<ErrorResponse> response = restTemplate.exchange(BASE_URL_PROCESSA_COMPRA, HttpMethod.POST, requestEntity, ErrorResponse.class);

            assertNotNull(response);
            assertTrue(response.getStatusCode().is4xxClientError());
            assertTrue(response.getBody().getMessage().contains("Cupom inválido"));

        }

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
}
