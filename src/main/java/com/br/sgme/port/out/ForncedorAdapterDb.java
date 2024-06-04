package com.br.sgme.port.out;

import com.br.sgme.domain.Fornecedor;

import java.util.List;

public interface ForncedorAdapterDb {
    Fornecedor save(Fornecedor fornecedor);
    Fornecedor update(Fornecedor fornecedor);
    Fornecedor alterarStatus(Fornecedor fornecedor);
    Fornecedor findByIdAndUsuarioId(String idFornecedor, String usuarioId);
    List<Fornecedor> findByUsuarioId(String usuarioId);
    Fornecedor findByDocumento(String cnpj, String usuarioId);
    boolean verificaDocumentoBd(String cnpj, String usuarioId);
}
