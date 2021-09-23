package com.devonfw.quarkus.ordermanagement.logic;

import com.devonfw.quarkus.ordermanagement.service.v1.model.OrderDto;
import org.springframework.data.domain.Page;

public interface UcFindOrder {
    OrderDto findOrder(String id);

    Page<OrderDto> findOrder();
}
