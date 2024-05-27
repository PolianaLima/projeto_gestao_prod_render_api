package com.br.sgme.adapters.in.controller.cliente.dto;

import com.br.sgme.domain.Cliente;
import com.br.sgme.adapters.out.bd.model.ClienteEntityBd;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClienteDto {
    private String id;

    @JsonProperty("usuario_id")
    private String idUsuario;

    private String documento;

    private String nome;

    private String telefone;

    private String status;

    @JsonProperty("data_nascimento")
    private LocalDate dataNascimento;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonProperty("data_created")
    private LocalDateTime dataCreated;


    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonProperty("data_updated")
    private LocalDateTime dataUpdated;


    public static ClienteDto to(Cliente cliente){
        return ClienteDto.builder()
                .id(cliente.getId())
                .idUsuario(cliente.getUsuario().getId())
                .nome(cliente.getNome())
                .documento(cliente.getDocumento())
                .telefone(cliente.getTelefone())
                .dataNascimento(cliente.getDataNascimento())
                .status(cliente.getStatus().name())
                .dataCreated(cliente.getDateCreated())
                .dataUpdated(cliente.getDateUpdated())
                .build();
    }
}
