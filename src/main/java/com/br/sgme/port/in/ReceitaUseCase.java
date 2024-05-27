package com.br.sgme.port.in;

import com.br.sgme.adapters.in.controller.receita.dto.ReceitaDto;
import com.br.sgme.domain.Receita;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReceitaUseCase {
    void save(Receita receita, String token);

   void update(Receita receita, String token);

    List<Receita> get(String idUsuario);

    Receita getById(String id, String token);

    void delete(String id, String token);
}
