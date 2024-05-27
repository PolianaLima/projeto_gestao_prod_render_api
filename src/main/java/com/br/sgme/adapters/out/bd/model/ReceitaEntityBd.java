package com.br.sgme.adapters.out.bd.model;

import com.br.sgme.domain.enums.FormasPagamento;
import com.br.sgme.domain.enums.StatusContas;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "receitas")
public class ReceitaEntityBd {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private ClienteEntityBd clienteEntityBd;

    @Column(nullable = false)
    private Double valor;

    @Column(name = "data_vencimento", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataVencimento;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusContas status;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FormasPagamento pagamento;

    private String observacao;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonProperty("data_created")
    private LocalDateTime dataCreated;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonProperty("data_updated")
    private LocalDateTime dataUpdated;


}
