package com.br.sgme.port.out;

import com.br.sgme.domain.Venda;

import java.util.List;

public interface VendaAdapterDb {
    Venda save(Venda venda);
    Venda cancelarVenda(Venda venda);
    List<Venda> get(String idUsuario);
    Venda getById(String idVenda, String idUsuario);

   List <Venda> allVendasByCheckoutId(String string, String id);
}
