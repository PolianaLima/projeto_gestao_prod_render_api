package com.br.sgme.adapters.out.bd.service;

import com.br.sgme.adapters.out.bd.model.FornecedorEntityBd;
import com.br.sgme.adapters.out.bd.model.Usuario;
import com.br.sgme.adapters.out.bd.repository.FornecedorRepository;
import com.br.sgme.config.exceptions.RecursoNaoEncontradoException;
import com.br.sgme.domain.Fornecedor;
import com.br.sgme.port.out.ForncedorAdapterDb;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FornecedorServiceAdapterImpl implements ForncedorAdapterDb {

    private final FornecedorRepository fornecedorRepository;

    @Override
    public Fornecedor save(Fornecedor fornecedor) {
        FornecedorEntityBd fornecedorEntityBd = from(fornecedor);
        FornecedorEntityBd save = fornecedorRepository.save(fornecedorEntityBd);
        return toFornecedor(save);
    }

    @Override
    public Fornecedor update(Fornecedor fornecedor) {
        FornecedorEntityBd fornecedorEntityBd = from(fornecedor);
        FornecedorEntityBd save = fornecedorRepository.save(fornecedorEntityBd);
        return toFornecedor(save);
    }

    @Override
    public Fornecedor alterarStatus(Fornecedor fornecedor) {
        FornecedorEntityBd fornecedorEntityBd = from(fornecedor);
        FornecedorEntityBd save = fornecedorRepository.save(fornecedorEntityBd);
        return toFornecedor(save);
    }

    @Override
    public Fornecedor findByIdAndUsuarioId(String idFornecedor, String usuarioId) {
        FornecedorEntityBd fornecedorEntityBd = fornecedorRepository.findByIdAndUsuarioId(idFornecedor, usuarioId).orElseThrow(
                ()->new RecursoNaoEncontradoException("Fornecedor nao encontrado"
        ));
        return toFornecedor(fornecedorEntityBd);
    }

    @Override
    public List<Fornecedor> findByUsuarioId(String usuarioId) {
        List<FornecedorEntityBd> byUsuarioId = fornecedorRepository.findByUsuarioId(usuarioId);
        return byUsuarioId.stream()
                .map(this::toFornecedor)
                .collect(Collectors.toList());
    }

    @Override
    public Fornecedor findByDocumento(String cnpj, String usuarioId) {
        FornecedorEntityBd fornecedorEntityBd = fornecedorRepository.findByDocumentoAndUsuarioId(cnpj, usuarioId).orElseThrow(
                ()->new RecursoNaoEncontradoException("Fornecedor nao encontrado")
        );
        return toFornecedor(fornecedorEntityBd);
    }

    @Override
    public boolean verificaDocumentoBd(String documento, String usuarioId) {
        return fornecedorRepository.existByDOcumentoAndUsuarioId(documento, usuarioId);
    }

    private FornecedorEntityBd from(Fornecedor fornecedor) {
        return FornecedorEntityBd.builder()
                .id(fornecedor.getId())
                .usuario(Usuario.builder()
                        .id(fornecedor.getUsuario().getId())
                        .build())
                .nome(fornecedor.getNome())
                .documento(fornecedor.getDocumento())
                .status(fornecedor.getStatus())
                .dataCreated(fornecedor.getDataCreated())
                .dataUpdated(fornecedor.getDataUpdated())
                .build();
    }

    private Fornecedor toFornecedor(FornecedorEntityBd save){
        return Fornecedor.builder()
                .id(save.getId())
                .usuario(Usuario.builder()
                        .id(save.getUsuario().getId())
                        .build())
                .nome(save.getNome())
                .documento(save.getDocumento())
                .status(save.getStatus())
                .dataCreated(save.getDataCreated())
                .dataUpdated(save.getDataUpdated())
                .build();
    }
}

