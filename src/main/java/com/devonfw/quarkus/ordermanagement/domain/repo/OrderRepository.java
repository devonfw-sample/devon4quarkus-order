package com.devonfw.quarkus.ordermanagement.domain.repo;

import com.devonfw.quarkus.ordermanagement.domain.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    @Query("SELECT a FROM OrderEntity a WHERE a.price < :priceMax AND a.price > :priceMin")
    List<OrderEntity> findOrdersByCriteria(@Param("priceMax") BigDecimal priceMax, @Param("priceMin") BigDecimal priceMin);
}