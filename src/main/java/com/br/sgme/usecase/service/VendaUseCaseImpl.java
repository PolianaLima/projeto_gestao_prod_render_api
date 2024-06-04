package com.br.sgme.usecase.service;

import com.br.sgme.adapters.out.bd.model.Usuario;
import com.br.sgme.config.exceptions.RecursoNaoEncontradoException;
import com.br.sgme.domain.Checkout;
import com.br.sgme.domain.ItemVenda;
import com.br.sgme.domain.Venda;
import com.br.sgme.domain.enums.FormasPagamento;
import com.br.sgme.domain.enums.StatusCheckout;
import com.br.sgme.domain.enums.StatusVenda;
import com.br.sgme.port.in.VendaUseCase;
import com.br.sgme.port.out.CheckoutAdapterDb;
import com.br.sgme.port.out.ItemVendaAdapterDb;
import com.br.sgme.port.out.VendaAdapterDb;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class VendaUseCaseImpl implements VendaUseCase {

    private final VendaAdapterDb vendaAdapterDb;
    private final ItemVendaAdapterDb itemVendaAdapterDb;
    private final UsuarioLogado usuarioLogado;
    private final CheckoutAdapterDb checkoutAdapterDb;

    @Override
    public void save(Venda venda, List<ItemVenda> itensVenda, String token) {

        Usuario usuario = getUsuarioLogado(token);

        Checkout checkout = checkoutAdapterDb.findLastCheckoutByUsuarioId(usuario.getId());

        if (checkout == null) {
            throw new RecursoNaoEncontradoException("Nenhum checkout aberto, abra um novo checkout para realizar uma venda");
        }

       /* String statusCheckout = checkout.getStatus().toString();

        if (statusCheckout.equals(StatusCheckout.FECHADO.toString())) {
            throw new RecursoNaoEncontradoException("Checkout finalizado, abra um novo checkout para realizar uma venda");
        }
*/
        Venda vendaSave = Venda.builder()
                .checkout(checkout)
                .usuario(usuario)
                .valorTotal(calculaValorTotal(itensVenda))
                .descontoTotal(calculaDescontoTotal(itensVenda))
                .pagamento(FormasPagamento.valueOf(venda.getPagamento().name().toUpperCase()))
                .status(StatusVenda.FINALIZADA)
                .dataCreated(LocalDateTime.now())
                .build();

        Venda vendaSaved = vendaAdapterDb.save(vendaSave);

        //Corrigir a logica para mapear os tens e salvar no banco
        List<ItemVenda> itensVendaSave = itensVenda.stream()
                .map(itemVenda -> ItemVenda.builder()
                        .venda(vendaSaved)
                        .idProduto(itemVenda.getIdProduto())
                        .quantidade(itemVenda.getQuantidade())
                        .valorProduto(itemVenda.getValorProduto())
                        .descontoProduto(itemVenda.getDescontoProduto())
                        .dataCreated(LocalDateTime.now())
                        .build())
                .collect(Collectors.toList());

        itemVendaAdapterDb.saveAll(itensVendaSave);

    }

    @Override
    public List<Venda> findByUsuarioId(String idUsuario) {

        Usuario  usuario = getUsuarioLogado(idUsuario);
        return new ArrayList<>(vendaAdapterDb.get(usuario.getId()));
    }

    @Override
    public Venda findIdAndUsuarioId(String id, String idUsuario) {
        Usuario usuario = getUsuarioLogado(idUsuario);
        return vendaAdapterDb.getById(id, usuario.getId());
    }

    @Override
    public void cancelarVenda(Venda venda, String token) {

        Usuario usuario = getUsuarioLogado(token);
        Venda vendaSelecionada = vendaAdapterDb.getById(venda.getId(), usuario.getId());

        Venda vendaCancelada = Venda.builder()
                .id(vendaSelecionada.getId())
                .usuario(vendaSelecionada.getUsuario())
                .checkout(vendaSelecionada.getCheckout())
                .valorTotal(vendaSelecionada.getValorTotal())
                .descontoTotal(vendaSelecionada.getDescontoTotal())
                .pagamento(vendaSelecionada.getPagamento())
                .status(StatusVenda.CANCELADA)
                .dataCreated(vendaSelecionada.getDataCreated())
                .build();
        vendaAdapterDb.cancelarVenda(vendaCancelada);
    }

    @Override
    public List<Venda> allVendasByCheckoutId(String string, String token) {
        Usuario usuario = getUsuarioLogado(token);
        return new ArrayList<>(vendaAdapterDb.allVendasByCheckoutId(string, usuario.getId()));
    }


    private Double calculaValorTotal(List<ItemVenda> itemVendas) {
        return itemVendas.stream()
                .map(itemVendaDto -> itemVendaDto.getValorProduto() * itemVendaDto.getQuantidade())
                .reduce(0.0, Double::sum);
    }

    private Double calculaDescontoTotal(List<ItemVenda> itemVendas) {
        return itemVendas.stream()
                .map(itemVendaDto -> itemVendaDto.getDescontoProduto() * itemVendaDto.getQuantidade())
                .reduce(0.0, Double::sum);
    }

    private Usuario getUsuarioLogado(String token) {
        return usuarioLogado.getUsuarioLogado(token);
    }
}
