package com.desafio.seed.cdc.lojavirtual.service;

import com.desafio.seed.cdc.lojavirtual.exception.BusinessException;
import com.desafio.seed.cdc.lojavirtual.model.context.PaisEstadoContext;
import com.desafio.seed.cdc.lojavirtual.model.dto.DetalheCompraResponse;
import com.desafio.seed.cdc.lojavirtual.model.entity.Compra;
import com.desafio.seed.cdc.lojavirtual.model.entity.CupomDesconto;
import com.desafio.seed.cdc.lojavirtual.model.entity.Pedido;
import com.desafio.seed.cdc.lojavirtual.model.factory.CompraFactory;
import com.desafio.seed.cdc.lojavirtual.model.vo.NovaCompraRequestVo;
import com.desafio.seed.cdc.lojavirtual.repository.CompraRepository;
import com.desafio.seed.cdc.lojavirtual.repository.CupomDescontoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CompraService {

    private final CompraRepository compraRepository;
    private final CupomDescontoRepository cupomDescontoRepository;

    public Compra createCompra(final NovaCompraRequestVo novaCompraVo, final Pedido pedido, final PaisEstadoContext context) {

        Compra novaCompra = CompraFactory.create(novaCompraVo, pedido, context.getPais(), context.getEstado());

        adicionaPossivelDesconto(novaCompraVo.getCodigoCupom(), novaCompra);

        return compraRepository.save(novaCompra);

    }

    private void adicionaPossivelDesconto(final String codigoCupom, final Compra compra) {
        if(codigoCupom == null) {
            return;
        }

        CupomDesconto cupom = cupomDescontoRepository.getCupomByCodigo(codigoCupom)
                .orElseThrow(() -> new BusinessException("Cupom não encontrado", HttpStatus.NOT_FOUND));

        compra.aplicarCupom(cupom);

    }

    public DetalheCompraResponse getDetalheCompra(final Integer idCompra) {
       DetalheCompraResponse dto = compraRepository.findDetalheCompraById(idCompra);

       dto.setNomeCompletoComprador(formataNomeComprador(dto));
       dto.setCupomAplicado(Objects.nonNull(dto.getCodigoCupom()));
       dto.setValorFinalComDesconto(calculaValorFinal(dto.getTotal(), dto.getPercentualDesconto()));

       return dto;
    }

    private String formataNomeComprador(DetalheCompraResponse dto) {
        return dto.getNomeComprador() + " " + dto.getSobrenomeComprador();
    }

    private BigDecimal calculaValorFinal(final BigDecimal total, final BigDecimal percentualDesconto) {
        if(Objects.isNull(total) || Objects.isNull(percentualDesconto)) return null;

        BigDecimal desconto = percentualDesconto.divide(BigDecimal.valueOf(100)).multiply(total);
        return total.subtract(desconto);
    }
}
