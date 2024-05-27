package com.br.sgme.adapters.in.controller.usuario.dto;


import com.br.sgme.domain.enums.UsuarioRole;

public record RegisterDto(String nome, String login, String senha, UsuarioRole role) {
}
