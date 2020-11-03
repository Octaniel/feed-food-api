package com.dro.feedfood.repository.video;

import com.dro.feedfood.model.Video;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.StringUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class VideoRepositoryImpl implements VideoRepositoryQuery {
    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<Video> listar(Pageable pageable, String nome) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Video> criteria = builder.createQuery(Video.class);
        Root<Video> root = criteria.from(Video.class);

        criteria.select(root);

        Predicate[] predicates = criarRestricoes(nome, builder, root);
        criteria.where(predicates);

        List<Order> orderList = new ArrayList<>();
        orderList.add(builder.desc(root.get("id")));
        criteria.orderBy(orderList);

        TypedQuery<Video> query = manager.createQuery(criteria);
        List<Video> resultList = query.getResultList();
        adicionarRestricoesDePaginacao(query, pageable);
        return new PageImpl<>(resultList, pageable, resultList.size());
    }

    private Predicate[] criarRestricoes(String nome, CriteriaBuilder builder, Root<Video> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.isNotEmpty(nome)){
            predicates.add(builder.like(builder.lower(root.get("nome")),"%"+nome.toLowerCase()+"%"));
        }

        return predicates.toArray(new Predicate[0]);
    }

    private void adicionarRestricoesDePaginacao(TypedQuery<Video> query, Pageable pageable) {
        int paginaAtual = pageable.getPageNumber();
        int totalRegistroPorPagina = pageable.getPageSize();
        int primeiroRegistroDaPagina = paginaAtual * totalRegistroPorPagina;
        query.setFirstResult(primeiroRegistroDaPagina);
        query.setMaxResults(totalRegistroPorPagina);
    }
}
