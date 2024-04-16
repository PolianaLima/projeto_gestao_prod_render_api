package com.br.sgme.controller.despesa.dto;

import com.br.sgme.model.Despesa;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DespesaDto {

    private String id;

    @JsonProperty("usuario_id")
    private String idUsuario;

    @JsonProperty("fornecedor_id")
    private String idFornecedor;


    private Double valor;

    @JsonProperty("data_vencimento")
    private LocalDate dataVencimento;

    private String status;

    @JsonProperty("forma_pagamento")
    private String formaPagamento;

    private String observacao;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonProperty("data_created")
    private LocalDate dataCreated;

    public static DespesaDto to(Despesa saved){
        return new DespesaDto(
                saved.getId(),
                saved.getUsuario().getId(),
                saved.getFornecedor().getId(),
                saved.getValor(),
                saved.getDataVencimento(),
                saved.getStatus(),
                saved.getPagamento().name(),
                saved.getObservacao(),
                saved.getData_created()
        );
    }


}
