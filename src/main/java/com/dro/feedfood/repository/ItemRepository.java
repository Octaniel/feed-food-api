package com.dro.feedfood.repository;

import com.dro.feedfood.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByVideoId(Long idVideo);
}
