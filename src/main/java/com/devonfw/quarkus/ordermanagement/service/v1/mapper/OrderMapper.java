package com.devonfw.quarkus.ordermanagement.service.v1.mapper;

import com.devonfw.quarkus.ordermanagement.domain.model.OrderEntity;
import com.devonfw.quarkus.ordermanagement.service.v1.model.OrderDto;
import org.mapstruct.Mapper;
import org.tkit.quarkus.rs.mappers.OffsetDateTimeMapper;

import java.util.List;

@Mapper(uses = OffsetDateTimeMapper.class)
public interface OrderMapper {
    OrderDto map(OrderEntity model);

    List<OrderDto> map(List<OrderEntity> orders);
}
