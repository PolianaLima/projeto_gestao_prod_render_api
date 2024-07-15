package com.br.sgme.config.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorDetails implements Serializable {

    @Serial
    private static final long serialVersionUID = 328838427783108943L;
    String message;
    String time;
    int codigo;
}
