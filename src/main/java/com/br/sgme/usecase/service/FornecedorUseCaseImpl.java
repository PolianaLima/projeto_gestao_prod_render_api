package com.br.sgme.usecase.service;

import com.br.sgme.config.exceptions.ErrorDataException;
import com.br.sgme.adapters.out.bd.model.Usuario;
import com.br.sgme.domain.Fornecedor;
import com.br.sgme.domain.enums.Status;
import com.br.sgme.port.in.FornecedorUseCase;
import com.br.sgme.port.out.ForncedorAdapterDb;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FornecedorUseCaseImpl implements FornecedorUseCase {
    private final ForncedorAdapterDb forncedorAdapterDb;
    private final UsuarioLogado usuarioLogado;

    @Override
    public void cadastrar(Fornecedor fornecedor, String token) {
        Usuario usuario = getUsuarioLogado(token);
        verificaDocumento(fornecedor, usuario.getId());

        Fornecedor fornecedorSalvar  = Fornecedor.builder()
                .usuario(usuario)
                .documento(fornecedor.getDocumento())
                .nome(fornecedor.getNome())
                .status(Status.ATIVO)
                .dataCreated(LocalDateTime.now())
                .build();
        forncedorAdapterDb.save(fornecedorSalvar);

    }

    @Override
    public void atualizar(Fornecedor fornecedor, String token) {
        Usuario usuario = getUsuarioLogado(token);

        Fornecedor fornecedorSelecionado = getDocumentoById(fornecedor.getId(), usuario.getId());
        verificarAutorizacaoUsuario(fornecedorSelecionado.getUsuario(), usuario);

        verificarDocumentoAlterado(fornecedor, fornecedorSelecionado);

        Fornecedor fornecedorUpdate = Fornecedor.builder()
                .id(fornecedorSelecionado.getId())
                .usuario(fornecedorSelecionado.getUsuario())
                .documento(fornecedor.getDocumento())
                .nome(fornecedor.getNome())
                .status(fornecedor.getStatus())
                .dataCreated(fornecedorSelecionado.getDataCreated())
                .dataUpdated(LocalDateTime.now())
                .build();
        forncedorAdapterDb.update(fornecedorUpdate);
    }

    @Override
    public List<Fornecedor> get(String token) {
        Usuario usuario = getUsuarioLogado(token);
        return new ArrayList<>(forncedorAdapterDb.findByUsuarioId(usuario.getId()));
    }

    @Override
    public Fornecedor getById(String id, String token) {
        Usuario usuario = getUsuarioLogado(token);
        return forncedorAdapterDb.findByIdAndUsuarioId(id, usuario.getId());
    }

    @Override
    public Fornecedor getByDocumento(String idUsuario, String token) {
        Usuario usuario = getUsuarioLogado(token);

        return forncedorAdapterDb.findByDocumento(idUsuario, usuario.getId());
    }

    @Override
    public void alterarStatus(Fornecedor fornecedor, String token) {
        Usuario usuario = getUsuarioLogado(token);
        Fornecedor fornecedorSelecionado = getDocumentoById(fornecedor.getId(), usuario.getId());

        Fornecedor fornecedorAlterar = Fornecedor.builder()
                .id(fornecedorSelecionado.getId())
                .usuario(fornecedorSelecionado.getUsuario())
                .documento(fornecedorSelecionado.getDocumento())
                .nome(fornecedorSelecionado.getNome())
                .status(fornecedor.getStatus())
                .dataCreated(fornecedorSelecionado.getDataCreated())
                .dataUpdated(LocalDateTime.now())
                .build();

        forncedorAdapterDb.update(fornecedorAlterar);
    }


    private void verificaDocumento(Fornecedor data, String idUsuario) {
        if (!data.getDocumento().isEmpty()){
            boolean verificaedDocumentoBd = forncedorAdapterDb.verificaDocumentoBd(data.getDocumento(), idUsuario);
            if(verificaedDocumentoBd){
                throw new ErrorDataException("Documento ja cadastrado");
            }
        }
    }

    private void verificarDocumentoAlterado(Fornecedor fornecedorUpdate, Fornecedor fornecedorSelecionado) {
        boolean verificaDocumentoBd = forncedorAdapterDb.verificaDocumentoBd(fornecedorUpdate.getDocumento(), fornecedorSelecionado.getUsuario().getId());
        if (verificaDocumentoBd && !Objects.equals(fornecedorSelecionado.getDocumento(), fornecedorUpdate.getDocumento())) {
            Fornecedor fornecedor = forncedorAdapterDb.findByDocumento(fornecedorUpdate.getDocumento(), fornecedorSelecionado.getUsuario().getId());
            if(!Objects.equals(fornecedor.getDocumento(), fornecedorSelecionado.getDocumento())){
                throw new ErrorDataException("Documento já cadastrado");
            }
        }
    }

    private void verificarAutorizacaoUsuario(Usuario usuarioLogado, Usuario usuarioClienteUpdate) {
        if (!Objects.equals(usuarioLogado.getId(), usuarioClienteUpdate.getId())) {
            throw new ErrorDataException("Usuario nao autorizado");
        }
    }

    private Fornecedor getDocumentoById(String idFornecedor, String idUsuario) {
        return forncedorAdapterDb.findByIdAndUsuarioId(idFornecedor, idUsuario);
    }

    private Usuario getUsuarioLogado(String token) {
        return usuarioLogado.getUsuarioLogado(token);
    }

}
