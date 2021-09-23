package com.devonfw.quarkus.ordermanagement.domain.repo;

import com.devonfw.quarkus.ordermanagement.domain.model.OrderEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<OrderEntity, Long> {
    @Override
    List<OrderEntity> findAll();
}