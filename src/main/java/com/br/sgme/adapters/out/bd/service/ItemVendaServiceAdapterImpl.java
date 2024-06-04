package com.br.sgme.adapters.out.bd.service;


import com.br.sgme.adapters.out.bd.model.ItemVendaEntityBd;
import com.br.sgme.adapters.out.bd.model.VendaEntityDb;
import com.br.sgme.adapters.out.bd.repository.ItemVendaRepository;
import com.br.sgme.domain.ItemVenda;
import com.br.sgme.domain.Venda;
import com.br.sgme.port.out.ItemVendaAdapterDb;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ItemVendaServiceAdapterImpl implements ItemVendaAdapterDb {

    private final ItemVendaRepository itemVendaRepository;

    @Override
    public List<ItemVenda> saveAll(List<ItemVenda> items) {
        List<ItemVendaEntityBd> itemVendaEntityBds = items.stream()
                .map(this::from)
                .collect(Collectors.toList());

        List<ItemVendaEntityBd> saveEntities = itemVendaRepository.saveAll(itemVendaEntityBds);
        return saveEntities.stream()
                .map(this::toItem)
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemVenda> findByVendaId(String vendaId) {
        List<ItemVendaEntityBd> byVendaId = itemVendaRepository.findAllByVendaId(vendaId);
        return byVendaId.stream()
                .map(this::toItem)
                .collect(java.util.stream.Collectors.toList());
    }

    private ItemVendaEntityBd from(ItemVenda item) {
        return ItemVendaEntityBd.builder()
                .id(item.getId())
                .idProduto(item.getIdProduto())
                .valorProduto(item.getValorProduto())
                .venda(VendaEntityDb.builder()
                        .id(item.getVenda().getId())
                        .build())
                .quantidade(item.getQuantidade())
                .descontoProduto(item.getDescontoProduto())
                .dataCreated(item.getDataCreated())
                .build();
    }

    private ItemVenda toItem(ItemVendaEntityBd itemVendaEntityBd){
        return ItemVenda.builder()
                .id(itemVendaEntityBd.getId())
                .idProduto(itemVendaEntityBd.getIdProduto())
                .valorProduto(itemVendaEntityBd.getValorProduto())
                .venda(Venda.builder()
                        .id(itemVendaEntityBd.getVenda().getId())
                        .build())
                .quantidade(itemVendaEntityBd.getQuantidade())
                .descontoProduto(itemVendaEntityBd.getDescontoProduto())
                .dataCreated(itemVendaEntityBd.getDataCreated())
                .build();
    }


}
