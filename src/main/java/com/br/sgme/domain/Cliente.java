package com.br.sgme.domain;

import com.br.sgme.domain.enums.Status;
import com.br.sgme.config.exceptions.ErrorDataException;


import com.br.sgme.adapters.out.bd.model.Usuario;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;

@Data
@Builder
@NoArgsConstructor
public class Cliente {

    private String id;
    private Usuario usuario;
    private String nome;
    private String documento; //pode ser CPF ou CNPJ
    private String telefone;
    private Status status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataNascimento;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateCreated;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateUpdated;

    public Cliente(String id, Usuario usuario, String nome, String documento, String telefone, Status status, LocalDate dataNascimento, LocalDateTime dateCreated, LocalDateTime dateUpdated) {
        this.id = id;
        this.usuario = usuario;
        this.nome = nome;
        this.documento = documento;
        this.telefone = telefone;
        this.status = status;
        this.dataNascimento = dataNascimento;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;

        validaDocumento(documento);
        validaDataNascimento(dataNascimento);
    }

    public void validaDocumento(String documento){
        if (documento == null){
            return;
        }

        if (!documento.isEmpty()  && !isFormatoCpfouCNPJ(documento)){
                throw new ErrorDataException("Documento invalido, o documento deve ser um CPF ou CNPJ ou vazio.");
            }
        this.documento = documento;
   }

   private boolean isFormatoCpfouCNPJ(String documento){
       return isFormatoCPF(documento) || isFormatoCNPJ(documento);
   }

   private boolean isFormatoCPF(String cpf){
       return cpf.length() == 11;
    }
    private boolean isFormatoCNPJ(String cnpj){
        return cnpj.length() == 14;
    }

    public void validaDataNascimento(LocalDate dataNascimento){
        if(dataNascimento != null && dataNascimento.isAfter(ChronoLocalDate.from(LocalDate.now()))){
            throw new ErrorDataException("Data de nascimento invalida, a data deve ser menor que a data atual.");
        }

    }


}
