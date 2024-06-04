package com.br.sgme.usecase.service;

import com.br.sgme.config.exceptions.RecursoNaoEncontradoException;
import com.br.sgme.config.security.TokenService;
import com.br.sgme.adapters.out.bd.model.Usuario;
import com.br.sgme.adapters.out.bd.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class UsuarioLogado {

    private  final UsuarioRepository usuarioRepository;
    private final TokenService tokenService;

    public Usuario getUsuarioLogado(String token) {
        String username = tokenService.validateToken(token.replace("Bearer ", ""));

        return usuarioRepository.findByUsername(username).orElseThrow(
                () -> new RecursoNaoEncontradoException("Usuário não encontrado")
        );
    }
}
