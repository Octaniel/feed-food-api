package com.dro.feedfood.repository.video;

import com.dro.feedfood.model.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VideoRepositoryQuery {

    Page<Video> listar(Pageable pageable, String nome);
}
