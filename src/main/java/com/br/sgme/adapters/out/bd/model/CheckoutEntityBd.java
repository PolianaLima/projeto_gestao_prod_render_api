package com.br.sgme.adapters.out.bd.model;

import com.br.sgme.domain.enums.StatusCheckout;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "checkouts")
public class CheckoutEntityBd {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @JsonProperty("valor_inicial")
    private Double valorInicial;

    @OneToMany(mappedBy = "checkout", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<VendaEntityDb> vendas = new ArrayList<>();


    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusCheckout status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonProperty("data_created")
    private LocalDate dataCreated;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonProperty("data_closed")
    private LocalDate dataClosed;
}
