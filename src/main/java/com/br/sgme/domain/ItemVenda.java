package com.br.sgme.domain;

import com.br.sgme.config.exceptions.ErrorDataException;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
public class ItemVenda {
    private String id;
    private String idProduto;
    private Double valorProduto;
    private Double descontoProduto;
    private Integer quantidade;
    private Venda venda;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dataCreated;

    public ItemVenda(String id, String idProduto, Double valorProduto, Double descontoProduto, Integer quantidade, Venda venda, LocalDateTime dataCreated) {
        this.id = id;
        this.idProduto = idProduto;
        this.valorProduto = valorProduto;
        this.descontoProduto = descontoProduto;
        this.quantidade = quantidade;
        this.venda = venda;
        this.dataCreated = dataCreated;

        validate();
    }

    private void validate() {
        if(this.quantidade == null || this.quantidade <= 0){
            throw new ErrorDataException("Quantidade deve ser maior que 0");
        }

        if(this.valorProduto == null || this.valorProduto <= 0){
            throw new ErrorDataException("Valor do produto deve ser maior que 0");
        }

        if (this.descontoProduto > this.valorProduto) {
            throw new ErrorDataException("Desconto não pode ser maior que o valor do produto");
        }
    }
}
