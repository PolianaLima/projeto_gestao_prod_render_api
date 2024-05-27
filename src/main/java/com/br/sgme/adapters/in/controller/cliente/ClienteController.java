package com.br.sgme.adapters.in.controller.cliente;

import com.br.sgme.adapters.in.controller.cliente.dto.ClienteDto;
import com.br.sgme.domain.Cliente;
import com.br.sgme.domain.enums.Status;
import com.br.sgme.port.in.ClienteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.br.sgme.utils.UrlCrossOrigin.URL_CROSS_ORIGIN;

@CrossOrigin(URL_CROSS_ORIGIN)
@RestController
@RequiredArgsConstructor
@RequestMapping("clientes")
public class ClienteController {

    private final ClienteUseCase clienteUseCase;

    @GetMapping()
    public List<ClienteDto> getAllClientes(@RequestHeader("Authorization") String token) {
        List<Cliente> clientes = clienteUseCase.get(token);
        return clientes.stream()
                .map(ClienteDto::to)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ClienteDto getClienteById(@PathVariable UUID id, @RequestHeader("Authorization") String token) {
        Cliente cliente = clienteUseCase.getId((id).toString(), token);
        return ClienteDto.to(cliente);
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> save(@RequestBody @Validated ClienteDto clienteDto, @RequestHeader("Authorization") String token) {
        Cliente cliente = Cliente.builder()
                .nome(clienteDto.getNome())
                .documento(clienteDto.getDocumento())
                .dataNascimento(clienteDto.getDataNascimento())
                .telefone(clienteDto.getTelefone())
                .build();
        clienteUseCase.cadastrar(cliente, token);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{clienteID}")
    public ResponseEntity<?> update(@PathVariable String clienteID, @RequestBody ClienteDto clienteDto, @RequestHeader("Authorization") String token) {
        Cliente cliente = Cliente.builder()
                .id(clienteID)
                .nome(clienteDto.getNome())
                .documento(clienteDto.getDocumento())
                .dataNascimento(clienteDto.getDataNascimento())
                .telefone(clienteDto.getTelefone())
                .status(Status.valueOf(clienteDto.getStatus()))
                .build();
        clienteUseCase.atualizar(cliente, token);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("alterarStatus/{clienteId}")
    public ResponseEntity<?> alterarStatus(@PathVariable("clienteId") String id, @RequestBody ClienteDto clienteDto , @RequestHeader("Authorization") String token) {
        Cliente cliente = Cliente.builder()
                .id(id)
                .status(Status.valueOf(clienteDto.getStatus()))
                .build();
        clienteUseCase.alterarStatus(cliente, token);
        return ResponseEntity.ok().build();
    }

}
