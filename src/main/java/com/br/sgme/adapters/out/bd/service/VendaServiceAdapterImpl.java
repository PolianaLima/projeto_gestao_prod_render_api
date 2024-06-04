package com.br.sgme.adapters.out.bd.service;

import com.br.sgme.adapters.out.bd.model.CheckoutEntityBd;
import com.br.sgme.adapters.out.bd.model.ItemVendaEntityBd;
import com.br.sgme.adapters.out.bd.model.Usuario;
import com.br.sgme.adapters.out.bd.model.VendaEntityDb;
import com.br.sgme.adapters.out.bd.repository.VendaRepository;
import com.br.sgme.config.exceptions.RecursoNaoEncontradoException;
import com.br.sgme.domain.Checkout;
import com.br.sgme.domain.ItemVenda;
import com.br.sgme.domain.Venda;
import com.br.sgme.port.out.VendaAdapterDb;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VendaServiceAdapterImpl implements VendaAdapterDb {

    private final VendaRepository vendaRepository;

    @Override
    public Venda save(Venda venda) {
        VendaEntityDb vendaEntityDb = from(venda);
        VendaEntityDb save = vendaRepository.save(vendaEntityDb);
        return toVenda(save);
    }

    @Override
    public Venda cancelarVenda(Venda venda) {
        VendaEntityDb vendaEntityDb = from(venda);
        VendaEntityDb save = vendaRepository.save(vendaEntityDb);
        return toVenda(save);
    }

    @Override
    public List<Venda> get(String idUsuario) {

        List<VendaEntityDb> byUsuarioId = vendaRepository.findAllByUsuarioId(idUsuario);
        return byUsuarioId.stream()
                .map(this::toVenda)
                .collect(Collectors.toList());
    }

    @Override
    public Venda getById(String idVenda, String idUsuario) {

       VendaEntityDb vendaEntityDb = vendaRepository.findByIdAndUsuarioId(idVenda, idUsuario).orElseThrow(
                ()->new RecursoNaoEncontradoException("Venda nao encontrada"));
        return toVenda(vendaEntityDb);
    }

    @Override
    public List<Venda> allVendasByCheckoutId(String idCheckout, String idUsuario) {
        List<VendaEntityDb> allVendasByCheckoutId = vendaRepository.findAllByCheckoutId(idCheckout, idUsuario);
        return allVendasByCheckoutId.stream()
                .map(this:: toVenda)
                .collect(Collectors.toList());
    }


    private VendaEntityDb from(Venda venda) {
        List<ItemVenda> itens = venda.getItens();
        if (itens == null) {
            itens = new ArrayList<>();
        }

        return VendaEntityDb.builder()
                .id(venda.getId())
                .usuario(Usuario.builder()
                        .id(venda.getUsuario().getId())
                        .build())
                .checkout(CheckoutEntityBd.builder()
                        .id(venda.getCheckout().getId())
                        .build())
                .itens(itens.stream()
                        .map(this::fromItem)
                        .collect(Collectors.toList()))
                .pagamento(venda.getPagamento())
                .valorTotal(venda.getValorTotal())
                .descontoTotal(venda.getDescontoTotal())
                .status(venda.getStatus())
                .dataCreated(venda.getDataCreated())
                .build();
    }

    private ItemVendaEntityBd fromItem(ItemVenda item) {
        return ItemVendaEntityBd.builder()
                .id(item.getId())
                .idProduto(item.getIdProduto())
                .venda(VendaEntityDb.builder()
                        .id(item.getVenda().getId())
                        .build())
                .quantidade(item.getQuantidade())
                .descontoProduto(item.getDescontoProduto())
                .build();
    }

    private Venda toVenda(VendaEntityDb vendaEntityDb){
        return Venda.builder()
                .id(vendaEntityDb.getId())
                .usuario(Usuario.builder()
                        .id(vendaEntityDb.getUsuario().getId())
                        .build())
                .checkout(Checkout.builder()
                        .id(vendaEntityDb.getCheckout().getId())
                        .build())
                .itens(vendaEntityDb.getItens().stream()
                        .map(this::toItem)
                        .collect(Collectors.toList()))
                .pagamento(vendaEntityDb.getPagamento())
                .valorTotal(vendaEntityDb.getValorTotal())
                .descontoTotal(vendaEntityDb.getDescontoTotal())
                .status(vendaEntityDb.getStatus())
                .dataCreated(vendaEntityDb.getDataCreated())
                .build();
    }

    private ItemVenda toItem(ItemVendaEntityBd itemVendaEntityBd){
        return ItemVenda.builder()
                .id(itemVendaEntityBd.getId())
                .idProduto(itemVendaEntityBd.getIdProduto())
                .valorProduto(itemVendaEntityBd.getValorProduto())
                .venda(Venda.builder()
                        .id(itemVendaEntityBd.getVenda().getId())
                        .build())
                .quantidade(itemVendaEntityBd.getQuantidade())
                .descontoProduto(itemVendaEntityBd.getDescontoProduto())
                .dataCreated(itemVendaEntityBd.getDataCreated())
                .build();
    }

}
