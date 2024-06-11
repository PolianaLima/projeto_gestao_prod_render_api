package com.br.sgme.adapters.in.controller.fornecedor;

import com.br.sgme.adapters.in.controller.fornecedor.dto.FornecedorDto;
import com.br.sgme.domain.Fornecedor;
import com.br.sgme.domain.enums.Status;
import com.br.sgme.port.in.FornecedorUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("fornecedores")
public class FornecedorController {
    private final FornecedorUseCase fornecedorUseCase;

    @GetMapping()
    public List<FornecedorDto> getAllFornecedores(@RequestHeader("Authorization") String token){
        List<Fornecedor> fornecedors = fornecedorUseCase.get(token);
        return fornecedors.stream()
                .map(FornecedorDto::to)
                .collect(Collectors.toList());
    }


    @GetMapping("/{id}")
    public FornecedorDto getFornecedorId(@PathVariable UUID id,  @RequestHeader("Authorization") String token){
        Fornecedor fornecedor = fornecedorUseCase.getById((id).toString(), token);
        return FornecedorDto.to(fornecedor);
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> save(@RequestBody @Validated FornecedorDto fornecedorDto, @RequestHeader("Authorization") String token){
       Fornecedor fornecedor = Fornecedor.builder()
               .documento(fornecedorDto.getDocumento())
               .nome(fornecedorDto.getNome())
               .build();
         fornecedorUseCase.cadastrar(fornecedor, token);
            return ResponseEntity.ok().build();
    }

    @PutMapping("/{fornecedorID}")
    public ResponseEntity<?> update(@PathVariable String fornecedorID, @RequestBody FornecedorDto fornecedorDto, @RequestHeader("Authorization") String token){
        Fornecedor fornecedor = Fornecedor.builder()
                .id(fornecedorID)
                .documento(fornecedorDto.getDocumento())
                .nome(fornecedorDto.getNome())
                .status(Status.valueOf(fornecedorDto.getStatus().toUpperCase()))
                .build();

        fornecedorUseCase.atualizar(fornecedor, token);
        return ResponseEntity.ok().build();

    }

    @PatchMapping("alterarStatus/{fornecedorID}")
    public ResponseEntity<?> alterarStatus(@PathVariable("fornecedorID") String idFornercedor, @RequestBody FornecedorDto fornecedorDto , @RequestHeader("Authorization") String token) {
        Fornecedor fornecedor = Fornecedor.builder()
                .id(idFornercedor)
                .status(Status.valueOf(fornecedorDto.getStatus()))
                .build();
        fornecedorUseCase.alterarStatus(fornecedor, token);
        return ResponseEntity.ok().build();
    }
}
