package com.br.sgme.adapters.out.bd.service;

import com.br.sgme.adapters.out.bd.model.CheckoutEntityBd;
import com.br.sgme.adapters.out.bd.model.ItemVendaEntityBd;
import com.br.sgme.adapters.out.bd.model.Usuario;
import com.br.sgme.adapters.out.bd.model.VendaEntityDb;
import com.br.sgme.adapters.out.bd.repository.CheckoutRepository;
import com.br.sgme.config.exceptions.RecursoNaoEncontradoException;
import com.br.sgme.domain.Checkout;
import com.br.sgme.domain.ItemVenda;
import com.br.sgme.domain.Venda;
import com.br.sgme.port.out.CheckoutAdapterDb;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CheckoutServiceAdapterImpl implements CheckoutAdapterDb {
    private final CheckoutRepository checkoutRepository;

    @Override
    public Checkout abrirCheckout(Checkout checkout) {
        CheckoutEntityBd checkoutEntityBd = from(checkout);
        checkoutRepository.save(checkoutEntityBd);
        return toCheckout(checkoutEntityBd);
    }

    @Override
    public Checkout finalizarCheckout(Checkout checkout) {
        CheckoutEntityBd checkoutEntityBd = from(checkout);
        checkoutRepository.save(checkoutEntityBd);
        return toCheckout(checkoutEntityBd);
    }

    @Override
    public List<Checkout> findByUsuarioId(String usuarioId) {
        List<CheckoutEntityBd> byUsuarioId = checkoutRepository.findAllByUsuarioId(usuarioId);
        return byUsuarioId.stream()
                .map(this::toCheckout)
                .collect(Collectors.toList());
    }

    @Override
    public Checkout findByIdAndUsuarioId(String id, String usuarioId) {
        CheckoutEntityBd checkoutEntityBd = checkoutRepository.findByIdAndUsuarioId(id, usuarioId).orElseThrow(
                () -> new RecursoNaoEncontradoException("Checkout não encontrado")
        );

        return toCheckout(checkoutEntityBd);
    }

    @Override
    public Checkout findLastCheckoutByUsuarioId(String usuarioId) {

        List<CheckoutEntityBd> checkoutEntityBds = checkoutRepository.findAllByUsuarioIdOpen(usuarioId);

        if(checkoutEntityBds.isEmpty()){
            return null;
        }

        /*CheckoutEntityBd checkoutEntityBd = checkoutRepository.findLastCheckout(usuarioId).orElseThrow(
                () -> new RecursoNaoEncontradoException("Checkout não encontrado")
        );*/


        return toCheckout(checkoutEntityBds.get(0));
    }

    @Override
    public Checkout lastCheckout(String usuarioId) {
        Optional<CheckoutEntityBd> optionalCheckoutEntityBd = checkoutRepository.findLastCheckout(usuarioId);

        if (optionalCheckoutEntityBd == null || optionalCheckoutEntityBd.isEmpty()) {
            throw new RecursoNaoEncontradoException("Checkout não encontrado");
        }

        CheckoutEntityBd checkoutEntityBd = optionalCheckoutEntityBd.get();

        if (checkoutEntityBd == null) {
            throw new RecursoNaoEncontradoException("Checkout não encontrado");
        }

        return toCheckout(checkoutEntityBd);
    }

    @Override
    public boolean existsByCheckoutOpen(String usuarioId) {
        return checkoutRepository.existsByCheckoutOpen(usuarioId);
    }

    private CheckoutEntityBd from(Checkout checkout) {

        return CheckoutEntityBd.builder()
                .id(checkout.getId())
                .usuario(Usuario.builder()
                        .id(checkout.getUsuario().getId())
                        .build())
                .valorInicial(checkout.getValorInicial())
                /*.vendas(checkout.getVendas().stream()
                        .map(this::fromVenda)
                        .collect(Collectors.toList()))*/
                .status(checkout.getStatus())
                .dataCreated(checkout.getDataCreated())
                .dataClosed(checkout.getDataClosed())
                .build();
    }

    private Checkout toCheckout(CheckoutEntityBd checkoutEntityBd){
        return Checkout.builder()
                .id(checkoutEntityBd.getId())
                .usuario(Usuario.builder()
                        .id(checkoutEntityBd.getUsuario().getId())
                        .build())
                .valorInicial(checkoutEntityBd.getValorInicial())
                /*.vendas(checkoutEntityBd.getVendas().stream()
                        .map(this::toVenda)
                        .collect(Collectors.toList()))*/
                .status(checkoutEntityBd.getStatus())
                .dataCreated(checkoutEntityBd.getDataCreated())
                .dataClosed(checkoutEntityBd.getDataClosed())
                .build();
    }


    private VendaEntityDb fromVenda(Venda venda) {
        return VendaEntityDb.builder()
                .id(venda.getId())
                .usuario(Usuario.builder()
                        .id(venda.getUsuario().getId())
                        .build())
                .checkout(CheckoutEntityBd.builder()
                        .id(venda.getCheckout().getId())
                        .build())
                .itens(venda.getItens().stream()
                        .map(this::fromItem)
                        .collect(Collectors.toList()))
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
                .build();
    }

    private ItemVenda toItem(ItemVendaEntityBd itemVendaEntityBd){
        return ItemVenda.builder()
                .id(itemVendaEntityBd.getId())
                .idProduto(itemVendaEntityBd.getIdProduto())
                .venda(Venda.builder()
                        .id(itemVendaEntityBd.getVenda().getId())
                        .build())
                .quantidade(itemVendaEntityBd.getQuantidade())
                .descontoProduto(itemVendaEntityBd.getDescontoProduto())
                .build();
    }



}
