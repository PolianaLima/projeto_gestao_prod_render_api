package com.br.sgme.port.out;

import com.br.sgme.domain.Produto;

import java.util.List;

public interface ProdutoAdapterDb {
    Produto save(Produto produto);
    Produto update(Produto produto);
    Produto alterarStatus(Produto produto);
    Produto findByIdAndUsuarioId(String idProduto, String usuarioId);
    List<Produto> findByUsuarioId(String usuarioId);
    Produto findByCodigo(String codigo, String usuarioId);
    boolean verificaCodigoBd(String codigo, String usuarioId);
}
