package com.br.sgme.config.exceptions;


import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler({LoginInvalidoException.class, JWTVerificationException.class})
    public ResponseEntity<ErrorDetails> loginInvalidoException(LoginInvalidoException exception) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN.value())
                .body(ErrorDetails.builder()
                        .message(exception.getMessage())
                        .time(LocalDateTime.now().toString())
                        .codigo(HttpStatus.FORBIDDEN.value())
                        .build()
                );
    }

    @ExceptionHandler({RecursoNaoEncontradoException.class})
    public ResponseEntity<ErrorDetails> detailsHandleExceptions(RecursoNaoEncontradoException exception) {
        return ResponseEntity
                .status(404)
                .body(ErrorDetails.builder()
                        .message(exception.getMessage())
                        .time(LocalDateTime.now().toString())
                        .codigo(HttpStatus.NOT_FOUND.value())
                        .build()
                );
    }

    @ExceptionHandler({ErrorDataException.class})
    public ResponseEntity<ErrorDetails> errorDuplicatedData(ErrorDataException exception) {
        return ResponseEntity
                .status(400)
                .body(ErrorDetails.builder()
                        .message(exception.getMessage())
                        .time(LocalDateTime.now().toString())
                        .codigo(HttpStatus.BAD_REQUEST.value())
                        .build()
                );

    }


}
