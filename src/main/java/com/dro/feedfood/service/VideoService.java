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
    private final ItemRepository itemRepository;
    private final GostoRepository gostoRepository;
    private final ComentarioRepository comentarioRepository;

    public VideoService(ApplicationEventPublisher publisher, VideoRepository videoRepository,
                        ItemRepository itemRepository, GostoRepository gostoRepository,
                        ComentarioRepository comentarioRepository) {
        this.publisher = publisher;
        this.videoRepository = videoRepository;
        this.itemRepository = itemRepository;
        this.gostoRepository = gostoRepository;
        this.comentarioRepository = comentarioRepository;
    }

    public ResponseEntity<Video> salvar(Video video, HttpServletResponse httpServletResponse) {
        LocalDateTime localDateTime = LocalDateTime.now();
        video.setDataAlteracao(localDateTime);
        video.setDataCriacao(localDateTime);
        List<Item> itens = video.getItens()==null?new ArrayList<>():video.getItens();
        Video save = videoRepository.save(video);
        itens.forEach(x->{
            x.setVideo(save);
            itemRepository.save(x);
        });
        publisher.publishEvent(new RecursoCriadoEvent(this, httpServletResponse, save.getId()));
        if(save.getItens()!=null)save.getItens().forEach(x-> x.setVideo(null));
        return ResponseEntity.status(HttpStatus.CREATED).body(save);
    }

    public Video atualizar(Video video){
        Video video1 = videoRepository.findById(video.getId()).get();
        BeanUtils.copyProperties(video,video1, "dataCriacao", "url", "pessoa");
        video1.setDataAlteracao(LocalDateTime.now());
        List<Item> itens = video.getItens();
        itemRepository.findAllByVideoId(video1.getId()).forEach(x->{
            if(!itens.contains(x)){
                itemRepository.delete(x);
            }
        });
        itemRepository.saveAll(itens);
        return videoRepository.save(video1);
    }

    public void remover(Long id){
        comentarioRepository.deleteAll(comentarioRepository.findAllByVideoId(id));
        gostoRepository.deleteAll(gostoRepository.findAllByIdGosto_Video_Id(id));
        itemRepository.deleteAll(itemRepository.findAllByVideoId(id));
        videoRepository.deleteById(id);
    }
}
