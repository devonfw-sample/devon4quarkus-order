package com.devonfw.quarkus.ordermanagement.rest.v1.mapper;

import com.devonfw.quarkus.ordermanagement.domain.model.ItemEntity;
import com.devonfw.quarkus.ordermanagement.service.v1.model.ItemDto;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper
public interface ItemMapper {
    List<ItemDto> map(List<ItemEntity> items);
}
