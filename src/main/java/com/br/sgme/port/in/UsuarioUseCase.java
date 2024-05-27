package com.br.sgme.port.in;

import com.br.sgme.adapters.in.controller.usuario.dto.AuthenticationDto;
import com.br.sgme.adapters.in.controller.usuario.dto.LoginResponseDto;
import com.br.sgme.adapters.in.controller.usuario.dto.RegisterDto;

public interface UsuarioUseCase {

   void save (RegisterDto registerDto);

    LoginResponseDto login(AuthenticationDto authenticationDTO);

}
