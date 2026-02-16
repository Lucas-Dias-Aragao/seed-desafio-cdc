package com.desafio.seed.cdc.lojavirtual.service;

import com.desafio.seed.cdc.lojavirtual.exception.BusinessException;
import com.desafio.seed.cdc.lojavirtual.model.entity.Pais;
import com.desafio.seed.cdc.lojavirtual.model.vo.PaisRequestVo;
import com.desafio.seed.cdc.lojavirtual.repository.PaisRepository;
import com.desafio.seed.cdc.lojavirtual.utils.MessageConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("Test")
public class PaisServiceTest {

    @Mock
    private PaisRepository paisRepository;

    @InjectMocks
    private PaisService paisService;

    @Nested
    @DisplayName("200 OK")
    class Success {

        @Test
        @DisplayName("Deve criar um pais com sucesso")
        void deveRetornarPaisExistenteComSucesso() {
            Pais pais = new Pais(1,"Brasil");

            when(paisRepository.findById(1)).thenReturn(Optional.of(pais));

            Pais resultado = paisService.getPaisExistente(1);
            assertNotNull(resultado);
            assertEquals("Brasil", resultado.getNome());
        }
    }

    @Nested
    @DisplayName("400 Bad Request")
    class Fail {

        @Test
        @DisplayName("Deve lançar exception quando pais não existir")
        void deveRetornarPaisExistenteComSucesso() {
            when(paisRepository.findById(99)).thenReturn(Optional.empty());

            BusinessException ex = assertThrows(BusinessException.class,
                    () -> paisService.getPaisExistente(99)
            );

            assertEquals(MessageConstants.PAIS_NAO_ENCONTRADO, ex.getMensagem());
            assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
            verify(paisRepository).findById(99);
        }
    }

}
