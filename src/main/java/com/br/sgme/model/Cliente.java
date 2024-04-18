package com.br.sgme.model;

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
@Entity(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
   private String id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(length = 14)
    private String cpf;

    @Column(nullable = false, length = 80)
    private String nome;

    @Column(nullable = false, length = 15)
    private String telefone;

    @Column(name = "data_nascimento")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataNascimento;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate data_created;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate data_updated;
}
