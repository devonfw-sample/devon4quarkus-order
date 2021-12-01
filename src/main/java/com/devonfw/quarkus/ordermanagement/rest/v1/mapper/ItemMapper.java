package com.devonfw.quarkus.ordermanagement.rest.v1.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.devonfw.quarkus.ordermanagement.domain.model.ItemEntity;
import com.devonfw.quarkus.ordermanagement.rest.v1.model.ItemDto;

@Mapper
public interface ItemMapper {
  List<ItemDto> map(List<ItemEntity> items);
}
