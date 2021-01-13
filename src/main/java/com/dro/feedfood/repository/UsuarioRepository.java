package com.dro.feedfood.repository;

import com.dro.feedfood.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByPessoaEmail(String email);

    Long countAllByDataCriacaoAfter(LocalDateTime data);

}
