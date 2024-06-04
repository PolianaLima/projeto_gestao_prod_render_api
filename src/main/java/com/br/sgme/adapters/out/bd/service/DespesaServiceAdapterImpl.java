package com.br.sgme.adapters.out.bd.service;

import com.br.sgme.adapters.out.bd.model.DespesaEntityBd;
import com.br.sgme.adapters.out.bd.model.FornecedorEntityBd;
import com.br.sgme.adapters.out.bd.model.Usuario;
import com.br.sgme.adapters.out.bd.repository.DespesaRepository;
import com.br.sgme.config.exceptions.RecursoNaoEncontradoException;
import com.br.sgme.domain.Despesa;
import com.br.sgme.domain.Fornecedor;
import com.br.sgme.port.out.DespesaAdapterDb;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DespesaServiceAdapterImpl implements DespesaAdapterDb {

    private final  DespesaRepository despesaRepository;

    @Override
    public Despesa save(Despesa despesa) {
        DespesaEntityBd despesaEntityBd = from(despesa);
        DespesaEntityBd save = despesaRepository.save(despesaEntityBd);
        return toDespesa(save);
    }

    @Override
    public Despesa update(Despesa despesa) {
        DespesaEntityBd despesaEntityBd = from(despesa);
        DespesaEntityBd save = despesaRepository.save(despesaEntityBd);
        return toDespesa(save);
    }

    @Override
    public Despesa alterarStatus(Despesa despesa) {
        DespesaEntityBd despesaEntityBd = from(despesa);
        DespesaEntityBd save = despesaRepository.save(despesaEntityBd);
        return toDespesa(save);
    }

    @Override
    public Despesa findByIdAndUsuarioId(String idDespesa, String usuarioId) {
        DespesaEntityBd despesaEntityBd = despesaRepository.findByIdAndUsuarioId(idDespesa, usuarioId).orElseThrow(
                ()->new RecursoNaoEncontradoException("Despesa nao encontrada"));
        return toDespesa(despesaEntityBd);
    }

    @Override
    public List<Despesa> findByUsuarioId(String usuarioId) {
        List<DespesaEntityBd> byUsuarioId = despesaRepository.findByUsuarioId(usuarioId);
        return byUsuarioId.stream()
                .map(this::toDespesa)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String idDespesa, String usuarioId) {
        despesaRepository.deleteByIdAndUsuarioId(idDespesa, usuarioId);
    }

    private DespesaEntityBd from(Despesa despesa){
        return DespesaEntityBd.builder()
                .id(despesa.getId())
                .usuario(Usuario.builder()
                        .id(despesa.getUsuario().getId())
                        .build())
                .fornecedor(FornecedorEntityBd.builder()
                        .id(despesa.getFornecedor().getId())
                        .build())
                .valor(despesa.getValor())
                .dataVencimento(despesa.getDataVencimento())
                .pagamento(despesa.getPagamento())
                .observacao(despesa.getObservacao())
                .status(despesa.getStatus())
                .dataCreated(despesa.getDataCreated())
                .dataUpdated(despesa.getDataUpdated())
                .build();
    }

    private Despesa toDespesa(DespesaEntityBd despesaEntityBd){
        return Despesa.builder()
                .id(despesaEntityBd.getId())
                .usuario(Usuario.builder()
                        .id(despesaEntityBd.getUsuario().getId())
                        .build())
                .fornecedor(Fornecedor.builder()
                        .id(despesaEntityBd.getFornecedor().getId())
                        .nome(despesaEntityBd.getFornecedor().getNome())
                        .build())
                .valor(despesaEntityBd.getValor())
                .dataVencimento(despesaEntityBd.getDataVencimento())
                .pagamento(despesaEntityBd.getPagamento())
                .observacao(despesaEntityBd.getObservacao())
                .status(despesaEntityBd.getStatus())
                .dataCreated(despesaEntityBd.getDataCreated())
                .dataUpdated(despesaEntityBd.getDataUpdated())
                .build();
    }
}
