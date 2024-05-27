package com.br.sgme.port.in;

import com.br.sgme.domain.Produto;

import java.util.List;

public interface ProdutoUseCase {
    void cadastrar(Produto produto, String token);
    void alterar(Produto produto, String token);
    List<Produto> get(String idUsuario);
    Produto getById(String id, String token);
    void alterarStatus(Produto produto, String token);
}
