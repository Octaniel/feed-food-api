package com.dro.feedfood.repository.usuario;

import com.dro.feedfood.model.Pessoa_;
import com.dro.feedfood.model.Usuario;
import com.dro.feedfood.model.Usuario_;
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

public class UsuarioRepositoryImpl implements UsuarioRepositoryQuery{
    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<Usuario> listar(Pageable pageable, String nome) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Usuario> criteria = builder.createQuery(Usuario.class);
        Root<Usuario> root = criteria.from(Usuario.class);

        criteria.select(root);

        Predicate[] predicates = criarRestricoes(nome, builder, root);
        criteria.where(predicates);

        List<Order> orderList = new ArrayList<>();
        orderList.add(builder.desc(root.get("id")));
        criteria.orderBy(orderList);

        TypedQuery<Usuario> query = manager.createQuery(criteria);
        int size = query.getResultList().size();
        adicionarRestricoesDePaginacao(query, pageable);
        return new PageImpl<>(query.getResultList(), pageable, size);
    }

    private Predicate[] criarRestricoes(String nome, CriteriaBuilder builder, Root<Usuario> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.isNotEmpty(nome)){
            predicates.add(builder.like(builder.lower(root.get(Usuario_.pessoa).get(Pessoa_.nome)),
                    "%"+nome.toLowerCase()+"%"));
        }

        return predicates.toArray(new Predicate[0]);
    }

    private void adicionarRestricoesDePaginacao(TypedQuery<Usuario> query, Pageable pageable) {
        int paginaAtual = pageable.getPageNumber();
        int totalRegistroPorPagina = pageable.getPageSize();
        int primeiroRegistroDaPagina = paginaAtual * totalRegistroPorPagina;
        query.setFirstResult(primeiroRegistroDaPagina);
        query.setMaxResults(totalRegistroPorPagina);
    }
}
