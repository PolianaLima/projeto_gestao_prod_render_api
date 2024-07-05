package com.br.sgme.adapters.in.controller.despesa;

import com.br.sgme.adapters.in.controller.despesa.dto.DespesaDto;
import com.br.sgme.domain.Despesa;
import com.br.sgme.domain.Fornecedor;
import com.br.sgme.domain.enums.FormasPagamento;
import com.br.sgme.domain.enums.StatusContas;
import com.br.sgme.port.in.DespesaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/despesas")
public class DespesaController {
    private final DespesaUseCase despesaUseCase;

    @GetMapping()
    public List<DespesaDto>getAllDespesas(@RequestHeader("Authorization") String token){
        List<Despesa> despesas = despesaUseCase.get(token);
        return despesas.stream()
                .map(DespesaDto::to)
                .collect(java.util.stream.Collectors.toList());

    }

    @GetMapping("/{id}")
    public DespesaDto getById(@PathVariable UUID id, @RequestHeader("Authorization") String token){
        Despesa despesa = despesaUseCase.getById((id).toString(), token);
        return DespesaDto.to(despesa);
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> save(@RequestBody @Validated DespesaDto despesaDto, @RequestHeader("Authorization") String token){
        Despesa despesa = Despesa.builder()
                .valor(despesaDto.getValor())
                .fornecedor(Fornecedor.builder()
                        .id(despesaDto.getIdFornecedor())
                        .build())
                .dataVencimento(despesaDto.getDataVencimento())
                .pagamento(FormasPagamento.valueOf(despesaDto.getFormaPagamento().toUpperCase()))
                .status(StatusContas.valueOf(despesaDto.getStatus().toUpperCase()))
                .observacao(despesaDto.getObservacao())
                .build();

        despesaUseCase.save(despesa, token);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{despesaId}")
    public ResponseEntity<?>update(@PathVariable UUID despesaId,@RequestBody DespesaDto despesaDto, @RequestHeader("Authorization") String token){
        Despesa despesa = Despesa.builder()
                .id((despesaId).toString())
                .valor(despesaDto.getValor())
                .dataVencimento(despesaDto.getDataVencimento())
                .pagamento(FormasPagamento.valueOf(despesaDto.getFormaPagamento().toUpperCase()))
                .observacao(despesaDto.getObservacao())
                .status(StatusContas.valueOf(despesaDto.getStatus().toUpperCase()))
                .build();
        despesaUseCase.update(despesa, token);
        return ResponseEntity.ok().build();

    }

    @DeleteMapping("/{id}")
    public void delete (@PathVariable UUID id, @RequestHeader("Authorization") String token){
        despesaUseCase.delete((id).toString(), token);
    }
}
