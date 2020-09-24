package com.dro.feedfood.repository;

import com.dro.feedfood.model.Pessoa;
import com.dro.feedfood.repository.pessoa.PessoaRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User: Octaniel
 * Date: 24/09/2020
 * Time: 06:02
 */
public interface PessoaRepository extends JpaRepository<Pessoa, Long>, PessoaRepositoryQuery {
}
