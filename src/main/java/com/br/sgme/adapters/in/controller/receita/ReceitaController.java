package com.br.sgme.adapters.in.controller.receita;

import com.br.sgme.adapters.in.controller.receita.dto.ReceitaDto;
import com.br.sgme.domain.Cliente;
import com.br.sgme.domain.Receita;
import com.br.sgme.domain.enums.FormasPagamento;
import com.br.sgme.domain.enums.StatusContas;
import com.br.sgme.port.in.ReceitaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.br.sgme.utils.UrlCrossOrigin.URL_CROSS_ORIGIN;

@CrossOrigin(URL_CROSS_ORIGIN)
@RestController
@RequiredArgsConstructor
@RequestMapping("/receitas")
public class ReceitaController {
    private final ReceitaUseCase receitaUseCase;

    @GetMapping()
    public List<ReceitaDto> getAllReceitas(@RequestHeader("Authorization") String token){
        List<Receita> receitas = receitaUseCase.get(token);
        return receitas.stream()
                .map(ReceitaDto::to)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ReceitaDto getById(@PathVariable UUID id, @RequestHeader("Authorization") String token){
       Receita receita = receitaUseCase.getById((id).toString(), token);
        return ReceitaDto.to(receita);
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> save(@RequestBody @Validated ReceitaDto receitaDto, @RequestHeader("Authorization") String token){
        Receita receita = Receita.builder()
                .valor(receitaDto.getValor())
                .cliente(Cliente.builder()
                        .id(receitaDto.getIdCliente())
                        .build())
                .dataVencimento(receitaDto.getDataVencimento())
                .pagamento(FormasPagamento.valueOf(receitaDto.getFormaPagamento().toUpperCase()))
                .status(StatusContas.valueOf(receitaDto.getStatus().toUpperCase()))
                .observacao(receitaDto.getObservacao())
                .build();

        receitaUseCase.save(receita, token);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?>update(@PathVariable UUID id,@RequestBody ReceitaDto receitaDto, @RequestHeader("Authorization") String token){

        Receita receita = Receita.builder()
                .id((id.toString()))
                .valor(receitaDto.getValor())
                .dataVencimento(receitaDto.getDataVencimento())
                .pagamento(FormasPagamento.valueOf(receitaDto.getFormaPagamento().toUpperCase()))
                .observacao(receitaDto.getObservacao())
                .status(StatusContas.valueOf(receitaDto.getStatus().toUpperCase()))
                .build();

        receitaUseCase.update(receita, token);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public void delete (@PathVariable UUID id, @RequestHeader("Authorization") String token){
        receitaUseCase.delete((id).toString(), token);
    }
}
