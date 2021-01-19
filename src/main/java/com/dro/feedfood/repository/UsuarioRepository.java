package com.dro.feedfood.repository;

import com.dro.feedfood.model.Usuario;
import com.dro.feedfood.repository.usuario.UsuarioRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>, UsuarioRepositoryQuery {
    Optional<Usuario> findByPessoaEmail(String email);

    Long countAllByDataCriacaoAfter(LocalDateTime data);

}
