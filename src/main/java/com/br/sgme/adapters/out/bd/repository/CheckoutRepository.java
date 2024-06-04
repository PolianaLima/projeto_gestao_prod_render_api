package com.br.sgme.adapters.out.bd.repository;

import com.br.sgme.adapters.out.bd.model.CheckoutEntityBd;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CheckoutRepository extends JpaRepository<CheckoutEntityBd, String> {

    @Query("SELECT c FROM checkouts c WHERE c.usuario.id = :idUsuario")
    List<CheckoutEntityBd> findAllByUsuarioId(String idUsuario);

    @Query("SELECT c FROM checkouts c WHERE c.usuario.id = :idUsuario and c.status = 'ABERTO'")
    List<CheckoutEntityBd> findAllByUsuarioIdOpen(String idUsuario);

   /* @Query("SELECT c FROM checkouts c WHERE c.data_created = :dataCreated and c.usuario.id = :idUsuario")
    CheckoutEntityBd findByDataCreated(String dataCreated);*/

    @Query("SELECT c FROM checkouts c WHERE c.id = :id and c.usuario.id = :idUsuario")
    Optional<CheckoutEntityBd> findByIdAndUsuarioId(String id, String idUsuario);

    @Query("SELECT c FROM checkouts c WHERE c.usuario.id = :idUsuario and c.status = 'ABERTO' ORDER BY c.dataCreated DESC LIMIT 1")
    Optional <CheckoutEntityBd> findLastCheckout(String idUsuario);


    @Query("SELECT COUNT(c) > 0 FROM checkouts c WHERE c.status = 'ABERTO' AND c.usuario.id = :usuarioId")
    boolean existsByCheckoutOpen(@Param("usuarioId") String usuarioId);

}
