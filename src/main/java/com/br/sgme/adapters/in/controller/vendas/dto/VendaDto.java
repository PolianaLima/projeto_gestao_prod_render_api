package com.br.sgme.adapters.in.controller.vendas.dto;

import com.br.sgme.adapters.out.bd.model.Usuario;
import com.br.sgme.domain.Checkout;
import com.br.sgme.domain.Venda;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VendaDto {
    private String id;

    @JsonProperty("usuario_id")
    private String idUsuario;

    @JsonProperty("checkout_id")
    private String idCheckout;

    @JsonProperty("desconto_total")
    private Double descontoTotal;

    @JsonProperty("valor_total")
    private Double valorTotal;

    private String status;

    private List<ItemVendaDto> itemVendaDtos;

    @JsonProperty("forma_pagamento")
    private String formaPagamento;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonProperty("data_created")
    private LocalDateTime dataCreated;



    public static VendaDto to(Venda venda) {

        List<ItemVendaDto> itensDto = venda.getItens().stream()
                .map(ItemVendaDto::to) // Converte cada item de venda para DTO
                .collect(Collectors.toList());

        return VendaDto.builder()
                .id(venda.getId())
                .idUsuario(venda.getUsuario().getId())
                .idCheckout(venda.getCheckout().getId())
                .itemVendaDtos(itensDto)
                .descontoTotal(venda.getDescontoTotal())
                .valorTotal(venda.getValorTotal())
                .formaPagamento(venda.getPagamento().name())
                .status(venda.getStatus().name())
                .dataCreated(venda.getDataCreated())
                .build();
    }

}
