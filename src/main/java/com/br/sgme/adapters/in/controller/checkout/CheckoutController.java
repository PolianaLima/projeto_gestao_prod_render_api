package com.br.sgme.adapters.in.controller.checkout;


import com.br.sgme.adapters.in.controller.checkout.dto.CheckoutDto;
import com.br.sgme.domain.Checkout;
import com.br.sgme.port.in.CheckoutUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.br.sgme.utils.UrlCrossOrigin.URL_CROSS_ORIGIN;

@CrossOrigin(URL_CROSS_ORIGIN)
@RestController
@RequiredArgsConstructor
@RequestMapping("ckeckouts")
public class CheckoutController {

    private final CheckoutUseCase checkoutUseCase;

    @PostMapping("/abirNovoCheckout")
    public ResponseEntity<?> save(@RequestBody CheckoutDto checkoutDto, @RequestHeader("Authorization") String token) {
        Checkout checkout = Checkout.builder()
                .valorInicial(checkoutDto.getValorInicial())
                .build();
        checkoutUseCase.save(checkout, token);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/finalizarCheckout/{id}")
    public ResponseEntity<?> finalizarCheckout(@PathVariable UUID id, @RequestBody CheckoutDto checkoutDto,
                                               @RequestHeader("Authorization") String token) {
        Checkout checkout = Checkout.builder()
                .id(id.toString())
                .build();
        checkoutUseCase.finalizarCheckout(checkout, token);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public CheckoutDto getById(@PathVariable UUID id, @RequestHeader("Authorization") String token) {
        Checkout checkout = checkoutUseCase.getById((id).toString(), token);
        return CheckoutDto.to(checkout);
    }

    @GetMapping("/isCheckoutOpen")
    public boolean verificaCheckoutOpen(@RequestHeader("Authorization") String token) {
        return checkoutUseCase.existCheckoutOpen(token);
    }

    @GetMapping("/checkoutOpen")
    public CheckoutDto getCheckout(@RequestHeader("Authorization") String token) {
        Checkout checkout = checkoutUseCase.getCheckoutOpen(token);
        return CheckoutDto.toCheckout(checkout);
    }


}
