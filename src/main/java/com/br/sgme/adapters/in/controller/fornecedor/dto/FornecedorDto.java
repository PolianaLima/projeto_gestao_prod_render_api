package com.br.sgme.adapters.in.controller.fornecedor.dto;

import com.br.sgme.adapters.out.bd.model.FornecedorEntityBd;
import com.br.sgme.domain.Fornecedor;
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
public class FornecedorDto {
    private String id;

    @JsonProperty("usuario_id")
    private String idUsuario;

    private String documento;

    private String nome;

    private String status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonProperty("data_created")
    private LocalDateTime dataCreated;


    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonProperty("data_updated")
    private LocalDateTime dataUpdated;

    public static FornecedorDto to(Fornecedor fornecedor){
        return FornecedorDto.builder()
                .id(fornecedor.getId())
                .idUsuario(fornecedor.getUsuario().getId())
                .nome(fornecedor.getNome())
                .documento(fornecedor.getDocumento())
                .status(fornecedor.getStatus().name())
                .dataCreated(fornecedor.getDataCreated())
                .dataUpdated(fornecedor.getDataUpdated())
                .build();
    }

}
