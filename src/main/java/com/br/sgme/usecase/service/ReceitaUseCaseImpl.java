package com.br.sgme.usecase.service;

import com.br.sgme.config.exceptions.ErrorDataException;
import com.br.sgme.domain.Cliente;
import com.br.sgme.domain.Receita;
import com.br.sgme.adapters.out.bd.model.Usuario;
import com.br.sgme.port.ReceitaAdapterDb;
import com.br.sgme.port.in.ReceitaUseCase;
import com.br.sgme.port.out.ClienteAdapterDb;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReceitaUseCaseImpl implements ReceitaUseCase {

    private final ReceitaAdapterDb receitaAdapterDb;
    private final UsuarioLogado usuarioLogado;
    private final ClienteAdapterDb clienteAdapterDb;

    @Override
    public void save(Receita receita, String token) {
        Usuario usuario = getUsuarioLogado(token);
        Cliente cliente = getCliente(receita.getCliente().getId(), usuario.getId());
        verificaDataVencimento(receita);
        Receita receitaSalvar = Receita.builder()
                .usuario(usuario)
                .cliente(cliente)
                .valor(receita.getValor())
                .dataVencimento(receita.getDataVencimento())
                .pagamento(receita.getPagamento())
                .observacao(receita.getObservacao())
                .status(receita.getStatus())
                .dataCreated(LocalDateTime.now())
                .build();

        receitaAdapterDb.save(receitaSalvar);
    }

    @Override
    public void update(Receita receita, String token) {

        Usuario usuario = getUsuarioLogado(token);
        Receita receitaSelecionada = getDespesaById(receita.getId(), usuario.getId());

        verificarDataVencimentoUpdate(receita, receitaSelecionada);

        Receita receitaUpdate = Receita.builder()
                .id(receitaSelecionada.getId())
                .usuario(receitaSelecionada.getUsuario())
                .cliente(receitaSelecionada.getCliente())
                .valor(receita.getValor())
                .dataVencimento(receita.getDataVencimento())
                .pagamento(receita.getPagamento())
                .observacao(receita.getObservacao())
                .status(receita.getStatus())
                .dataCreated(receitaSelecionada.getDataCreated())
                .dataUpdated(LocalDateTime.now())
                .build();

        receitaAdapterDb.update(receitaUpdate);

    }

    @Override
    public List<Receita> get(String idUsuario) {
        Usuario usuario = getUsuarioLogado(idUsuario);
        return new ArrayList<>(receitaAdapterDb.findByUsuarioId(usuario.getId()));
    }

    @Override
    public Receita getById(String id, String token) {
        Usuario usuario = getUsuarioLogado(token);
        return receitaAdapterDb.findByIdAndUsuarioId(id, usuario.getId());
    }

    @Override
    public void delete(String id, String token) {
        Usuario usuario = getUsuarioLogado(token);
        receitaAdapterDb.delete(id, usuario.getId());

    }

    private void verificaDataVencimento(Receita receita) {
        if (receita.getDataVencimento().isBefore(ChronoLocalDate.from(LocalDateTime.now()))) {
            throw new ErrorDataException("Data de vencimento não pode ser menor que a data atual");
        }
    }

    private void verificarDataVencimentoUpdate(Receita receita, Receita receitaSelecionada) {
        if (!receita.getDataVencimento().isEqual(receitaSelecionada.getDataVencimento()) &&
        receita.getDataVencimento().isBefore(ChronoLocalDate.from(LocalDateTime.now()))) {
            throw new ErrorDataException("Data de vencimento não pode ser menor que a data atual");
        }
    }

    private Usuario getUsuarioLogado(String token) {
        return usuarioLogado.getUsuarioLogado(token);
    }

    private Cliente getCliente(String idFornecedor, String idUsuario) {
        return clienteAdapterDb.findByIdAndUsuarioId(idFornecedor, idUsuario);
    }

    private Receita getDespesaById(String idDespesa, String idUsuario) {
        return receitaAdapterDb.findByIdAndUsuarioId(idDespesa, idUsuario);
    }
}
