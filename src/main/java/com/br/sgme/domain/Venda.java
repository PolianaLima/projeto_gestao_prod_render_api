package com.br.sgme.domain;

import com.br.sgme.adapters.out.bd.model.Usuario;
import com.br.sgme.config.exceptions.ErrorDataException;
import com.br.sgme.domain.enums.FormasPagamento;
import com.br.sgme.domain.enums.StatusVenda;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
public class Venda {
    private String id;
    private Usuario usuario;
    private Checkout checkout;
    private Double descontoTotal;
    private Double valorTotal;
    private StatusVenda status;
    private FormasPagamento pagamento;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dataCreated;


    private List<ItemVenda> itens = new ArrayList<>();


    public Venda(String id, Usuario usuario, Checkout checkout, Double descontoTotal, Double valorTotal, StatusVenda status, FormasPagamento pagamento, LocalDateTime dataCreated, List<ItemVenda> itens) {
        this.id = id;
        this.usuario = usuario;
        this.checkout = checkout;
        this.descontoTotal = descontoTotal;
        this.valorTotal = valorTotal;
        this.status = status;
        this.pagamento = pagamento;
        this.dataCreated = dataCreated;
        this.itens = itens;

    }


}
