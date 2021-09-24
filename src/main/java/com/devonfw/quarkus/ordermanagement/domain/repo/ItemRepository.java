package com.devonfw.quarkus.ordermanagement.domain.repo;

import com.devonfw.quarkus.ordermanagement.domain.model.ItemEntity;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<ItemEntity, Long>, ItemFragment {
}