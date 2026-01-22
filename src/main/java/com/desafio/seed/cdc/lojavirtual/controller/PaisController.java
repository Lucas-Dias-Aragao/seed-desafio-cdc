package com.desafio.seed.cdc.lojavirtual.controller;

import com.desafio.seed.cdc.lojavirtual.model.vo.PaisRequestVo;
import com.desafio.seed.cdc.lojavirtual.service.PaisService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/pais")
public class PaisController {

    private final PaisService paisService;

    @PostMapping
    public ResponseEntity<?> createPais(@Valid @RequestBody PaisRequestVo dto) {
        return paisService.createPais(dto);
    }

}
