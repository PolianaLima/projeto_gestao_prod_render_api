package com.br.sgme.adapters.out.bd.model;

import com.br.sgme.domain.enums.FormasPagamento;
import com.br.sgme.domain.enums.StatusVenda;
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
@Entity(name = "vendas")
public class VendaEntityDb {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "checkout_id")
    private CheckoutEntityBd checkout;

    @JsonProperty("desconto_total")
    private Double descontoTotal;

    @JsonProperty("valor_total")
    private Double valorTotal;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusVenda status;

    @OneToMany(mappedBy = "venda", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER,orphanRemoval = true)
    private List<ItemVendaEntityBd> itens = new ArrayList<>();

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FormasPagamento pagamento;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonProperty("data_created")
    private LocalDateTime dataCreated;


}
