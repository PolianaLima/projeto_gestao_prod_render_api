package com.br.sgme.adapters.in.controller.produtos;

import com.br.sgme.adapters.in.controller.produtos.dto.ProdutoDto;
import com.br.sgme.domain.Produto;
import com.br.sgme.domain.enums.Status;
import com.br.sgme.port.in.ProdutoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("produtos")
public class ProdutoController {
    private final ProdutoUseCase produtoUseCase;

    @GetMapping()
    public List<ProdutoDto> getAllProdutos(@RequestHeader("Authorization") String token) {
        List<Produto> produtos = produtoUseCase.get(token);
        return produtos.stream()
                .map(ProdutoDto::to)
                .collect(Collectors.toList());
    }

    @GetMapping("/{idProduto}")
    public ProdutoDto getById(@PathVariable UUID idProduto, @RequestHeader("Authorization") String token) {
        Produto produto = produtoUseCase.getById((idProduto).toString(), token);
        return ProdutoDto.to(produto);
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> save(@RequestBody @Validated ProdutoDto produtoDto, @RequestHeader("Authorization") String token) {
        Produto produto = Produto.builder()
                .codigo(produtoDto.getCodigo())
                .nome(produtoDto.getNome())
                .preco(produtoDto.getPreco())
                .custo(produtoDto.getCusto())
                .build();
        produtoUseCase.cadastrar(produto, token);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{idProduto}")
    public ResponseEntity<?> update(@PathVariable UUID idProduto, @RequestBody ProdutoDto produtoDto, @RequestHeader("Authorization") String token) {
        Produto produto = Produto.builder()
                .id((idProduto).toString())
                .codigo(produtoDto.getCodigo())
                .nome(produtoDto.getNome())
                .preco(produtoDto.getPreco())
                .custo(produtoDto.getCusto())
                .status(Status.valueOf(produtoDto.getStatus()))
                .build();

        produtoUseCase.alterar(produto, token);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("alterarStatus/{idProduto}")
    public ResponseEntity<?> alterarStatus(@PathVariable("idProduto") String idProduto, @RequestBody ProdutoDto produtoDto, @RequestHeader("Authorization") String token) {
        Produto produto = Produto.builder()
                .id(idProduto)
                .status(Status.valueOf(produtoDto.getStatus()))
                .build();
        produtoUseCase.alterarStatus(produto, token);
        return ResponseEntity.ok().build();
    }


}
