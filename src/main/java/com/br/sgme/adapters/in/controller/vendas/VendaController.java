package com.br.sgme.adapters.in.controller.vendas;

import com.br.sgme.adapters.in.controller.vendas.dto.VendaDto;
import com.br.sgme.adapters.in.controller.vendas.dto.VendaRequestDto;
import com.br.sgme.domain.Checkout;
import com.br.sgme.domain.ItemVenda;
import com.br.sgme.domain.Venda;
import com.br.sgme.domain.enums.FormasPagamento;
import com.br.sgme.port.in.VendaUseCase;
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
@RequestMapping("/vendas")
public class VendaController {
    private final VendaUseCase vendaUseCase;

    @GetMapping()
    public List<VendaDto> getAllVendas( @RequestHeader("Authorization") String token){
        List<Venda> vendas = vendaUseCase.findByUsuarioId(token);
        return vendas.stream()
                .map(VendaDto::to)
                .collect(java.util.stream.Collectors.toList());
    }

    @PostMapping("/novavenda")
    public ResponseEntity<?> save(@RequestBody @Validated VendaRequestDto vendaRequestDto, @RequestHeader("Authorization") String token) {

        Venda venda = Venda.builder()
                .checkout(Checkout.builder()
                        /*.id(vendaRequestDto.getVendaDto().getIdCheckout())*/
                        .build())
                .pagamento(FormasPagamento.valueOf(vendaRequestDto.getVendaDto().getFormaPagamento().toUpperCase()))
                .build();
        List<ItemVenda> itensVenda = vendaRequestDto.getItensVendaDto().stream()
                .map(itemVendaDto -> ItemVenda.builder()
                        .idProduto(itemVendaDto.getIdProduto())
                        .valorProduto(itemVendaDto.getValorProduto())
                        .descontoProduto(itemVendaDto.getDescontoProduto())
                        .quantidade(itemVendaDto.getQuantidade())
                        .build())
                .collect(Collectors.toList());

        vendaUseCase.save(venda, itensVenda, token);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public VendaDto getVendaId(@PathVariable UUID id, @RequestHeader("Authorization") String token){
        Venda venda = vendaUseCase.findIdAndUsuarioId(id.toString(), token);
        return VendaDto.to(venda);
    }

    @PutMapping("/cancelar/{id}")
    public ResponseEntity<?> cancelarVenda(@PathVariable UUID id,@RequestBody VendaDto vendaDto, @RequestHeader("Authorization") String token){
       Venda vendaCancelada = Venda.builder()
               .id(id.toString())
               .build();
        vendaUseCase.cancelarVenda(vendaCancelada, token);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/checkout/{id}")
    public List<VendaDto> getAllVendasByPdvId(@PathVariable UUID id, @RequestHeader("Authorization") String token){
        List<Venda> vendas = vendaUseCase.allVendasByCheckoutId(id.toString(), token);
        return vendas.stream()
                .map(VendaDto::to)
                .collect(Collectors.toList());
    }

}
