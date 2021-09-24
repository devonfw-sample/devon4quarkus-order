package com.devonfw.quarkus.ordermanagement.service.v1.mapper;

import com.devonfw.quarkus.ordermanagement.domain.model.ItemEntity;
import com.devonfw.quarkus.ordermanagement.service.v1.model.ItemDto;
import org.mapstruct.Mapper;
import org.tkit.quarkus.rs.mappers.OffsetDateTimeMapper;

import java.util.List;

@Mapper(uses = OffsetDateTimeMapper.class)
public interface ItemMapper {
    List<ItemDto> map(List<ItemEntity> items);
}
