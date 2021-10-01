package com.devonfw.quarkus.ordermanagement.logic;

import com.devonfw.quarkus.ordermanagement.domain.model.ItemEntity;
import com.devonfw.quarkus.ordermanagement.domain.model.OrderEntity;
import com.devonfw.quarkus.ordermanagement.domain.repo.ItemRepository;
import com.devonfw.quarkus.ordermanagement.domain.repo.OrderRepository;
import com.devonfw.quarkus.ordermanagement.service.v1.mapper.ItemMapper;
import com.devonfw.quarkus.ordermanagement.service.v1.mapper.OrderMapper;
import com.devonfw.quarkus.ordermanagement.service.v1.model.ItemDto;
import com.devonfw.quarkus.ordermanagement.service.v1.model.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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

    public OrderDto findOrder(String id) {
        Optional<OrderEntity> orderEntityOptional = orderRepository.findById(Long.valueOf(id));
        if (orderEntityOptional.isPresent()) {
            return orderMapper.map(orderEntityOptional.get());
        }
        return null;
    }

    public Page<OrderDto> findOrders() {
        List<OrderEntity> allOrderEntity = orderRepository.findAll();
        return new PageImpl<>(orderMapper.map(allOrderEntity));
    }

    public Page<ItemDto> findItemsByOrderId(String id) {
        List<ItemEntity> itemFoundByOrderId = itemRepository.findItemsByOrderId(Long.valueOf(id));
        return new PageImpl<>(itemMapper.map(itemFoundByOrderId));
    }

}
