package com.br.sgme.adapters.in.controller.receita.dto;

import com.br.sgme.domain.Receita;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReceitaDto {

    private String id;

    @JsonProperty("usuario_id")
    private String idUsuario;

    @JsonProperty("cliente_id")
    private String idCliente;


    private Double valor;

    @JsonProperty("data_vencimento")
    private LocalDate dataVencimento;

    private String status;

    @JsonProperty("forma_pagamento")
    private String formaPagamento;

    private String observacao;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonProperty("data_created")
    private LocalDateTime dataCreated;


    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonProperty("data_updated")
    private LocalDateTime dataUpdated;


    public static ReceitaDto to(Receita receita){
        return ReceitaDto.builder()
                .id(receita.getId())
                .idUsuario(receita.getUsuario().getId())
                .idCliente(receita.getCliente().getId())
                .valor(receita.getValor())
                .dataVencimento(receita.getDataVencimento())
                .formaPagamento(receita.getPagamento().name())
                .status(receita.getStatus().name())
                .observacao(receita.getObservacao())
                .dataCreated(receita.getDataCreated())
                .dataUpdated(receita.getDataUpdated())
                .build();
    }

}
