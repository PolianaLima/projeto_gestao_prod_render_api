package com.br.sgme.adapters.out.bd.repository;

import com.br.sgme.adapters.out.bd.model.VendaEntityDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VendaRepository extends JpaRepository<VendaEntityDb, String> {
    @Query("SELECT v FROM vendas v WHERE v.usuario.id = :usuarioId AND v.status  = 'FINALIZADA'")
    List<VendaEntityDb> findAllByUsuarioId(@Param("usuarioId") String usuarioId);

    @Query("SELECT v FROM vendas v WHERE v.checkout.id = :idCheckout and v.usuario.id = :usuarioId")
    List<VendaEntityDb> findAllByCheckoutId(@Param("idCheckout") String pdvId, @Param("usuarioId") String usuarioId);

    @Query("SELECT v FROM vendas v WHERE v.id = :id AND v.usuario.id = :usuarioId")
    Optional<VendaEntityDb> findByIdAndUsuarioId(@Param("id") String id, @Param("usuarioId") String usuarioId);
}
