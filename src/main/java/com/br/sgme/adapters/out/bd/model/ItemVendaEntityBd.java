package com.br.sgme.adapters.out.bd.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "items")
public class ItemVendaEntityBd {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    @JsonProperty("id_produto")
    private String idProduto;

    @Column(nullable = false)
    @JsonProperty("valor_produto")
    private Double valorProduto;

    @JsonProperty("desconto_produto")
    private Double descontoProduto;

    @Column(nullable = false)
    private Integer quantidade;

    @ManyToOne
    @JoinColumn(name = "venda_id")
    private VendaEntityDb venda;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonProperty("data_created")
    private LocalDateTime dataCreated;

}
