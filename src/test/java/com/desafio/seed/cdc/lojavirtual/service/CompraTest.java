package com.desafio.seed.cdc.lojavirtual.service;

import com.desafio.seed.cdc.lojavirtual.exception.BusinessException;
import com.desafio.seed.cdc.lojavirtual.model.entity.Compra;
import com.desafio.seed.cdc.lojavirtual.model.entity.CupomDesconto;
import com.desafio.seed.cdc.lojavirtual.utils.MessageConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class CompraTest {

    @Test
    @DisplayName("Não deve aplicar um cupom se o mesmo estiver com validade expirada")
    void naoDeveAplicarCupomSeEstiverExpirado() {
        CupomDesconto cupom = CupomDesconto.builder().codigo("TESTE")
                .percentual(BigDecimal.TEN).validade(LocalDate.now().minusDays(1))
                .build();

        Compra compra = new Compra();
        BusinessException ex = assertThrows(BusinessException.class,
                () -> compra.aplicarCupom(cupom)
        );

        assertEquals(MessageConstants.CUPOM_EXPIRADO, ex.getMensagem());
    }

    @Test
    @DisplayName("Não deve aplicar um cupom se a compra já possuir um cupom")
    void naoDeveAplicarCupomSeACompraJaPossuirUm() {
        CupomDesconto cupom = CupomDesconto.builder().codigo("TESTE")
                .percentual(BigDecimal.TEN).validade(LocalDate.now().plusDays(1))
                .build();

        CupomDesconto cupomAplicado = CupomDesconto.builder().codigo("TESTE2")
                .percentual(BigDecimal.TEN).validade(LocalDate.now().plusDays(1))
                .build();

        Compra compra = Compra.builder().cupom(cupomAplicado).build();

        BusinessException ex = assertThrows(BusinessException.class,
                () -> compra.aplicarCupom(cupom)
        );

        assertEquals(MessageConstants.CUPOM_JA_APLICADO, ex.getMensagem());
    }

}
