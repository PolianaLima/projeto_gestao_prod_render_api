package com.br.sgme.adapters.out.bd.service;

import com.br.sgme.adapters.out.bd.model.ProdutoEntityBd;
import com.br.sgme.adapters.out.bd.model.Usuario;
import com.br.sgme.adapters.out.bd.repository.ProdutoRepository;
import com.br.sgme.config.exceptions.RecursoNaoEncontradoException;
import com.br.sgme.domain.Produto;
import com.br.sgme.port.out.ProdutoAdapterDb;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProdutoServiceAdapterImpl implements ProdutoAdapterDb {
    private final ProdutoRepository produtoRepository;

    @Override
    public Produto save(Produto produto) {
        ProdutoEntityBd produtoEntityBd = from(produto);
        ProdutoEntityBd save = produtoRepository.save(produtoEntityBd);
        return toProduto(save);
    }

    @Override
    public Produto update(Produto produto) {
        ProdutoEntityBd produtoEntityBd = from(produto);
        ProdutoEntityBd save = produtoRepository.save(produtoEntityBd);
        return toProduto(save);
    }

    @Override
    public Produto alterarStatus(Produto produto) {
        ProdutoEntityBd produtoEntityBd = from(produto);
        ProdutoEntityBd save = produtoRepository.save(produtoEntityBd);
        return toProduto(save);
    }

    @Override
    public List<Produto> findByUsuarioId(String usuarioId) {
        List<ProdutoEntityBd> byUsuarioId = produtoRepository.findByUsuarioId(usuarioId);
        return byUsuarioId.stream()
                .map(this::toProduto)
                .collect(Collectors.toList());
    }

    @Override
    public Produto findByCodigo(String codigo, String usuarioId) {
       ProdutoEntityBd produtoEntityBd = produtoRepository.findByCodigo(codigo, usuarioId).orElseThrow(
               ()->new RecursoNaoEncontradoException("Produto nao encontrado")
       );
        return toProduto(produtoEntityBd);
    }

    @Override
    public boolean verificaCodigoBd(String codigo, String usuarioId) {
        return produtoRepository.existByCodigoAndUsuarioId(codigo, usuarioId);
    }

    @Override
    public Produto findByIdAndUsuarioId(String idProduto, String usuarioId) {
        ProdutoEntityBd produtoEntityBd = produtoRepository.findByIdAndUsuarioId(idProduto, usuarioId).orElseThrow(
                ()->new RecursoNaoEncontradoException("Produto nao encontrado"));
        return toProduto(produtoEntityBd);
    }

    private ProdutoEntityBd from(Produto produto) {
        return ProdutoEntityBd.builder()
                .id(produto.getId())
                .usuario(Usuario.builder()
                        .id(produto.getUsuario().getId())
                        .build())
                .codigo(produto.getCodigo())
                .nome(produto.getNome())
                .preco(produto.getPreco())
                .custo(produto.getCusto())
                .status(produto.getStatus())
                .dataCreated(produto.getDataCreated())
                .dataUpdated(produto.getDataUpdated())
                .build();
    }

    private Produto toProduto(ProdutoEntityBd save) {
        return Produto.builder()
                .id(save.getId())
                .usuario(Usuario.builder()
                        .id(save.getUsuario().getId())
                        .build())
                .codigo(save.getCodigo())
                .nome(save.getNome())
                .preco(save.getPreco())
                .custo(save.getCusto())
                .status(save.getStatus())
                .dataCreated(save.getDataCreated())
                .dataUpdated(save.getDataUpdated())
                .build();
    }
}
