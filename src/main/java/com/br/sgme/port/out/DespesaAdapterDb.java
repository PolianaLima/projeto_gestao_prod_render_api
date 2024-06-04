package com.br.sgme.port.out;

import com.br.sgme.domain.Despesa;

import java.util.List;

public interface DespesaAdapterDb {
    Despesa save(Despesa despesa);
    Despesa update(Despesa despesa);
    Despesa alterarStatus(Despesa despesa);
    Despesa findByIdAndUsuarioId(String idDespesa, String usuarioId);
    List<Despesa> findByUsuarioId(String usuarioId);
    void delete(String idDespesa, String usuarioId);
}
