package com.br.sgme.port.in;

import com.br.sgme.adapters.in.controller.vendas.dto.VendaDto;
import com.br.sgme.domain.ItemVenda;
import com.br.sgme.domain.Venda;

import java.util.List;

public interface VendaUseCase {
    void save(Venda venda, List<ItemVenda> itemVenda, String token);
    List<Venda> findByUsuarioId(String idUsuario);
    Venda findIdAndUsuarioId(String id, String IdUsuario);

    void cancelarVenda(Venda venda, String token);

    List<Venda> allVendasByCheckoutId(String string, String token);
}
