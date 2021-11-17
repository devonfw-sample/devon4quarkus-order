package com.devonfw.quarkus.ordermanagement.logic;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.devonfw.quarkus.ordermanagement.domain.model.ItemEntity;
import com.devonfw.quarkus.ordermanagement.domain.model.OrderEntity;
import com.devonfw.quarkus.ordermanagement.domain.repo.ItemRepository;
import com.devonfw.quarkus.ordermanagement.domain.repo.OrderRepository;
import com.devonfw.quarkus.ordermanagement.service.v1.mapper.ItemMapper;
import com.devonfw.quarkus.ordermanagement.service.v1.mapper.OrderMapper;
import com.devonfw.quarkus.ordermanagement.service.v1.model.ItemDto;
import com.devonfw.quarkus.ordermanagement.service.v1.model.OrderDto;
import com.devonfw.quarkus.ordermanagement.service.v1.model.OrderSearchCriteriaDto;

@Named
@Transactional
@ApplicationScoped
public class UcFindOrder {

  @Inject
  OrderRepository orderRepository;

  @Inject
  ItemRepository itemRepository;

  @Inject
  OrderMapper orderMapper;

  @Inject
  ItemMapper itemMapper;

  public OrderDto findOrder(Long id) {

    Optional<OrderEntity> orderEntityOptional = this.orderRepository.findById(id);
    if (orderEntityOptional.isPresent()) {
      return this.orderMapper.map(orderEntityOptional.get());
    }
    return null;
  }

  public Page<OrderDto> findOrdersByCriteria(OrderSearchCriteriaDto cto) {

    Page<OrderEntity> orders = this.orderRepository.findByCriteria(cto);
    return new PageImpl<>(this.orderMapper.map(orders.getContent()), orders.getPageable(), orders.getTotalElements());
  }

  public Page<ItemDto> findItemsByOrderId(Long id) {

    List<ItemEntity> itemFoundByOrderId = this.itemRepository.findAllByProductorderId(id);
    return new PageImpl<>(this.itemMapper.map(itemFoundByOrderId));
  }
}
