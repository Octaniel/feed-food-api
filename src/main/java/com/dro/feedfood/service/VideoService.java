package com.dro.feedfood.service;

import com.dro.feedfood.event.RecursoCriadoEvent;
import com.dro.feedfood.model.Item;
import com.dro.feedfood.model.Video;
import com.dro.feedfood.repository.ItemRepository;
import com.dro.feedfood.repository.VideoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
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
    private final ItemRepository itemRepository;

    public VideoService(ApplicationEventPublisher publisher, VideoRepository videoRepository, ItemRepository itemRepository) {
        this.publisher = publisher;
        this.videoRepository = videoRepository;
        this.itemRepository = itemRepository;
    }

    public ResponseEntity<Video> salvar(Video video, HttpServletResponse httpServletResponse) {
        LocalDateTime localDateTime = LocalDateTime.now();
        video.setDataAlteracao(localDateTime);
        video.setDataCriacao(localDateTime);
        List<Item> itens = video.getItens();
        Video save = videoRepository.save(video);
        itens.forEach(x->{
            x.setVideo(save);
            itemRepository.save(x);
        });
        publisher.publishEvent(new RecursoCriadoEvent(this, httpServletResponse, save.getId()));
        save.getItens().forEach(x-> x.setVideo(null));
        return ResponseEntity.status(HttpStatus.CREATED).body(save);
    }

    public Video atualizar(Video video){
        Video video1 = videoRepository.findById(video.getId()).get();
        BeanUtils.copyProperties(video,video1, "dataCriacao", "url", "pessoa");
        video1.setDataAlteracao(LocalDateTime.now());
        return videoRepository.save(video1);
    }
}
