package com.dro.feedfood.repository;

import com.dro.feedfood.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * User: Octaniel
 * Date: 22/09/2020
 * Time: 18:06
 */
public interface VideoRepository extends JpaRepository<Video, Long> {
    @Query(value = "SELECT * " +
            "FROM video video ORDER By id desc", nativeQuery = true)
    List<Video> listarDecrescente();
}
