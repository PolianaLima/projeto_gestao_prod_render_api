package com.br.sgme.ports;

import com.br.sgme.controller.produtos.dto.ProdutoDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProdutoUseCase {
    ResponseEntity<?> save(ProdutoDto produtoDto);
    ResponseEntity<?> update(String id, ProdutoDto produtoDto);
    List<ProdutoDto> get(String idUsuario);
    ProdutoDto getById(String id);
}
