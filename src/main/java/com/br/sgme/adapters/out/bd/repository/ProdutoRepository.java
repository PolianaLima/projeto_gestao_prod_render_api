package com.br.sgme.adapters.out.bd.repository;

import com.br.sgme.adapters.out.bd.model.ProdutoEntityBd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<ProdutoEntityBd, String> {
    @Query("SELECT c FROM produtos c WHERE c.usuario.id = :usuarioId")
    List<ProdutoEntityBd> findByUsuarioId(@Param("usuarioId") String usuarioId);

    @Query("SELECT c FROM produtos c WHERE c.codigo = :codigo AND c.usuario.id = :usuarioId")
   Optional <ProdutoEntityBd> findByCodigo(@Param("codigo") String codigo, @Param("usuarioId") String usuarioId);

    @Query("SELECT c FROM produtos c WHERE c.id = :id AND c.usuario.id = :usuarioId")
    Optional<ProdutoEntityBd> findByIdAndUsuarioId(@Param("id") String id, @Param("usuarioId") String usuarioId);

    @Query("SELECT COUNT(c) > 0 FROM produtos c WHERE c.codigo = :codigo AND c.usuario.id = :usuarioId")
    boolean existByCodigoAndUsuarioId(@Param("codigo") String codigo, @Param("usuarioId") String usuarioId);

}
