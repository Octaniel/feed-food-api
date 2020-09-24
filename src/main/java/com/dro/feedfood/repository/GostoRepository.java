package com.dro.feedfood.repository;

import com.dro.feedfood.model.Gosto;
import com.dro.feedfood.model.IdGosto;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User: Octaniel
 * Date: 24/09/2020
 * Time: 09:07
 */
public interface GostoRepository extends JpaRepository<Gosto, IdGosto> {
}
