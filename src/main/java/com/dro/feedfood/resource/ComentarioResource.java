package com.dro.feedfood.resource;

import com.dro.feedfood.model.Comentario;
import com.dro.feedfood.model.Video;
import com.dro.feedfood.repository.ComentarioRepository;
import com.dro.feedfood.service.ComentarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * User: Octaniel
 * Date: 22/09/2020
 * Time: 18:07
 */
@RestController
@RequestMapping("comentario")
public class ComentarioResource {

    private final ComentarioRepository comentarioRepository;
    private final ComentarioService comentarioService;

    public ComentarioResource(ComentarioRepository comentarioRepository, ComentarioService comentarioService) {
        this.comentarioRepository = comentarioRepository;
        this.comentarioService = comentarioService;
    }

    @PostMapping
    public ResponseEntity<Comentario> salvar(@RequestBody Comentario comentario, HttpServletResponse httpServletResponse){
        return comentarioService.salvar(comentario, httpServletResponse);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        comentarioRepository.deleteById(id);
    }

    @GetMapping
    public List<Comentario> listarPorVideo(Long idVideo){
        return comentarioRepository.findAllByVideoId(idVideo);
    }
}
