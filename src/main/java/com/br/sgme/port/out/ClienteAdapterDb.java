package com.br.sgme.port.out;

import com.br.sgme.domain.Cliente;

import java.util.List;

public interface ClienteAdapterDb {
    Cliente save(Cliente cliente);
    Cliente update(Cliente cliente);
    Cliente alterarStatus(Cliente cliente);
    Cliente findByIdAndUsuarioId(String idCliente, String usuarioId);
    List<Cliente> findByUsuarioId(String usuarioId);
    Cliente findByDocumento(String documento, String usuarioId);
    boolean verificaDocumentoBd(String documento, String usuarioId);
}
