package com.br.sgme.usecase.service;

import com.br.sgme.adapters.out.bd.model.Usuario;
import com.br.sgme.config.exceptions.ErrorDataException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AutorizacaoUsuario {
    public void autorizacaoUsuario(Usuario usuarioLogado, Usuario usuarioClienteUpdate) {
        if (!Objects.equals(usuarioLogado.getId(), usuarioClienteUpdate.getId())) {
            throw new ErrorDataException("Usuario nao autorizado");
        }
    }
}
