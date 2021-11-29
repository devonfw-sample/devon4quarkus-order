package com.devonfw.quarkus.ordermanagement.rest.v1.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.devonfw.quarkus.ordermanagement.domain.model.OrderEntity;
import com.devonfw.quarkus.ordermanagement.rest.v1.model.OrderDto;

@Mapper
public interface OrderMapper {
  OrderDto map(OrderEntity model);

  List<OrderDto> map(List<OrderEntity> orders);
}
