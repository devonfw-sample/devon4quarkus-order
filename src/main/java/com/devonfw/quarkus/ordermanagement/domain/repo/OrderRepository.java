package com.devonfw.quarkus.ordermanagement.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devonfw.quarkus.ordermanagement.domain.model.OrderEntity;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long>, OrderFragment {

}