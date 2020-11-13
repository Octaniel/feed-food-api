package com.dro.feedfood.resource;

import com.dro.feedfood.model.Video;
import com.dro.feedfood.repository.PessoaRepository;
import com.dro.feedfood.repository.VideoRepository;
import com.dro.feedfood.service.VideoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * User: Octaniel
 * Date: 22/09/2020
 * Time: 18:18
 */
@RestController
@RequestMapping("video")
public class VideoResource {
    private final VideoRepository videoRepository;
    private final VideoService videoService;
    private final PessoaRepository pessoaRepository;

    public VideoResource(VideoRepository videoRepository, VideoService videoService, PessoaRepository pessoaRepository) {
        this.videoRepository = videoRepository;
        this.videoService = videoService;
        this.pessoaRepository = pessoaRepository;
    }

    @GetMapping
    public List<Video> listar(Pageable pageable, String nome) {
        Page<Video> listar = videoRepository.listar(pageable, nome);
        List<Video> collect = listar.stream().peek(x -> x.setListaDePessoasQueGostaram(pessoaRepository.listaPessoaGostaramDesteVideo(x.getId()))).collect(Collectors.toList());
        Collections.shuffle(collect);
        return collect;
    }

    @GetMapping("gostei")
    public List<Video> listarQueGostei(Pageable pageable, String nome) {
        Page<Video> listar = videoRepository.listarQueGostei(pageable, nome);
        List<Video> collect = listar.stream().peek(x -> x.setListaDePessoasQueGostaram(pessoaRepository.listaPessoaGostaramDesteVideo(x.getId()))).collect(Collectors.toList());
        System.out.println("jjj");
        return collect;
    }

    @PostMapping
    public ResponseEntity<Video> salvar(@Valid @RequestBody Video video, HttpServletResponse httpServletResponse) {
        return videoService.salvar(video, httpServletResponse);
    }

    @PutMapping
    public Video atualizar(@Valid @RequestBody Video video) {
        return videoService.atualizar(video);
    }
}
