package com.br.sgme.usecase.service;

import com.br.sgme.domain.Produto;
import com.br.sgme.domain.enums.Status;
import com.br.sgme.config.exceptions.RecursoNaoEncontradoException;
import com.br.sgme.adapters.out.bd.model.Usuario;
import com.br.sgme.port.in.ProdutoUseCase;
import com.br.sgme.port.out.ProdutoAdapterDb;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ProdutoUseCaseImpl implements ProdutoUseCase {

    private final ProdutoAdapterDb produtoAdapterDb;
    private final UsuarioLogado usuarioLogado;
    private final AutorizacaoUsuario autorizacaoUsuario;

    @Override
    public void cadastrar(Produto produto, String token) {
        Usuario usuario = getUsuarioLogado(token);
        verificaCodigo(produto, usuario.getId());

        if(produto.getPreco() < produto.getCusto()){
            throw new RecursoNaoEncontradoException("Preço não pode ser menor que o custo");
        }

        Produto produtoSalvar = Produto.builder()
                .usuario(usuario)
                .codigo(produto.getCodigo())
                .nome(produto.getNome())
                .preco(produto.getPreco())
                .custo(produto.getCusto())
                .status(Status.ATIVO)
                .dataCreated(LocalDateTime.now())
                .build();

        produtoAdapterDb.save(produtoSalvar);
    }


    @Override
    public void alterar(Produto produto, String token) {
        Usuario usuario = getUsuarioLogado(token);
        Produto produtoSelecionado = getProdutoById(produto.getId(), usuario.getId());
        verificaCodigoUpdate(produto, produtoSelecionado.getCodigo(), usuario.getId());

        if(produto.getPreco() < produto.getCusto()){
            throw new RecursoNaoEncontradoException("Preço não pode ser menor que o custo");
        }
        Produto produtoAlterar = Produto.builder()
                .id(produtoSelecionado.getId())
                .usuario(produtoSelecionado.getUsuario())
                .codigo(produto.getCodigo())
                .nome(produto.getNome())
                .preco(produto.getPreco())
                .custo(produto.getCusto())
                .status(produto.getStatus())
                .dataCreated(produtoSelecionado.getDataCreated())
                .dataUpdated(LocalDateTime.now())
                .build();
        produtoAdapterDb.update(produtoAlterar);
    }


    @Override
    public List<Produto> get(String idUsuario) {
        Usuario usuario = getUsuarioLogado(idUsuario);
        return new ArrayList<>(produtoAdapterDb.findByUsuarioId(usuario.getId()));
    }

    @Override
    public Produto getById(String id, String token) {
        Usuario usuario = getUsuarioLogado(token);
        return produtoAdapterDb.findByIdAndUsuarioId(id, usuario.getId());
    }

    @Override
    public void alterarStatus(Produto produto, String token) {
        Usuario usuario = getUsuarioLogado(token);
        Produto produtoSelecionado = getProdutoById(produto.getId(), usuario.getId());

        Produto produtoAlterarStatus = Produto.builder()
                .id(produtoSelecionado.getId())
                .usuario(produtoSelecionado.getUsuario())
                .codigo(produtoSelecionado.getCodigo())
                .nome(produtoSelecionado.getNome())
                .preco(produtoSelecionado.getPreco())
                .custo(produtoSelecionado.getCusto())
                .status(produto.getStatus())
                .dataCreated(produtoSelecionado.getDataCreated())
                .dataUpdated(LocalDateTime.now())
                .build();

        produtoAdapterDb.alterarStatus(produtoAlterarStatus);
    }


    private Usuario getUsuarioLogado(String token) {
        return usuarioLogado.getUsuarioLogado(token);
    }

    private void verificaCodigo(Produto produto, String idUsuario) {
        boolean existByCodigoAndUsuarioId = produtoAdapterDb.verificaCodigoBd(produto.getCodigo(), idUsuario);
        if (existByCodigoAndUsuarioId) {
            throw new RecursoNaoEncontradoException("Codigo de produto já cadastrado");
        }
    }

    private void verificaCodigoUpdate(Produto produto,String codigoProdutoSelecionado, String idUsuario ) {
        boolean existByCodigoAndUsuarioId = produtoAdapterDb.verificaCodigoBd(produto.getCodigo(), idUsuario);
        if (existByCodigoAndUsuarioId && !produto.getCodigo().equals(codigoProdutoSelecionado)) {
            throw new RecursoNaoEncontradoException("Codigo de produto já cadastrado");
        }
    }

    private Produto getProdutoById(String idProduto, String idUsuario) {
        return produtoAdapterDb.findByIdAndUsuarioId(idProduto, idUsuario);
    }

}
