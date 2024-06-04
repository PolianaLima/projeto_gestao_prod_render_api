package com.br.sgme.domain;


import com.br.sgme.adapters.out.bd.model.Usuario;
import com.br.sgme.config.exceptions.ErrorDataException;
import com.br.sgme.domain.enums.Status;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
public class Fornecedor {

    private String id;
    private Usuario usuario;
    private String documento;
    private String nome;
    private Status status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dataCreated;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dataUpdated;

    public Fornecedor(String id,
                      Usuario usuario,
                      String documento,
                      String nome,
                      Status status,
                      LocalDateTime dataCreated,
                      LocalDateTime dataUpdated) {
        this.id = id;
        this.usuario = usuario;
        this.documento = documento;
        this.nome = nome;
        this.status = status;
        this.dataCreated = dataCreated;
        this.dataUpdated = dataUpdated;

        validaDocumento(documento);

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
}
