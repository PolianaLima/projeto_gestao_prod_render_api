package com.br.sgme.adapters.out.bd.repository;

import com.br.sgme.adapters.out.bd.model.ClienteEntityBd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<ClienteEntityBd, String> {


    @Query("SELECT c FROM clientes c WHERE c.usuario.id = :usuarioId")
    List<ClienteEntityBd> findByUsuarioId(@Param("usuarioId") String usuarioId);

    @Query("SELECT c FROM clientes c WHERE c.documento = :documento AND c.usuario.id = :usuarioId")
    Optional<ClienteEntityBd> findByDocumentoAndUsuarioId(@Param("documento") String documento, @Param("usuarioId") String usuarioId);


    @Query("SELECT c FROM clientes c WHERE c.id = :id AND c.usuario.id = :usuarioId")
    Optional<ClienteEntityBd> findByIdAndUsuarioId(@Param("id") String id, @Param("usuarioId") String usuarioId);

    @Query("SELECT COUNT(c) > 0 FROM clientes c WHERE c.documento = :documento AND c.usuario.id = :usuarioId")
    boolean existByDOcumentoAndUsuarioId(@Param("documento") String documento, @Param("usuarioId") String usuarioId);
}
