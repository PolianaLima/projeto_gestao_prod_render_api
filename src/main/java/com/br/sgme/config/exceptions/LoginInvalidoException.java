package com.br.sgme.config.exceptions;

public class LoginInvalidoException extends RuntimeException{
    public LoginInvalidoException(String message){
        super(message);
    }
}
