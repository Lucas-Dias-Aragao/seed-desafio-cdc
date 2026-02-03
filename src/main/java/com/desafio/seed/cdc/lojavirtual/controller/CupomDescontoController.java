package com.desafio.seed.cdc.lojavirtual.controller;

import com.desafio.seed.cdc.lojavirtual.model.dto.SuccessResponse;
import com.desafio.seed.cdc.lojavirtual.model.vo.CadastroCupomRequestVo;
import com.desafio.seed.cdc.lojavirtual.service.CupomDescontoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cupom")
public class CupomDescontoController {

    private final CupomDescontoService cupomDescontoService;

    @PostMapping
    public SuccessResponse cadastraCupom(@Valid @RequestBody final CadastroCupomRequestVo vo) {
        return cupomDescontoService.cadastraCupomDesconto(vo);
    }

}
