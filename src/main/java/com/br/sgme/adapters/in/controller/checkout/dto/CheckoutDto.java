package com.br.sgme.adapters.in.controller.checkout.dto;


import com.br.sgme.adapters.in.controller.vendas.dto.VendaDto;
import com.br.sgme.domain.Checkout;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@Builder
public class CheckoutDto {

    private String id;

    @JsonProperty("usuario_id")
    private String idUsuario;

    @JsonProperty("valor_inicial")
    private Double valorInicial;

    private List<VendaDto> vendaDtos;

    @JsonProperty("status")
    private String status;


    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonProperty("data_created")
    private LocalDate dataCreated;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonProperty("date_closed")
    private LocalDate dataClosed;

    public static CheckoutDto to(Checkout checkout) {

        List<VendaDto> vendasDtos = checkout.getVendas().stream()
                .map(VendaDto::to)
                .collect(Collectors.toList());

        return CheckoutDto.builder()
                .id(checkout.getId())
                .idUsuario(checkout.getUsuario().getId())
                .valorInicial(checkout.getValorInicial())
                .vendaDtos(vendasDtos)
                .status(checkout.getStatus().name())
                .dataCreated(checkout.getDataCreated())
                .dataClosed(checkout.getDataClosed())
                .build();

    }

    public static CheckoutDto toCheckout(Checkout checkout){
        return CheckoutDto.builder()
                .id(checkout.getId())
                .idUsuario(checkout.getUsuario().getId())
                .valorInicial(checkout.getValorInicial())
                .status(checkout.getStatus().name())
                .dataCreated(checkout.getDataCreated())
                .dataClosed(checkout.getDataClosed())
                .build();
    }
}
