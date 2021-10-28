package com.devonfw.quarkus.ordermanagement.service.v1.mapper;

import com.devonfw.quarkus.ordermanagement.domain.model.OrderEntity;
import com.devonfw.quarkus.ordermanagement.service.v1.model.OrderDto;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper
public interface OrderMapper {
    OrderDto map(OrderEntity model);

    List<OrderDto> map(List<OrderEntity> orders);
}
