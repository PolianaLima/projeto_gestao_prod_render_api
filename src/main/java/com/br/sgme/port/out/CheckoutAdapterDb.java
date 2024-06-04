package com.br.sgme.port.out;

import com.br.sgme.domain.Checkout;

import java.util.List;

public interface CheckoutAdapterDb {
    Checkout abrirCheckout(Checkout checkout);
    Checkout finalizarCheckout(Checkout checkout);
    List<Checkout> findByUsuarioId(String usuarioId);
    Checkout findByIdAndUsuarioId(String id, String usuarioId);

    Checkout findLastCheckoutByUsuarioId(String usuarioId);

    Checkout lastCheckout(String usuarioId);

    boolean existsByCheckoutOpen(String usuarioId);
}
