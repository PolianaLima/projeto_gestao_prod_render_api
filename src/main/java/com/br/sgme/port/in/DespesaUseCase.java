package com.br.sgme.port.in;

import com.br.sgme.adapters.in.controller.despesa.dto.DespesaDto;
import com.br.sgme.domain.Despesa;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DespesaUseCase {

    void save(Despesa despesa, String token);

    void update(Despesa despesa, String token);

    List<Despesa> get(String idUsuario);

    Despesa getById(String id, String token);

    void delete(String id, String token);
}
