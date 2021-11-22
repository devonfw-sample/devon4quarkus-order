package com.devonfw.quarkus.ordermanagement.domain.repo;

import com.devonfw.quarkus.ordermanagement.domain.model.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
    List<ItemEntity> findAllByProductorderId(Long id);
    void deleteAllByProductorderId(Long id);
}