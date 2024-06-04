package com.br.sgme.port.out;

import com.br.sgme.domain.ItemVenda;

import java.util.List;

public interface ItemVendaAdapterDb {
    List<ItemVenda> saveAll(List<ItemVenda> item);
    List<ItemVenda> findByVendaId(String vendaId);
}
