package com.desafio.seed.cdc.lojavirtual.service;

import com.desafio.seed.cdc.lojavirtual.exception.BusinessException;
import com.desafio.seed.cdc.lojavirtual.model.vo.CategoriaRequestVo;
import com.desafio.seed.cdc.lojavirtual.utils.MessageConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@SpringBootTest
public class CategoriaServiceTest {

    @Autowired
    private CategoriaService categoriaService;

    @Test
    @DisplayName("Deve lançar exception se o nome da categoria for nula")
    void deveLancarExceptionSeNomeDaCategoriaForNull() {
        CategoriaRequestVo vo = new CategoriaRequestVo(null);

        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> categoriaService.createCategoria(vo)
        );

        assertEquals(ex.getMensagem(), MessageConstants.NOME_OBRIGATORIO);
    }

    @Test
    @DisplayName("Deve lançar exception se o nome da categoria for vazio")
    void deveLancarExceptionSeNomeCategoriaIsBlank() {
        CategoriaRequestVo vo = new CategoriaRequestVo("");

        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> categoriaService.createCategoria(vo)
        );

        assertEquals(ex.getMensagem(), MessageConstants.NOME_OBRIGATORIO);
    }

}
