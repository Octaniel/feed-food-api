package com.dro.feedfood.repository.pessoa;

import com.dro.feedfood.model.Pessoa;

import java.util.List;

/**
 * User: Octaniel
 * Date: 24/09/2020
 * Time: 06:04
 */
public interface PessoaRepositoryQuery {

    List<Pessoa> listaPessoaGostaramDesteVideo(Long idVideo);
}
