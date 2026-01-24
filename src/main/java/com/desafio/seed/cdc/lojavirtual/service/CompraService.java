package com.desafio.seed.cdc.lojavirtual.service;

import com.desafio.seed.cdc.lojavirtual.model.entity.Compra;
import com.desafio.seed.cdc.lojavirtual.model.entity.Estado;
import com.desafio.seed.cdc.lojavirtual.model.entity.Pais;
import com.desafio.seed.cdc.lojavirtual.model.entity.Pedido;
import com.desafio.seed.cdc.lojavirtual.model.vo.NovaCompraRequestVo;
import com.desafio.seed.cdc.lojavirtual.repository.CompraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompraService {

    private final CompraRepository compraRepository;

    private final PaisService paisService;

    private final EstadoService estadoService;

    public Compra createCompra(final NovaCompraRequestVo novaCompraVo, final Pedido pedido) {

        Compra novaCompra = builderCompra(novaCompraVo, pedido);

        return compraRepository.save(novaCompra);

    }

    public Compra builderCompra(final NovaCompraRequestVo novaCompraVo, final Pedido pedido) {
        Pais pais = paisService.getPaisExistente(novaCompraVo.getPaisId());
        Estado estado = novaCompraVo.getEstadoId() != null ? estadoService.getEstadoExistente(novaCompraVo.getEstadoId()) : null;

        return Compra.builder()
                .email(novaCompraVo.getEmail())
                .nome(novaCompraVo.getNome())
                .sobrenome(novaCompraVo.getSobrenome())
                .documento(novaCompraVo.getDocumento())
                .logradouro(novaCompraVo.getEndereco())
                .numero(novaCompraVo.getNumero())
                .cep(novaCompraVo.getCep())
                .bairro(novaCompraVo.getBairro())
                .complemento(novaCompraVo.getComplemento())
                .cidade(novaCompraVo.getCidade())
                .pais(pais)
                .estado(estado)
                .telefone(novaCompraVo.getTelefone())
                .pedido(pedido)
                .build();

    }

}
