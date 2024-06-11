package com.br.sgme.adapters.out.bd.repository;

import com.br.sgme.adapters.out.bd.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    Optional<UserDetails> findByLogin(String login);
    @Query("SELECT u FROM Usuario u WHERE u.login = :username")
    Optional<Usuario> findByUsername(String username);

}
