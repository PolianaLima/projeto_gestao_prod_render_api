package com.br.sgme.adapters.in.controller.usuario.dto;

import com.br.sgme.adapters.out.bd.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UsuarioReponseDto {
    private  String id;
    private String nome;
    private String login;

    public UsuarioReponseDto to(Usuario usuario){
        return new UsuarioReponseDto(
                usuario.getId(),
                usuario.getNome(),
                usuario.getLogin()
        );
    }
}
