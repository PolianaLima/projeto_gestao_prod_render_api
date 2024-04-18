package com.br.sgme.service;

import com.br.sgme.controller.cliente.dto.ClienteDto;
import com.br.sgme.controller.despesa.dto.DespesaDto;
import com.br.sgme.enums.FormasPagamento;
import com.br.sgme.exceptions.ErrorDetails;
import com.br.sgme.exceptions.RecursoNaoEncontradoException;
import com.br.sgme.model.Despesa;
import com.br.sgme.model.Fornecedor;
import com.br.sgme.model.usuario.Usuario;
import com.br.sgme.ports.DespesaUseCase;
import com.br.sgme.repository.DespesaRepository;
import com.br.sgme.repository.FornecedorRepository;
import com.br.sgme.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DespesaUseCaseImpl implements DespesaUseCase {

    private final DespesaRepository despesaRepository;
    private final UsuarioRepository usuarioRepository;
    private final FornecedorRepository fornecedorRepository;

    @Override
    public ResponseEntity<?> save(DespesaDto despesaDto) {

        Usuario usuario = usuarioRepository.findById(despesaDto.getIdUsuario()).get();
        Fornecedor fornecedor = fornecedorRepository.findById(despesaDto.getIdFornecedor()).get();

        String observacao = despesaDto.getObservacao();

        if (observacao == null) observacao = "";

        ResponseEntity<ErrorDetails> dataVencimento = validaDataVencimento(despesaDto);
        if (dataVencimento != null) return dataVencimento;


        despesaRepository.save(Despesa.builder()
                .usuario(usuario)
                .fornecedor(fornecedor)
                .valor(despesaDto.getValor())
                .dataVencimento(despesaDto.getDataVencimento())
                .status(despesaDto.getStatus())
                .pagamento(FormasPagamento.valueOf(despesaDto.getFormaPagamento()))
                .observacao(observacao)
                .data_created(LocalDateTime.now().toLocalDate())
                .build());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<?> update(String id, DespesaDto despesaDto) {

        try {
            Despesa despesaSelecionada = despesaRepository.findById(id).get();

            despesaRepository.save(Despesa.builder()
                    .id(despesaSelecionada.getId())
                    .usuario(despesaSelecionada.getUsuario())
                    .fornecedor(despesaSelecionada.getFornecedor())
                    .valor(despesaDto.getValor())
                    .dataVencimento(despesaDto.getDataVencimento())
                    .status(despesaDto.getStatus())
                    .pagamento(FormasPagamento.valueOf(despesaDto.getFormaPagamento()))
                    .observacao(despesaDto.getObservacao())
                    .data_created(despesaSelecionada.getData_created())
                    .data_updated(LocalDateTime.now().toLocalDate())
                    .build());

            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (NoSuchElementException exception) {
            throw new RecursoNaoEncontradoException("Despesa nao encontrada");
        }
    }

    @Override
    public List<DespesaDto> get(String idUsuario) {
        return despesaRepository.findByUsuarioId(idUsuario)
                .stream()
                .map(DespesaDto::to)
                .collect(Collectors.toList());
    }

    @Override
    public DespesaDto getById(String id) {
        return despesaRepository.findById(id)
                .stream()
                .map(DespesaDto::to)
                .findFirst()
                .orElseThrow(() -> new RecursoNaoEncontradoException("Despesa nao encontrada"));
    }

    @Override
    public void delete(String id) {
        if (despesaRepository.findById(id).isEmpty()) throw new RecursoNaoEncontradoException("Despesa nao encontrada");
        despesaRepository.deleteById(id);

    }


    private ResponseEntity<ErrorDetails> validaDataVencimento(DespesaDto data) {
        if (data.getDataVencimento().isBefore(ChronoLocalDate.from(LocalDateTime.now()))) {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .body(new ErrorDetails("Data de vencimento invalida, a data de vencimento deve ser maior que a data atual", LocalDateTime.now(), HttpStatus.UNPROCESSABLE_ENTITY.value()));
        }
        return null;
    }
}
