package com.br.sgme.adapters.in.controller.usuario.dto;

import com.br.sgme.adapters.out.bd.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

@AllArgsConstructor
@Getter
@Builder
public class UsuarioReponseDto {
    private  String id;
    private String nome;
    private String login;

    public static UsuarioReponseDto to(Usuario usuario){
        return new UsuarioReponseDto(
                usuario.getId(),
                usuario.getNome(),
                usuario.getLogin()
        );
    }

}
