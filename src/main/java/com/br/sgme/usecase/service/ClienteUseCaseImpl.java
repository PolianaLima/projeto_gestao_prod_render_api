package com.br.sgme.usecase.service;


import com.br.sgme.adapters.out.bd.model.Usuario;
import com.br.sgme.config.exceptions.ErrorDataException;
import com.br.sgme.domain.Cliente;
import com.br.sgme.domain.enums.Status;
import com.br.sgme.port.in.ClienteUseCase;
import com.br.sgme.port.out.ClienteAdapterDb;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ClienteUseCaseImpl implements ClienteUseCase {

    private final ClienteAdapterDb clienteAdapterDb;

    private final UsuarioLogado usuarioLogado;
    private final AutorizacaoUsuario autorizacaoUsuario;


    @Override
    public void cadastrar(Cliente cliente, String token) {
        Usuario usuario = getUsuarioLogado(token);
        verificaDocumento(cliente, usuario.getId());

        Cliente clienteSalvar = Cliente.builder()
                .usuario(usuario)
                .documento(cliente.getDocumento())
                .nome(cliente.getNome())
                .dataNascimento(cliente.getDataNascimento())
                .telefone(cliente.getTelefone())
                .status(Status.ATIVO)
                .dateCreated(LocalDateTime.now())
                .build();
        clienteAdapterDb.save(clienteSalvar);
    }

    @Override
    public void atualizar(Cliente cliente, String token) {

            Usuario usuario = getUsuarioLogado(token);

            Cliente clienteSelecionado = getClienteById(cliente.getId(), usuario.getId());
            verificarDocumentoAlterado(cliente, clienteSelecionado);

            Cliente clienteUpdate = Cliente.builder()
                    .id(clienteSelecionado.getId())
                    .usuario(clienteSelecionado.getUsuario())
                    .documento(cliente.getDocumento())
                    .nome(cliente.getNome())
                    .dataNascimento(cliente.getDataNascimento())
                    .telefone(cliente.getTelefone())
                    .status(cliente.getStatus())
                    .dateCreated(clienteSelecionado.getDateCreated())
                    .dateUpdated(LocalDateTime.now())
                    .build();

            clienteAdapterDb.update(clienteUpdate);
    }

    @Override
    public List<Cliente> get(String token) {
        Usuario usuario = getUsuarioLogado(token);
        return new ArrayList<>(clienteAdapterDb.findByUsuarioId(usuario.getId()));
    }


    @Override
    public Cliente getId(String id, String token) {
        Usuario usuario = getUsuarioLogado(token);
        return  clienteAdapterDb.findByIdAndUsuarioId(id, usuario.getId());
    }

    @Override
    public Cliente getByDocumento(String documento, String token) {
        Usuario usuario = getUsuarioLogado(token);
        return clienteAdapterDb.findByDocumento(documento, usuario.getId());
    }

    @Override
    public void alterarStatus(Cliente cliente, String token) {
        Usuario usuario = getUsuarioLogado(token);
        Cliente clienteSelecionado = getClienteById(cliente.getId(), usuario.getId());
        verificarAutorizacaoUsuario(clienteSelecionado.getUsuario(), usuario);

        Cliente clienteAlterar = Cliente.builder()
                .id(clienteSelecionado.getId())
                .usuario(clienteSelecionado.getUsuario())
                .documento(clienteSelecionado.getDocumento())
                .nome(clienteSelecionado.getNome())
                .dataNascimento(clienteSelecionado.getDataNascimento())
                .telefone(clienteSelecionado.getTelefone())
                .status(cliente.getStatus())
                .dateCreated(clienteSelecionado.getDateCreated())
                .dateUpdated(LocalDateTime.now())
                .build();
        clienteAdapterDb.alterarStatus(clienteAlterar);
    }

    private void verificaDocumento(Cliente data, String idUsuario) {
        if (!data.getDocumento().isEmpty()){
            boolean verificaedDocumentoBd = clienteAdapterDb.verificaDocumentoBd(data.getDocumento(), idUsuario);
            if(verificaedDocumentoBd){
                throw new ErrorDataException("Documento ja cadastrado");
            }
        }
    }

    private void verificarDocumentoAlterado(Cliente clienteUpdate, Cliente clienteSelecionado) {
        boolean verificaDocumentoBd = clienteAdapterDb.verificaDocumentoBd(clienteUpdate.getDocumento(), clienteSelecionado.getUsuario().getId());
        if (verificaDocumentoBd && !Objects.equals(clienteSelecionado.getDocumento(), clienteUpdate.getDocumento())) {
            Cliente cliente = clienteAdapterDb.findByDocumento(clienteUpdate.getDocumento(), clienteSelecionado.getUsuario().getId());
            if(!Objects.equals(cliente.getDocumento(), clienteSelecionado.getDocumento())){
                throw new ErrorDataException("Cpf já cadastrado");
            }
        }
    }

    private void verificarAutorizacaoUsuario(Usuario usuarioLogado, Usuario usuarioClienteUpdate) {
        autorizacaoUsuario.autorizacaoUsuario(usuarioLogado, usuarioClienteUpdate);
    }

    private Cliente getClienteById(String idCliente, String idUsuario) {
        return clienteAdapterDb.findByIdAndUsuarioId(idCliente, idUsuario);
    }

    private Usuario getUsuarioLogado(String token) {
        return usuarioLogado.getUsuarioLogado(token);
    }

}
