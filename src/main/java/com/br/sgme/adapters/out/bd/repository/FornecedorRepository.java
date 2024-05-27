package com.br.sgme.adapters.out.bd.repository;

import com.br.sgme.adapters.out.bd.model.FornecedorEntityBd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FornecedorRepository extends JpaRepository<FornecedorEntityBd, String> {
    @Query("SELECT c FROM fornecedores c WHERE c.usuario.id = :usuarioId")
    List<FornecedorEntityBd> findByUsuarioId(@Param("usuarioId") String usuarioId);

    @Query("SELECT c FROM fornecedores c WHERE c.documento = :documento AND c.usuario.id = :usuarioId")
    Optional<FornecedorEntityBd> findByDocumentoAndUsuarioId(@Param("documento") String cnpj, @Param("usuarioId") String usuarioId);

    @Query("SELECT c FROM fornecedores c WHERE c.id = :id AND c.usuario.id = :usuarioId")
    Optional<FornecedorEntityBd> findByIdAndUsuarioId(@Param("id") String id, @Param("usuarioId") String usuarioId);

    @Query("SELECT COUNT(c) > 0 FROM fornecedores c WHERE c.documento = :documento AND c.usuario.id = :usuarioId")
    boolean existByDOcumentoAndUsuarioId(@Param("documento") String documento, @Param("usuarioId") String usuarioId);
}
