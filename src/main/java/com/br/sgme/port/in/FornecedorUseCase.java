package com.br.sgme.port.in;

import com.br.sgme.domain.Fornecedor;

import java.util.List;

public interface FornecedorUseCase {
    void cadastrar(Fornecedor fornecedor, String token);
    void atualizar(Fornecedor fornecedor, String token);

    List<Fornecedor>get(String idUsuario);

    Fornecedor getById(String id, String token);

    Fornecedor getByDocumento(String idUsuario, String token);

    void alterarStatus(Fornecedor fornecedor, String token);

}
