package com.br.sgme.adapters.out.bd.model;

import com.br.sgme.domain.enums.Status;
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
@Entity(name = "produtos")
public class ProdutoEntityBd {

    @Id
    @GeneratedValue(strategy =  GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(nullable = false, length = 80)
    private String nome;

    @Column(length = 20)
    private String codigo;

    @Column(nullable = false, length = 60)
    private Double preco;

    private Double custo;

    @Column(nullable = false, length = 80)
    @Enumerated(EnumType.STRING)
    private Status status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonProperty("data_created")
    private LocalDateTime dataCreated;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonProperty("data_updated")
    private LocalDateTime dataUpdated;



}
