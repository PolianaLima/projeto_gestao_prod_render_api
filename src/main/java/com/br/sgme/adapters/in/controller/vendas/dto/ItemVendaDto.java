package com.br.sgme.adapters.in.controller.vendas.dto;

import com.br.sgme.adapters.out.bd.model.ItemVendaEntityBd;
import com.br.sgme.domain.ItemVenda;
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
public class ItemVendaDto {
    private String id;

    @JsonProperty("id_produto")
    private String idProduto;


    @JsonProperty("valor_produto")
    private Double valorProduto;

    @JsonProperty("desconto_produto")
    private Double descontoProduto;


    @JsonProperty("quantidade")
    private Integer quantidade;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonProperty("data_created")
    private LocalDateTime dataCreated;


    public static ItemVendaDto to(ItemVenda itemVenda) {
        return ItemVendaDto.builder()
                .id(itemVenda.getId())
                .idProduto(itemVenda.getIdProduto())
                .valorProduto(itemVenda.getValorProduto())
                .descontoProduto(itemVenda.getDescontoProduto())
                .quantidade(itemVenda.getQuantidade())
                .dataCreated(itemVenda.getDataCreated())
                .build();
    }
}
