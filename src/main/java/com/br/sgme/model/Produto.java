package com.br.sgme.model;

import com.br.sgme.enums.Status;
import com.br.sgme.model.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "produtos")
public class Produto {

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
    private String status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate data_created;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate data_updated;



}
