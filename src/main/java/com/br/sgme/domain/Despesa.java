package com.br.sgme.domain;

import com.br.sgme.adapters.out.bd.model.Usuario;
import com.br.sgme.config.exceptions.ErrorDataException;
import com.br.sgme.domain.enums.FormasPagamento;
import com.br.sgme.domain.enums.StatusContas;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
public class Despesa {
    private String id;
    private Usuario usuario;
    private Fornecedor fornecedor;
    private Double valor;
    private LocalDate dataVencimento;
    private StatusContas status;
    private FormasPagamento pagamento;
    private String observacao;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime dataCreated;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime dataUpdated;

    public Despesa(String id, Usuario usuario, Fornecedor fornecedor, Double valor, LocalDate dataVencimento, StatusContas status, FormasPagamento pagamento, String observacao, LocalDateTime dataCreated, LocalDateTime dataUpdated) {
        this.id = id;
        this.usuario = usuario;
        this.fornecedor = fornecedor;
        this.valor = valor;
        this.dataVencimento = dataVencimento;
        this.status = status;
        this.pagamento = pagamento;
        this.observacao = observacao;
        this.dataCreated = dataCreated;
        this.dataUpdated = dataUpdated;

        validate();

    }

    private void validate() {
       if(this.valor == null){
           throw new ErrorDataException("Informe um valor valido");
       }

        if (this.valor <= 0) {
            throw new ErrorDataException("Valor não pode ser  0 ou negativo");
        }

        if( this.observacao == null || this.observacao.isEmpty()){
            this.observacao = "";
        }
    }


}

