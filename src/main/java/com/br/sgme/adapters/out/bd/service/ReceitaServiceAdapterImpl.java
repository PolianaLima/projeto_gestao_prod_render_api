package com.br.sgme.adapters.out.bd.service;

import com.br.sgme.adapters.out.bd.model.ClienteEntityBd;
import com.br.sgme.adapters.out.bd.model.ReceitaEntityBd;
import com.br.sgme.adapters.out.bd.model.Usuario;
import com.br.sgme.adapters.out.bd.repository.ReceitaRepository;
import com.br.sgme.config.exceptions.RecursoNaoEncontradoException;
import com.br.sgme.domain.Cliente;
import com.br.sgme.domain.Receita;
import com.br.sgme.port.ReceitaAdapterDb;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReceitaServiceAdapterImpl implements ReceitaAdapterDb {

    private final ReceitaRepository receitaRepository;

    @Override
    public Receita save(Receita receita) {
        ReceitaEntityBd receitaEntityBd = from(receita);
        ReceitaEntityBd save = receitaRepository.save(receitaEntityBd);
        return toReceita(save);
    }

    @Override
    public Receita update(Receita receita) {
        ReceitaEntityBd receitaEntityBd = from(receita);
        ReceitaEntityBd save = receitaRepository.save(receitaEntityBd);
        return toReceita(save);
    }

    @Override
    public Receita alterarStatus(Receita receita) {
        ReceitaEntityBd receitaEntityBd = from(receita);
        ReceitaEntityBd save = receitaRepository.save(receitaEntityBd);
        return toReceita(save);
    }

    @Override
    public Receita findByIdAndUsuarioId(String idDespesa, String usuarioId) {
        ReceitaEntityBd receitaEntityBd = receitaRepository.findByIdAndUsuarioId(idDespesa, usuarioId).orElseThrow(
                () -> new RecursoNaoEncontradoException("Receita nao encontrada")
        );
        return toReceita(receitaEntityBd);
    }

    @Override
    public List<Receita> findByUsuarioId(String usuarioId) {
        List<ReceitaEntityBd> byUsuarioId = receitaRepository.findByUsuarioId(usuarioId);
        return byUsuarioId.stream()
                .map(this::toReceita)
                .collect(Collectors.toList());

    }

    @Override
    public void delete(String idDespesa, String usuarioId) {
        receitaRepository.deleteByIdAndUsuarioId(idDespesa, usuarioId);
    }

    private ReceitaEntityBd from(Receita receita) {
        return ReceitaEntityBd.builder()
                .id(receita.getId())
                .usuario(Usuario.builder()
                        .id(receita.getUsuario().getId())
                        .build())
                .clienteEntityBd(ClienteEntityBd.builder()
                        .id(receita.getCliente().getId())
                        .build())
                .valor(receita.getValor())
                .dataVencimento(receita.getDataVencimento())
                .pagamento(receita.getPagamento())
                .observacao(receita.getObservacao())
                .status(receita.getStatus())
                .dataCreated(receita.getDataCreated())
                .dataUpdated(receita.getDataUpdated())
                .build();
    }

    private Receita toReceita(ReceitaEntityBd receitaEntityBd) {
        return Receita.builder()
                .id(receitaEntityBd.getId())
                .usuario(Usuario.builder()
                        .id(receitaEntityBd.getUsuario().getId())
                        .build())
                .cliente(Cliente.builder()
                        .id(receitaEntityBd.getClienteEntityBd().getId())
                        .nome(receitaEntityBd.getClienteEntityBd().getNome())
                        .build())
                .valor(receitaEntityBd.getValor())
                .dataVencimento(receitaEntityBd.getDataVencimento())
                .pagamento(receitaEntityBd.getPagamento())
                .observacao(receitaEntityBd.getObservacao())
                .status(receitaEntityBd.getStatus())
                .dataCreated(receitaEntityBd.getDataCreated())
                .dataUpdated(receitaEntityBd.getDataUpdated())
                .build();
    }
}
