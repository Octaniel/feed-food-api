package com.dro.feedfood.repository.pessoa;

import com.dro.feedfood.model.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Octaniel
 * Date: 24/09/2020
 * Time: 06:05
 */
public class PessoaRepositoryImpl implements PessoaRepositoryQuery{
    @PersistenceContext
    private EntityManager Manager;

    @Override
    public List<Pessoa> listaPessoaGostaramDesteVideo(Long idVideo) {
        CriteriaBuilder builder = Manager.getCriteriaBuilder();
        CriteriaQuery<Pessoa> query = builder.createQuery(Pessoa.class);
        Root<Pessoa> rootpes = query.from(Pessoa.class);
        Root<Gosto> rootgos = query.from(Gosto.class);

        query.select(rootpes);

        Predicate[] predicates = criarPredicates(idVideo, builder, rootpes, rootgos);
        query.where(predicates);

        TypedQuery<Pessoa> typedQuery = Manager.createQuery(query);
        List<Pessoa> resultList = typedQuery.getResultList();
        System.out.println("");
        return typedQuery.getResultList();
    }

    private Predicate[] criarPredicates(Long idVideo, CriteriaBuilder builder, Root<Pessoa> rootpes, Root<Gosto> rootgos) {
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(rootgos.get(Gosto_.VIDEO).get(Video_.ID),idVideo));
        predicates.add(builder.equal(rootpes,rootgos.get(Gosto_.PESSOA)));
        return predicates.toArray(new Predicate[0]);
    }
}
