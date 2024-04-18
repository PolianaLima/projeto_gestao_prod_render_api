package com.br.sgme.controller.produtos.dto;

import com.br.sgme.model.Produto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoDto {
    public String id;

    @JsonProperty("usuario_id")
    public String idUsuario;
    public String nome;
    public String codigo;
    public Double preco;
    public Double custo;
    public String status;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonProperty("data_created")
    private LocalDate dataCreated;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonProperty("data_updated")
    private LocalDate dataUpdated;

    public static ProdutoDto to(Produto saved) {
        return new ProdutoDto(
                saved.getId(),
                saved.getUsuario().getId(),
                saved.getNome(),
                saved.getCodigo(),
                saved.getPreco(),
                saved.getCusto(),
                saved.getStatus(),
                saved.getData_created(),
                saved.getData_updated()
        );
    }
}
