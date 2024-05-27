package com.br.sgme.adapters.in.controller.usuario;

import com.br.sgme.adapters.in.controller.usuario.dto.LoginResponseDto;
import com.br.sgme.adapters.in.controller.usuario.dto.AuthenticationDto;
import com.br.sgme.adapters.in.controller.usuario.dto.RegisterDto;
import com.br.sgme.port.in.UsuarioUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.br.sgme.utils.UrlCrossOrigin.URL_CROSS_ORIGIN;

@CrossOrigin(URL_CROSS_ORIGIN)
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UsuarioUseCase usuarioUseCase;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Validated AuthenticationDto data) {
        LoginResponseDto login = usuarioUseCase.login(data);
        return ResponseEntity.ok(login);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registrer(@RequestBody @Validated RegisterDto data) {
        usuarioUseCase.save(data);
        return  ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
