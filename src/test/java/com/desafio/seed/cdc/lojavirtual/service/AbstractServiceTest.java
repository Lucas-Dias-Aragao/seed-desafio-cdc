package com.desafio.seed.cdc.lojavirtual.service;

import com.desafio.seed.cdc.lojavirtual.repository.EstadoRepository;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("Test")
public class AbstractServiceTest {

    @Mock
    protected EstadoRepository estadoRepository;

}
