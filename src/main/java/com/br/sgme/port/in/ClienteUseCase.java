package com.br.sgme.port.in;

import com.br.sgme.domain.Cliente;

import java.util.List;

public interface ClienteUseCase {
    void cadastrar(Cliente cliente, String token);

   void atualizar(Cliente cliente, String token);

    List<Cliente> get(String idUsuario);

    Cliente getId (String id, String token);
    Cliente getByDocumento (String idUsuario, String token);

    void alterarStatus(Cliente cliente, String token);
}
