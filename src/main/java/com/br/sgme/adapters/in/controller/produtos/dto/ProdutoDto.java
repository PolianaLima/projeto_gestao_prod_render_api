package com.br.sgme.adapters.in.controller.produtos.dto;

import com.br.sgme.domain.Produto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProdutoDto {

    private String id;

    @JsonProperty("usuario_id")
    private String idUsuario;
    private String nome;
    private String codigo;
    private Double preco;
    private Double custo;
    private String status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonProperty("data_created")
    private LocalDateTime dataCreated;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonProperty("data_updated")
    private LocalDateTime dataUpdated;

    public static ProdutoDto to(Produto produto) {
        return ProdutoDto.builder()
                .id(produto.getId())
                .idUsuario(produto.getUsuario().getId())
                .nome(produto.getNome())
                .codigo(produto.getCodigo())
                .preco(produto.getPreco())
                .custo(produto.getCusto())
                .status(produto.getStatus().name())
                .dataCreated(produto.getDataCreated())
                .dataUpdated(produto.getDataUpdated())
                .build();
    }

}
