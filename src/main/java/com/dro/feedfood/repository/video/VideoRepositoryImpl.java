package com.dro.feedfood.repository.video;

import com.dro.feedfood.model.Gosto;
import com.dro.feedfood.model.Video;
import com.dro.feedfood.security.UsuarioSistema;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        int size = query.getResultList().size();
        adicionarRestricoesDePaginacao(query, pageable);
        return new PageImpl<>(query.getResultList(), pageable, size);
    }

    @Override
    public Page<Video> listarQueGostei(Pageable pageable, String nome) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Video> criteria = builder.createQuery(Video.class);
        Root<Video> root = criteria.from(Video.class);
        Root<Gosto> rootg = criteria.from(Gosto.class);

        criteria.select(root);
        List<Order> orders = new ArrayList<>();
        Random random = new Random();
        int i = random.nextInt(12);
        switch (i){
            case 0: orders.add(builder.asc(root.get("id"))); break;
            case 1: orders.add(builder.desc(root.get("id"))); break;
            case 2: orders.add(builder.asc(root.get("url"))); break;
            case 3: orders.add(builder.desc(root.get("url"))); break;
            case 4: orders.add(builder.asc(root.get("nome"))); break;
            case 5: orders.add(builder.desc(root.get("nome"))); break;
            case 6: orders.add(builder.asc(root.get("descricao"))); break;
            case 7: orders.add(builder.desc(root.get("descricao"))); break;
            case 8: orders.add(builder.asc(root.get("igredientes"))); break;
            case 9: orders.add(builder.desc(root.get("igredientes"))); break;
            case 10: orders.add(builder.asc(root.get("preparo"))); break;
            case 11: orders.add(builder.desc(root.get("preparo"))); break;
        }
        criteria.orderBy(orders);
        Predicate[] predicates = criarRestricoesQueGostei(nome, builder, root, rootg);
        criteria.where(predicates);

        List<Order> orderList = new ArrayList<>();
        orderList.add(builder.desc(root.get("id")));
        criteria.orderBy(orderList);

        TypedQuery<Video> query = manager.createQuery(criteria);
        int size = query.getResultList().size();
        adicionarRestricoesDePaginacao(query, pageable);
        return new PageImpl<>(query.getResultList(), pageable, size);
    }

    private Predicate[] criarRestricoes(String nome, CriteriaBuilder builder, Root<Video> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.isNotEmpty(nome)){
            predicates.add(builder.like(builder.lower(root.get("nome")),"%"+nome.toLowerCase()+"%"));
        }

        return predicates.toArray(new Predicate[0]);
    }

    private Predicate[] criarRestricoesQueGostei(String nome, CriteriaBuilder builder, Root<Video> root, Root<Gosto> rootg) {
        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.isNotEmpty(nome)){
            predicates.add(builder.like(builder.lower(root.get("nome")),"%"+nome.toLowerCase()+"%"));
        }

        predicates.add(builder.equal(rootg.get("idGosto").get("pessoa").get("email"), UsuarioSistema.email()));
        predicates.add(builder.equal(root,rootg.get("idGosto").get("video")));

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
