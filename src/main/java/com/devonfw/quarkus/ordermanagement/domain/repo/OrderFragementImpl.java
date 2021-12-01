package com.devonfw.quarkus.ordermanagement.domain.repo;

import static java.util.Objects.isNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.devonfw.quarkus.general.repo.QueryUtil;
import com.devonfw.quarkus.ordermanagement.domain.model.OrderEntity;
import com.devonfw.quarkus.ordermanagement.domain.model.QOrderEntity;
import com.devonfw.quarkus.ordermanagement.rest.v1.model.OrderSearchCriteriaDto;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;

public class OrderFragementImpl implements OrderFragment {

  @Inject
  EntityManager em;

  @Override
  public Page<OrderEntity> findByCriteria(OrderSearchCriteriaDto searchCriteria) {

    QOrderEntity order = QOrderEntity.orderEntity;
    List<Predicate> predicates = new ArrayList<>();

    if (!isNull(searchCriteria.getPriceMin())) {
      predicates.add(order.price.gt(searchCriteria.getPriceMin()));
    }
    if (!isNull(searchCriteria.getPriceMax())) {
      predicates.add(order.price.lt(searchCriteria.getPriceMax()));
    }

    JPAQuery<OrderEntity> query = new JPAQuery<OrderEntity>(this.em).from(order);
    if (!predicates.isEmpty()) {
      query.where(predicates.toArray(Predicate[]::new));
    }

    if (searchCriteria.getPageable() == null) {
      searchCriteria.setPageable(PageRequest.of(0, 10));
    }

    return QueryUtil.findPaginated(searchCriteria.getPageable(), query, searchCriteria.isDetermineTotal());
  }

}
