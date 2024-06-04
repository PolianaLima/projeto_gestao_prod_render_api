package com.br.sgme.utils;

import java.util.UUID;

public class GeradorCodigo {

    public static String RandomCodigo() {
        // Generate a random UUID
        UUID uuid = UUID.randomUUID();

        // Convert the UUID to a string and replace all "-" characters
        String codigo = uuid.toString().replace("-", "");

        // Truncate the string to the first 5 characters
        if (codigo.length() > 5) {
            codigo = codigo.substring(0, 5);
        }

        return codigo.toUpperCase();
    }
}