package com.br.sgme.usecase.service;

import com.br.sgme.adapters.in.controller.usuario.dto.AuthenticationDto;
import com.br.sgme.adapters.in.controller.usuario.dto.LoginResponseDto;
import com.br.sgme.adapters.in.controller.usuario.dto.RegisterDto;
import com.br.sgme.adapters.in.controller.usuario.dto.UsuarioReponseDto;
import com.br.sgme.adapters.out.bd.model.Usuario;
import com.br.sgme.adapters.out.bd.repository.UsuarioRepository;
import com.br.sgme.config.exceptions.LoginInvalidoException;
import com.br.sgme.config.exceptions.RecursoNaoEncontradoException;
import com.br.sgme.config.security.TokenService;
import com.br.sgme.port.in.UsuarioUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioUseCaseImpl implements UsuarioUseCase {

    private final AuthenticationManager authenticationManager;

    private final UsuarioRepository usuarioRepository;

    private final TokenService tokenService;

    @Override
    public void save(RegisterDto registerDto) {

        verificarUsuarioEmUso(registerDto);

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDto.senha());

        Usuario newUsuario = new Usuario(registerDto.nome(), registerDto.login(), encryptedPassword, registerDto.role());

        this.usuarioRepository.save(newUsuario);
    }

    @Override
    public LoginResponseDto login(AuthenticationDto authenticationDTO) {

        var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationDTO.login(), authenticationDTO.senha());

        try {

            var auth = this.authenticationManager.authenticate(usernamePassword);
            var usuario = ((Usuario) auth.getPrincipal());
            var token = tokenService.generateToken(usuario);

            return new LoginResponseDto(token, usuario);

        } catch (BadCredentialsException e) {
            throw new LoginInvalidoException(e.getMessage());
        }

    }

    @Override
    public UsuarioReponseDto usuarioByToken(String token) {
        String tokenValido = token.substring(7);
        String login = tokenService.getLogin(tokenValido);
        UserDetails userDetails = usuarioRepository.findByLogin(login)
               .orElseThrow(()-> new RecursoNaoEncontradoException("Usuário não encontrado"));
       Usuario usuario = (Usuario) userDetails;
       return UsuarioReponseDto.to(usuario);
    }

    @Override
    public void validateToken(String token) {
        String tokenValido = token.substring(7);
        tokenService.validateToken(tokenValido);
    }

    private void verificarUsuarioEmUso(RegisterDto data) {

        if (this.usuarioRepository.findByLogin(data.login()).isPresent()) {
            throw new LoginInvalidoException("Login em uso");
        }
    }

}
