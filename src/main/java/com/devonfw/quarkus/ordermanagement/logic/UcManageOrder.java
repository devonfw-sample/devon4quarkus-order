package com.devonfw.quarkus.ordermanagement.logic;

import com.devonfw.quarkus.ordermanagement.service.v1.model.NewOrderDto;
import com.devonfw.quarkus.ordermanagement.service.v1.model.OrderDto;

public interface UcManageOrder {
    OrderDto saveOrder(NewOrderDto dto);

    OrderDto deleteOrder(String id);
}
