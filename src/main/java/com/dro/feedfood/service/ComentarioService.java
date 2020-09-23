package com.dro.feedfood.service;

import com.dro.feedfood.event.RecursoCriadoEvent;
import com.dro.feedfood.model.Comentario;
import com.dro.feedfood.repository.ComentarioRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

/**
 * User: Octaniel
 * Date: 22/09/2020
 * Time: 18:10
 */
@Service
public class ComentarioService {

    private final ApplicationEventPublisher publisher;

    private final ComentarioRepository comentarioRepository;

    public ComentarioService(ApplicationEventPublisher publisher, ComentarioRepository comentarioRepository) {
        this.publisher = publisher;
        this.comentarioRepository = comentarioRepository;
    }

    public ResponseEntity<Comentario> salvar(Comentario comentario, HttpServletResponse httpServletResponse) {
        LocalDateTime localDateTime = LocalDateTime.now();
        comentario.setDataAlteracao(localDateTime);
        comentario.setDataCriacao(localDateTime);
        Comentario save = comentarioRepository.save(comentario);
        publisher.publishEvent(new RecursoCriadoEvent(this, httpServletResponse, save.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(save);
    }
}
