package com.br.sgme.port;

import com.br.sgme.domain.Receita;

import java.util.List;

public interface ReceitaAdapterDb {
    Receita save(Receita receita);
    Receita update(Receita receita);
    Receita alterarStatus(Receita receita);
    Receita findByIdAndUsuarioId(String idDespesa, String usuarioId);
    List<Receita> findByUsuarioId(String usuarioId);
    void delete(String idDespesa, String usuarioId);
}
