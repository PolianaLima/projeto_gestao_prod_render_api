package com.br.sgme.adapters.out.bd.service;

import com.br.sgme.adapters.out.bd.model.Usuario;
import com.br.sgme.config.exceptions.RecursoNaoEncontradoException;
import com.br.sgme.domain.Cliente;
import com.br.sgme.port.out.ClienteAdapterDb;
import com.br.sgme.adapters.out.bd.model.ClienteEntityBd;
import com.br.sgme.adapters.out.bd.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteServiceAdapterImpl implements ClienteAdapterDb {

    private final ClienteRepository clienteRepository;


    @Override
    public Cliente save(Cliente cliente) {
        ClienteEntityBd clienteEntityBd = from(cliente);
        ClienteEntityBd save = clienteRepository.save(clienteEntityBd);
        return toCliente(save);
    }

    @Override
    public Cliente update(Cliente cliente) {
        ClienteEntityBd clienteEntityBd = from(cliente);
        ClienteEntityBd save = clienteRepository.save(clienteEntityBd);
        return toCliente(save);
    }

    @Override
    public Cliente alterarStatus(Cliente cliente) {
        ClienteEntityBd clienteEntityBd = from(cliente);
        ClienteEntityBd save = clienteRepository.save(clienteEntityBd);
        return toCliente(save);
    }

    @Override
    public Cliente findByIdAndUsuarioId(String idCliente, String usuarioId) {
        ClienteEntityBd clienteEntityBd = clienteRepository.findByIdAndUsuarioId(idCliente, usuarioId).orElseThrow(
                ()->new RecursoNaoEncontradoException("Cliente nao encontrado"));
        return toCliente(clienteEntityBd);
    }

    @Override
    public List<Cliente> findByUsuarioId(String usuarioId) {
        List<ClienteEntityBd> byUsuarioId = clienteRepository.findByUsuarioId(usuarioId);
        return  byUsuarioId.stream()
                .map(this::toCliente)
                .collect(Collectors.toList());
    }

    @Override
    public Cliente findByDocumento(String documento, String usuarioId) {
       ClienteEntityBd clienteEntityBd = clienteRepository.findByDocumentoAndUsuarioId(documento, usuarioId).orElseThrow(
               ()->new RecursoNaoEncontradoException("Cliente nao encontrado"));
        return toCliente(clienteEntityBd);
    }

    @Override
    public boolean verificaDocumentoBd(String documento, String usuarioId) {
        return clienteRepository.existByDOcumentoAndUsuarioId(documento, usuarioId);
    }

    private ClienteEntityBd from(Cliente cliente) {
        return ClienteEntityBd.builder()
                .id(cliente.getId())
                .usuario(Usuario.builder()
                        .id(cliente.getUsuario().getId())
                        .build())
                .nome(cliente.getNome())
                .documento(cliente.getDocumento())
                .telefone(cliente.getTelefone())
                .dataNascimento(cliente.getDataNascimento())
                .status(cliente.getStatus())
                .dataCreated(cliente.getDateCreated())
                .dataUpdated(cliente.getDateUpdated())
                .build();
    }

    private Cliente toCliente(ClienteEntityBd save) {
        return Cliente.builder()
                .id(save.getId())
                .usuario(Usuario.builder()
                        .id(save.getUsuario().getId())
                        .build())
                .nome(save.getNome())
                .documento(save.getDocumento())
                .dataNascimento(save.getDataNascimento())
                .telefone(save.getTelefone())
                .status(save.getStatus())
                .dateCreated(save.getDataCreated())
                .dateUpdated(save.getDataUpdated())
                .build();
    }
}
