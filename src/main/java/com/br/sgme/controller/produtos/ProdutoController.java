package com.br.sgme.controller.produtos;

import com.br.sgme.controller.produtos.dto.ProdutoDto;
import com.br.sgme.ports.ProdutoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.br.sgme.utils.UrlCrossOrigin.URL_CROSS_ORIGIN;

@CrossOrigin(URL_CROSS_ORIGIN)
@RestController
@RequiredArgsConstructor
@RequestMapping("produtos")
public class ProdutoController {
    private final ProdutoUseCase produtoUseCase;

    @GetMapping()
    public List<ProdutoDto>getAllProdutos (@RequestParam UUID idUsuario){
        return produtoUseCase.get((idUsuario).toString());
    }

    @GetMapping("/{id}")
    public ProdutoDto getById(@PathVariable UUID id){
        return produtoUseCase.getById((id).toString());
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> save(@RequestBody @Validated ProdutoDto produtoDto){
        return produtoUseCase.save(produtoDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody ProdutoDto produtoDto){
        return produtoUseCase.update((id).toString(), produtoDto);
    }


}
