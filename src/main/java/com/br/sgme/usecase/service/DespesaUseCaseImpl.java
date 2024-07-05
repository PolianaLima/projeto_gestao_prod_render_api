package com.br.sgme.usecase.service;

import com.br.sgme.adapters.in.controller.despesa.dto.DespesaDto;
import com.br.sgme.adapters.out.bd.model.Usuario;
import com.br.sgme.config.exceptions.ErrorDataException;
import com.br.sgme.config.exceptions.ErrorDetails;
import com.br.sgme.domain.Despesa;
import com.br.sgme.domain.Fornecedor;
import com.br.sgme.port.in.DespesaUseCase;
import com.br.sgme.port.out.DespesaAdapterDb;
import com.br.sgme.port.out.ForncedorAdapterDb;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DespesaUseCaseImpl implements DespesaUseCase {

    private final DespesaAdapterDb despesaAdapterDb;
    private final UsuarioLogado usuarioLogado;
    private final ForncedorAdapterDb forncedorAdapterDb;

    @Override
    public void save(Despesa despesa, String token) {
        Usuario usuario = getUsuarioLogado(token);
        Fornecedor fornecedor = getFornecedor(despesa.getFornecedor().getId(), usuario.getId());
        verificarDataVencimento(despesa);
        Despesa despesaSalvar = Despesa.builder()
                .usuario(usuario)
                .fornecedor(fornecedor)
                .valor(despesa.getValor())
                .dataVencimento(despesa.getDataVencimento())
                .pagamento(despesa.getPagamento())
                .observacao(despesa.getObservacao())
                .status(despesa.getStatus())
                .dataCreated(LocalDateTime.now())
                .build();

        despesaAdapterDb.save(despesaSalvar);
    }


    @Override
    public void update(Despesa despesa, String token) {
        Usuario usuario = getUsuarioLogado(token);
        Despesa despesaSelecionada = getDespesaById(despesa.getId(), usuario.getId());

        verificarDataVencimentoUpdate(despesa, despesaSelecionada);

        Despesa despesaUpdate = Despesa.builder()
                .id(despesaSelecionada.getId())
                .usuario(despesaSelecionada.getUsuario())
                .fornecedor(despesaSelecionada.getFornecedor())
                .valor(despesa.getValor())
                .dataVencimento(despesa.getDataVencimento())
                .status(despesa.getStatus())
                .pagamento(despesa.getPagamento())
                .observacao(despesa.getObservacao())
                .dataCreated(despesaSelecionada.getDataCreated())
                .dataUpdated(LocalDateTime.now())
                .build();

        despesaAdapterDb.update(despesaUpdate);

    }

    @Override
    public List<Despesa> get(String token) {
       Usuario usuario = getUsuarioLogado(token);
       return new ArrayList<>(despesaAdapterDb.findByUsuarioId(usuario.getId()));
    }

    @Override
    public Despesa getById(String id, String token) {
        Usuario usuarioEntityBd = getUsuarioLogado(token);
        return despesaAdapterDb.findByIdAndUsuarioId(id, usuarioEntityBd.getId());
    }

    @Override
    public void delete(String id, String token) {
        Usuario usuario = getUsuarioLogado(token);
        despesaAdapterDb.delete(id, usuario.getId());

    }


    private void verificarDataVencimento(Despesa despesa) {
        if (despesa.getDataVencimento().isBefore(ChronoLocalDate.from(LocalDateTime.now()))) {
            throw new ErrorDataException("Data de vencimento não pode ser menor que a data atual");
        }
    }

    private void verificarDataVencimentoUpdate(Despesa despesa, Despesa despesaSelecionada) {
        if(!despesaSelecionada.getDataVencimento().isEqual(despesa.getDataVencimento()) &&
                despesa.getDataVencimento().isBefore(ChronoLocalDate.from(LocalDateTime.now()))){
           throw new ErrorDataException("Data de vencimento não pode ser menor que a data atual");
        }
    }

    private Usuario getUsuarioLogado(String token) {
        return usuarioLogado.getUsuarioLogado(token);
    }

    private Fornecedor getFornecedor(String idFornecedor, String idUsuario) {
        return forncedorAdapterDb.findByIdAndUsuarioId(idFornecedor, idUsuario);
    }

    private Despesa getDespesaById(String idDespesa, String idUsuario) {
        return despesaAdapterDb.findByIdAndUsuarioId(idDespesa, idUsuario);
    }

}
