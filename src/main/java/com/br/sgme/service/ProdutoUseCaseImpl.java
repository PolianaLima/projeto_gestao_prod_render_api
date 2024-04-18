package com.br.sgme.service;

import com.br.sgme.controller.produtos.dto.ProdutoDto;
import com.br.sgme.enums.Status;
import com.br.sgme.exceptions.ErrorDetails;
import com.br.sgme.exceptions.RecursoNaoEncontradoException;
import com.br.sgme.model.Produto;
import com.br.sgme.model.usuario.Usuario;
import com.br.sgme.ports.ProdutoUseCase;
import com.br.sgme.repository.ProdutoRepository;
import com.br.sgme.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ProdutoUseCaseImpl implements ProdutoUseCase {
    private final UsuarioRepository usuarioRepository;
    private final ProdutoRepository produtoRepository;

    @Override
    public ResponseEntity<?> save(ProdutoDto produtoDto) {

        Usuario usuario = usuarioRepository.findById(produtoDto.getIdUsuario()).get();
        ResponseEntity<ErrorDetails> codigoProduto = verificaCodigoProdudo(produtoDto);
        if (codigoProduto != null) return codigoProduto;

        produtoRepository.save(Produto.builder()
                .usuario(usuario)
                .nome(produtoDto.getNome())
                .codigo(produtoDto.getCodigo())
                .custo(produtoDto.getCusto())
                .preco(produtoDto.getPreco())
                .status(Status.ATIVO.name())
                .data_created(LocalDateTime.now().toLocalDate())
                .build());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<?> update(String id, ProdutoDto produtoDto) {
        try {
            Produto produtoSelecionado = produtoRepository.findById(id).get();

            ResponseEntity<ErrorDetails> codigoProduto = verificaCodigoProdudo(produtoDto);
            if (codigoProduto != null && !Objects.equals(produtoDto.codigo, produtoSelecionado.getCodigo())) return codigoProduto;

            produtoRepository.save(Produto.builder()
                    .id(produtoSelecionado.getId())
                    .usuario(produtoSelecionado.getUsuario())
                    .nome(produtoDto.getNome())
                    .codigo(produtoDto.getCodigo())
                    .custo(produtoDto.getCusto())
                    .preco(produtoDto.getPreco())
                    .status(produtoDto.getStatus())
                    .data_created(produtoSelecionado.getData_created())
                    .data_updated(LocalDateTime.now().toLocalDate())
                    .build());

            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (NoSuchElementException exception) {
            throw new RecursoNaoEncontradoException("Produto nao encontrada");
        }

    }

    @Override
    public List<ProdutoDto> get(String idUsuario) {
        return produtoRepository.findByUsuarioId(idUsuario)
                .stream()
                .map(ProdutoDto::to)
                .collect(Collectors.toList());
    }

    @Override
    public ProdutoDto getById(String id) {
        return produtoRepository.findById(id)
                .stream()
                .map(ProdutoDto::to)
                .findFirst()
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado"));
    }

   private ResponseEntity<ErrorDetails> verificaCodigoProdudo(ProdutoDto produtoDto) {
        Produto produto = produtoRepository.findByCodigo(produtoDto.getCodigo());
        if (produto != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorDetails("Codigo do produto já cadastrado",
                            LocalDateTime.now(),
                            HttpStatus.BAD_REQUEST.value()));
        }
        return null;
    }

}
