package com.br.sgme.port.in;

import com.br.sgme.domain.Checkout;

import java.util.List;

public interface CheckoutUseCase {
    void save(Checkout checkout, String token);

    void finalizarCheckout(Checkout checkout,String token);

    List<Checkout> get(String token);

    Checkout getById(String id, String token);

    Checkout getCheckoutOpen(String token);

    Checkout lastCheckout(String token);

    boolean existCheckoutOpen(String token);
}
