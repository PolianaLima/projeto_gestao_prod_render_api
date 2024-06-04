//package com.br.sgme.service;
//import com.br.sgme.controller.usuario.dto.AuthenticationDto;
//import com.br.sgme.controller.usuario.dto.RegisterDto;
//import com.br.sgme.exceptions.LoginInvalidoException;
//import com.br.sgme.model.Usuario;
//import com.br.sgme.repository.UsuarioRepository;
//import com.br.sgme.service.UsuarioUseCaseImpl;
//import com.br.sgme.infra.security.TokenService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//public class UsuarioUseCaseImplTest {
//
//    @InjectMocks
//    private UsuarioUseCaseImpl usuarioUseCase;
//
//    @Mock
//    private AuthenticationManager authenticationManager;
//
//    @Mock
//    private UsuarioRepository usuarioRepository;
//
//    @Mock
//    private TokenService tokenService;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testSave() {
//        RegisterDto registerDto = new RegisterDto();
//        registerDto.setLogin("test");
//        registerDto.setSenha("password");
//        registerDto.setNome("Test User");
//        registerDto.setRole("USER");
//
//        when(usuarioRepository.findByLogin(anyString())).thenReturn(Optional.empty());
//        when(usuarioRepository.save(any(Usuario.class))).thenReturn(null);
//
//        assertNotNull(usuarioUseCase.save(registerDto));
//    }
//
//    @Test
//    public void testLogin() {
//        AuthenticationDto authenticationDto = new AuthenticationDto();
//        authenticationDto.setLogin("test");
//        authenticationDto.setSenha("password");
//
//        Usuario usuario = new Usuario();
//        usuario.setLogin("test");
//        usuario.setSenha(new BCryptPasswordEncoder().encode("password"));
//
//        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
//        when(tokenService.generateToken(any(Usuario.class))).thenReturn("token");
//
//        assertDoesNotThrow(() -> usuarioUseCase.login(authenticationDto));
//    }
//
//    @Test
//    public void testLoginInvalidoException() {
//        AuthenticationDto authenticationDto = new AuthenticationDto();
//        authenticationDto.setLogin("test");
//        authenticationDto.setSenha("password");
//
//        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new LoginInvalidoException("Invalid login"));
//
//        assertThrows(LoginInvalidoException.class, () -> usuarioUseCase.login(authenticationDto));
//    }
//}