package com.br.sgme.adapters.out.bd.repository;

import com.br.sgme.adapters.out.bd.model.ItemVendaEntityBd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemVendaRepository extends JpaRepository<ItemVendaEntityBd, String> {

        @Query("SELECT i FROM items i WHERE i.venda.id = :vendaId")
        List<ItemVendaEntityBd> findAllByVendaId(String vendaId);
}
