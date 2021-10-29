package com.devonfw.quarkus.ordermanagement.domain.repo;

import com.devonfw.quarkus.ordermanagement.domain.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}