package com.dro.feedfood.repository.usuario;

import com.dro.feedfood.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsuarioRepositoryQuery {
    Page<Usuario> listar(Pageable pageable, String nome);
}
