package com.br.sgme.adapters.out.bd.repository;

import com.br.sgme.adapters.out.bd.model.DespesaEntityBd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface DespesaRepository extends JpaRepository<DespesaEntityBd, String> {
    @Query("SELECT c FROM despesas c WHERE c.usuario.id = :usuarioId ORDER BY c.dataVencimento ASC")
    List<DespesaEntityBd> findByUsuarioId(@Param("usuarioId") String usuarioId);

    @Query("SELECT c FROM despesas c WHERE c.id = :idDespesa AND c.usuario.id = :usuarioId")
    Optional<DespesaEntityBd> findByIdAndUsuarioId(@Param("idDespesa") String idDespesa, @Param("usuarioId") String usuarioId);

    @Transactional
    @Modifying
    @Query("DELETE FROM despesas c WHERE c.id = :id AND c.usuario.id = :usuarioId")
    void deleteByIdAndUsuarioId(@Param("id") String id, @Param("usuarioId") String usuarioId);
}
