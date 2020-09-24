package com.dro.feedfood.resource;

import com.dro.feedfood.model.Video;
import com.dro.feedfood.repository.PessoaRepository;
import com.dro.feedfood.repository.VideoRepository;
import com.dro.feedfood.service.VideoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

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
    public List<Video> listar(){
        List<Video> videos = new ArrayList<>();
         videoRepository.listarDecrescente().forEach(x->{
            x.setListaDePessoasQueGostaram(pessoaRepository.listaPessoaGostaramDesteVideo(x.getId()));
            videos.add(x);
         });
         return videos;
    }

    @PostMapping
    public ResponseEntity<Video> salvar(@Valid @RequestBody Video video, HttpServletResponse httpServletResponse) {
        return videoService.salvar(video,httpServletResponse);
    }
}
