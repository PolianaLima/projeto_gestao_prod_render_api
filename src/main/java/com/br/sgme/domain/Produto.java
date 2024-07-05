package com.br.sgme.domain;

import com.br.sgme.adapters.out.bd.model.Usuario;
import com.br.sgme.config.exceptions.ErrorDataException;
import com.br.sgme.domain.enums.Status;
import com.br.sgme.utils.GeradorCodigo;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
public class Produto {
    private String id;
    private Usuario usuario;
    private String nome;
    private String codigo;
    private Double preco;
    private Double custo;
    private Status status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dataCreated;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dataUpdated;

    public Produto(String id, Usuario usuario, String nome, String codigo, Double preco, Double custo, Status status, LocalDateTime dataCreated, LocalDateTime dataUpdated) {
        this.id = id;
        this.usuario = usuario;
        this.nome = nome;
        this.codigo = codigo;
        this.preco = preco;
        this.custo = custo;
        this.status = status;
        this.dataCreated = dataCreated;
        this.dataUpdated = dataUpdated;

        validate();
    }

    private void validate() {
        if (this.codigo == null || this.codigo.isEmpty()) {
            this.codigo = GeradorCodigo.RandomCodigo();
        }

        if (this.nome != null && this.nome.isEmpty()) {
            throw new ErrorDataException("Nome do produto é obrigatório");
        }

        if (this.preco != null && this.preco < 0) {
            throw new ErrorDataException("Preço não pode ser negativo");

        }

        if (this.custo != null && this.custo < 0) {
            throw new ErrorDataException("Custo não pode ser negativo");
        }


    }
}
