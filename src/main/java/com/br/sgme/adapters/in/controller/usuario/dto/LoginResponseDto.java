package com.br.sgme.adapters.in.controller.usuario.dto;

import com.br.sgme.adapters.out.bd.model.Usuario;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {
    private String token;
    private UsuarioReponseDto usuario;

    public LoginResponseDto(String token, Usuario usuario) {
        this.token = token;
        this.usuario = new UsuarioReponseDto(usuario.getId(), usuario.getNome(), usuario.getLogin());
    }


}
