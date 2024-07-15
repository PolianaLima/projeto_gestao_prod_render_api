package com.br.sgme.config.exceptions;


import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

@Getter
public class LoginInvalidoException extends RuntimeException implements Serializable {


    @Serial
    private static final long serialVersionUID = -4059855666241375748L;
    private final String mensagem;

    public LoginInvalidoException(String message){
        super(message);
        mensagem = message;
    }
}
