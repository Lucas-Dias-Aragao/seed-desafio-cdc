package com.desafio.seed.cdc.lojavirtual.service;

import com.desafio.seed.cdc.lojavirtual.model.dto.SuccessResponse;
import com.desafio.seed.cdc.lojavirtual.model.entity.CupomDesconto;
import com.desafio.seed.cdc.lojavirtual.model.vo.CadastroCupomRequestVo;
import com.desafio.seed.cdc.lojavirtual.repository.CupomDescontoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CupomDescontoService {

    private final CupomDescontoRepository cupomDescontoRepository;

    public SuccessResponse cadastraCupomDesconto(final CadastroCupomRequestVo vo) {
        CupomDesconto novoCupom = CupomDesconto.builder().codigo(vo.getCodigo())
                .percentual(vo.getPercentual())
                .validade(vo.getValidade())
                .build();

        novoCupom = cupomDescontoRepository.save(novoCupom);

        return new SuccessResponse(novoCupom.getCodigo() + " cadastrado com sucesso!");
    }
}
