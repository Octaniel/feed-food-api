package com.dro.feedfood.repository;

import com.dro.feedfood.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
