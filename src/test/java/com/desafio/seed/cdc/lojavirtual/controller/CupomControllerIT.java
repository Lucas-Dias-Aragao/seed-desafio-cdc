package com.desafio.seed.cdc.lojavirtual.controller;

import com.desafio.seed.cdc.lojavirtual.exception.config.ErrorResponse;
import com.desafio.seed.cdc.lojavirtual.model.dto.SuccessResponse;
import com.desafio.seed.cdc.lojavirtual.model.vo.CadastroCupomRequestVo;
import com.desafio.seed.cdc.lojavirtual.repository.CupomRepository;
import com.desafio.seed.cdc.lojavirtual.utils.MessageConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CupomControllerIT extends BaseControllerIT {

    @Autowired
    private CupomRepository cupomRepository;

    private static final String URL_CUPOM = "/cupom";

    @Nested
    @DisplayName("POST /cupom - 200 OK")
    class Success {

        @Test
        @DisplayName("Deve criar cupom se dados forem válidos")
        void deveCriarCupomSeDadosForemValidos() {
            CadastroCupomRequestVo cupom = builderCupomRequest("CUPOMTESTE", 20, LocalDate.now().plusDays(1));

            HttpEntity<CadastroCupomRequestVo> requestEntity = new HttpEntity<>(cupom);
            ResponseEntity<SuccessResponse> response = restTemplate.exchange(URL_CUPOM, HttpMethod.POST, requestEntity, SuccessResponse.class);

            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            String respostaEsperada = cupom.getCodigo() + " cadastrado com sucesso!";
            assertEquals(respostaEsperada, response.getBody().getMessage());
        }

    }

    @Nested
    @DisplayName("POST /cupom - 400 BAD REQUEST")
    class BadRequest {

        @Test
        @DisplayName("Não deve criar cupom com código e percentual nulos")
        void naoDeveCriarCupomComCodigoEPercentualNulos() {
            CadastroCupomRequestVo cupom = builderCupomRequest(null, null, LocalDate.now().plusDays(1));

            HttpEntity<CadastroCupomRequestVo> requestEntity = new HttpEntity<>(cupom);
            ResponseEntity<ErrorResponse> response = restTemplate.exchange(URL_CUPOM, HttpMethod.POST, requestEntity, ErrorResponse.class);

            assertNotNull(response);
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

            assertEquals(2, response.getBody().getCountErrors());
            Map<String, String> fieldErros = response.getBody().getFieldErrors();
            assertEquals(MessageConstants.INFORME_CODIGO_CUPOM, fieldErros.get("codigo"));
            assertEquals(MessageConstants.PERCENTUAL_OBRIGATORIO, fieldErros.get("percentual"));

        }

        @Test
        @DisplayName("Não deve criar cupom com data de validade no passado")
        void naoDeveCriarCupomComDataValidadeNoPassado() {
            CadastroCupomRequestVo cupom = builderCupomRequest("CUPOMNOVO", 10, LocalDate.now().minusDays(1));

            HttpEntity<CadastroCupomRequestVo> requestEntity = new HttpEntity<>(cupom);
            ResponseEntity<ErrorResponse> response = restTemplate.exchange(URL_CUPOM, HttpMethod.POST, requestEntity, ErrorResponse.class);

            assertNotNull(response);
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

            Map<String, String> fieldErros = response.getBody().getFieldErrors();
            assertEquals(MessageConstants.DATA_DEVE_SER_FUTURO, fieldErros.get("validade"));

        }
    }

    private CadastroCupomRequestVo builderCupomRequest(final String codigoCupom, final Integer percentualDesconto, final LocalDate validade) {
        return CadastroCupomRequestVo.builder().codigo(codigoCupom).percentual(percentualDesconto).validade(validade).build();
    }

    @BeforeEach
    void beforeEach() {
        cupomRepository.deleteAll();
    }

}
