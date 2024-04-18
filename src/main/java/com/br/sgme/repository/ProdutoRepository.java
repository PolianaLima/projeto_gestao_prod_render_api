package com.br.sgme.repository;

import com.br.sgme.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, String> {
    @Query("SELECT c FROM produtos c WHERE c.usuario.id = :usuarioId")
    List<Produto> findByUsuarioId(@Param("usuarioId") String usuarioId);

    @Query("SELECT c FROM produtos c WHERE c.codigo = :codigo")
    Produto findByCodigo(@Param("codigo") String codigo);

}
