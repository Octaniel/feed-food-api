package com.dro.feedfood.service;

import com.dro.feedfood.event.RecursoCriadoEvent;
import com.dro.feedfood.model.Item;
import com.dro.feedfood.model.Video;
import com.dro.feedfood.repository.ComentarioRepository;
import com.dro.feedfood.repository.GostoRepository;
import com.dro.feedfood.repository.ItemRepository;
import com.dro.feedfood.repository.VideoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Octaniel
 * Date: 22/09/2020
 * Time: 18:16
 */
@Service
public class VideoService {

    private final ApplicationEventPublisher publisher;

    private final VideoRepository videoRepository;

    public VideoService(ApplicationEventPublisher publisher, VideoRepository videoRepository) {
        this.publisher = publisher;
        this.videoRepository = videoRepository;
    }

    public ResponseEntity<Video> salvar(Video video, HttpServletResponse httpServletResponse) {
        Video save = videoRepository.save(video);
        publisher.publishEvent(new RecursoCriadoEvent(this, httpServletResponse, save.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(save);
    }

    public void remover(Long id){
        videoRepository.deleteById(id);
    }
}
