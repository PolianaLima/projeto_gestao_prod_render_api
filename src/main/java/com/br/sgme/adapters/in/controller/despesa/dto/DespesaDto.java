package com.br.sgme.adapters.in.controller.despesa.dto;

import com.br.sgme.adapters.out.bd.model.DespesaEntityBd;
import com.br.sgme.domain.Despesa;
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
public class DespesaDto {

    private String id;

    @JsonProperty("usuario_id")
    private String idUsuario;

    @JsonProperty("fornecedor_id")
    private String idFornecedor;

    @JsonProperty("nome")
    private String nomeFornecedor;

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

    public static DespesaDto to(Despesa despesa){
        return DespesaDto.builder()
                .id(despesa.getId())
                .idUsuario(despesa.getUsuario().getId())
                .idFornecedor(despesa.getFornecedor().getId())
                .nomeFornecedor(despesa.getFornecedor().getNome())
                .valor(despesa.getValor())
                .dataVencimento(despesa.getDataVencimento())
                .formaPagamento(despesa.getPagamento().name())
                .status(despesa.getStatus().name())
                .observacao(despesa.getObservacao())
                .dataCreated(despesa.getDataCreated())
                .dataUpdated(despesa.getDataUpdated())
                .build();
    }


}
