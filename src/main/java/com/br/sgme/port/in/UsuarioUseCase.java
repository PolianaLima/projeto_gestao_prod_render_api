package com.br.sgme.port.in;

import com.br.sgme.adapters.in.controller.usuario.dto.AuthenticationDto;
import com.br.sgme.adapters.in.controller.usuario.dto.LoginResponseDto;
import com.br.sgme.adapters.in.controller.usuario.dto.RegisterDto;
import com.br.sgme.adapters.in.controller.usuario.dto.UsuarioReponseDto;
import com.br.sgme.adapters.out.bd.model.Usuario;

public interface UsuarioUseCase {

   void save (RegisterDto registerDto);

    LoginResponseDto login(AuthenticationDto authenticationDTO);

    UsuarioReponseDto usuarioByToken(String token);

    void validateToken(String token);

}
