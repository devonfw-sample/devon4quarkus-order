package com.devonfw.quarkus.ordermanagement.domain.repo;

import org.springframework.data.domain.Page;

import com.devonfw.quarkus.ordermanagement.domain.model.OrderEntity;
import com.devonfw.quarkus.ordermanagement.service.v1.model.OrderSearchCriteriaDto;

public interface OrderFragment {
  public Page<OrderEntity> findByCriteria(OrderSearchCriteriaDto searchCriteria);
}
