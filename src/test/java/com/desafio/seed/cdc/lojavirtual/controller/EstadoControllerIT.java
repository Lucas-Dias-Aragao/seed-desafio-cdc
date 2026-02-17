package com.desafio.seed.cdc.lojavirtual.controller;

import com.desafio.seed.cdc.lojavirtual.exception.config.ErrorResponse;
import com.desafio.seed.cdc.lojavirtual.model.entity.Pais;
import com.desafio.seed.cdc.lojavirtual.model.vo.EstadoRequestVo;
import com.desafio.seed.cdc.lojavirtual.utils.MessageConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EstadoControllerIT extends BaseControllerIT {

    private static final String URL_ESTADO = "/estado";

    @Nested
    @DisplayName("POST /estado - 200 OK")
    class Success {

        @Test
        @DisplayName("Deve criar estado com sucesso")
        void deveCriarEstadoComSucesso() {
            Pais pais = paisRepository.save(new Pais("Brasil"));

            EstadoRequestVo vo = new EstadoRequestVo();
            vo.setNome("Sao Paulo");
            vo.setIdPais(pais.getId());

            HttpEntity<EstadoRequestVo> requestEntity = new HttpEntity<>(vo);
            ResponseEntity<ResponseEntity> response = restTemplate.exchange(URL_ESTADO, HttpMethod.POST, requestEntity, ResponseEntity.class);

            assertNotNull(response);
            assertTrue(response.getStatusCode().is2xxSuccessful());
        }

    }

    @Nested
    @DisplayName("POST /estado - 400 BadRequest")
    class BadRequest {

        @Test
        @DisplayName("Não deve criar estado com dados inválidos (nome e pais)")
        void naoDeveCriarEstadoComDadosInvalidos() {

            EstadoRequestVo vo = new EstadoRequestVo();
            vo.setNome(null);
            vo.setIdPais(null);

            HttpEntity<EstadoRequestVo> requestEntity = new HttpEntity<>(vo);
            ResponseEntity<ErrorResponse> response = restTemplate.exchange(URL_ESTADO, HttpMethod.POST, requestEntity, ErrorResponse.class);

            assertNotNull(response);
            assertTrue(response.getStatusCode().is4xxClientError());
            assertEquals(2, response.getBody().getCountErrors());
            assertEquals(MessageConstants.PAIS_OBRIGATORIO, response.getBody().getFieldErrors().get("idPais"));
            assertEquals(MessageConstants.NOME_OBRIGATORIO, response.getBody().getFieldErrors().get("nome"));
        }
    }

}
