package com.br.sgme.usecase.service;

import com.br.sgme.adapters.out.bd.model.Usuario;
import com.br.sgme.config.exceptions.ErrorDataException;
import com.br.sgme.domain.Checkout;
import com.br.sgme.domain.enums.StatusCheckout;
import com.br.sgme.port.in.CheckoutUseCase;
import com.br.sgme.port.out.CheckoutAdapterDb;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class CheckoutUseCaseImpl implements CheckoutUseCase {

    private final CheckoutAdapterDb checkoutAdapterDb;
    private final UsuarioLogado usuarioLogado;

    @Override
    public void save(Checkout checkout, String token) {

        Usuario usuario = getUsuarioLogado(token);
        Checkout checkoutAberto = checkoutAdapterDb.findLastCheckoutByUsuarioId(usuario.getId());

        if (checkoutAberto == null) {
            Checkout checkoutSalvar = Checkout.builder()
                    .usuario(usuario)
                    .valorInicial(checkout.getValorInicial())
                    .status(StatusCheckout.ABERTO)
                    .dataCreated(LocalDate.now())
                    .build();

            checkoutAdapterDb.abrirCheckout(checkoutSalvar);
        } else {
            throw new ErrorDataException("Já existe um checkout aberto para este usuário");
        }

       /* if (checkoutAberto != null && checkoutAberto.getStatus().equals(StatusCheckout.ABERTO)){
            throw new ErrorDataException("Já existe um checkout aberto para este usuário");
        }*/


    }

    @Override
    public void finalizarCheckout(Checkout checkout, String token) {
        Usuario usuario = getUsuarioLogado(token);
        Checkout checkoutAberto = checkoutAdapterDb.findLastCheckoutByUsuarioId(usuario.getId());

        if (checkoutAberto == null || !checkoutAberto.getStatus().equals(StatusCheckout.ABERTO)) {
            throw new ErrorDataException("Não existe um checkout aberto para este usuário");
        }

        Checkout checkoutFinalizar = Checkout.builder()
                .id(checkoutAberto.getId())
                .usuario(usuario)
                .valorInicial(checkoutAberto.getValorInicial())
                .status(StatusCheckout.FECHADO)
                .dataCreated(checkoutAberto.getDataCreated())
                .dataClosed(LocalDate.now())
                .build();

        checkoutAdapterDb.finalizarCheckout(checkoutFinalizar);
    }

    @Override
    public List<Checkout> get(String token) {
        Usuario usuario = getUsuarioLogado(token);
        return new ArrayList<>(checkoutAdapterDb.findByUsuarioId(usuario.getId()));
    }

    @Override
    public Checkout getById(String id, String token) {
        Usuario usuario = getUsuarioLogado(token);
        return checkoutAdapterDb.findByIdAndUsuarioId(id, usuario.getId());
    }

    @Override
    public Checkout getCheckoutOpen(String token) {
        Usuario usuario = getUsuarioLogado(token);
        return checkoutAdapterDb.lastCheckout(usuario.getId());
    }

    @Override
    public Checkout lastCheckout(String token) {
        Usuario usuario = getUsuarioLogado(token);
        return checkoutAdapterDb.lastCheckout(usuario.getId());
    }

    @Override
    public boolean existCheckoutOpen(String token) {
       Usuario usuario = getUsuarioLogado(token);
        return checkoutAdapterDb.existsByCheckoutOpen(usuario.getId());
    }

    private Usuario getUsuarioLogado(String token) {
        return usuarioLogado.getUsuarioLogado(token);
    }
}
