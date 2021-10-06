package com.devonfw.quarkus.ordermanagement.domain.repo;

import com.devonfw.quarkus.ordermanagement.domain.model.ItemEntity;

import java.util.List;

public interface ItemFragment {
    void deleteByOrderId(Long id);

    List<ItemEntity> findItemsByOrderId(Long id);
}
