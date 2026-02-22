package com.desafio.seed.cdc.lojavirtual.service;

import com.desafio.seed.cdc.lojavirtual.exception.BusinessException;
import com.desafio.seed.cdc.lojavirtual.model.context.PaisEstadoContext;
import com.desafio.seed.cdc.lojavirtual.model.entity.Estado;
import com.desafio.seed.cdc.lojavirtual.model.entity.Pais;
import com.desafio.seed.cdc.lojavirtual.repository.PaisRepository;
import com.desafio.seed.cdc.lojavirtual.utils.MessageConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaisServiceTest extends AbstractServiceTest {

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

        @Test
        @DisplayName("Deve retornar pais e estado com sucesso")
        void deveRetornarPaisEEstadoComSucesso() {
            Pais pais = new Pais("Brasil");
            pais.setId(1);

            Estado estado = new Estado("Sao Paulo", pais);
            estado.setId(1);

            when(paisRepository.estadoValidoParaPais(pais.getId(), estado.getId())).thenReturn(true);
            when(paisRepository.findById(pais.getId())).thenReturn(Optional.of(pais));
            when(estadoRepository.findById(estado.getId())).thenReturn(Optional.of(estado));

            PaisEstadoContext paisEstadoContext = paisService.validarEObterPaisEstado(pais.getId(), estado.getId());

            assertNotNull(paisEstadoContext);
            assertEquals("Brasil", paisEstadoContext.getPais().getNome());
            assertEquals("Sao Paulo", paisEstadoContext.getEstado().getNome());

        }

    }

    @Nested
    @DisplayName("400 Bad Request")
    class Fail {

        @Test
        @DisplayName("Deve lançar exception quando pais não existir")
        void deveLancarExceptionQuandoPaisNaoExistir() {
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
