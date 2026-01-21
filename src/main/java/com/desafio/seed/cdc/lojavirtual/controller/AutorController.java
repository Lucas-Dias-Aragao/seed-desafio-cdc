package com.desafio.seed.cdc.lojavirtual.controller;

import com.desafio.seed.cdc.lojavirtual.model.vo.AutorRequestVo;
import com.desafio.seed.cdc.lojavirtual.service.AutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/autores")
public class AutorController {

    private final AutorService autorService;

    @PostMapping
    public ResponseEntity cadastraNovoAutor(@Valid @RequestBody AutorRequestVo dto) {
        return autorService.createAutor(dto);
    }

}
