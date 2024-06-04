package com.br.sgme.domain;

import com.br.sgme.adapters.out.bd.model.Usuario;
import com.br.sgme.domain.enums.StatusCheckout;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@Data
public class Checkout {
    private String id;
    private Usuario usuario;

    @JsonProperty("valor_inicial")
    private Double valorInicial;

    private List<Venda> vendas;

    private StatusCheckout status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonProperty("data_created")
    private LocalDate dataCreated;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonProperty("data_closed")
    private LocalDate dataClosed;


    public Checkout(String id, Usuario usuario, Double valorInicial, List<Venda> vendas, StatusCheckout status, LocalDate dataCreated, LocalDate dataClosed) {
        this.id = id;
        this.usuario = usuario;
        this.valorInicial = valorInicial;
        this.vendas = vendas;
        this.status = status;
        this.dataCreated = dataCreated;
        this.dataClosed = dataClosed;

        validate();
    }

    private void validate() {
        if (this.valorInicial == null) {
           setValorInicial(0.0);
        }
    }
}
