package com.dro.feedfood.repository;

import com.dro.feedfood.model.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * User: Octaniel
 * Date: 22/09/2020
 * Time: 18:05
 */
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

    List<Comentario> findAllByVideoId(Long idVideo);
}
