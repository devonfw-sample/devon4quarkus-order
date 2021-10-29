package com.devonfw.quarkus.ordermanagement.logic;

import com.devonfw.quarkus.ordermanagement.domain.model.ItemEntity;
import com.devonfw.quarkus.ordermanagement.domain.model.OrderEntity;
import com.devonfw.quarkus.ordermanagement.domain.repo.ItemRepository;
import com.devonfw.quarkus.ordermanagement.domain.repo.OrderRepository;
import com.devonfw.quarkus.ordermanagement.service.v1.mapper.ItemMapper;
import com.devonfw.quarkus.ordermanagement.service.v1.mapper.OrderMapper;
import com.devonfw.quarkus.ordermanagement.service.v1.model.ItemDto;
import com.devonfw.quarkus.ordermanagement.service.v1.model.OrderDto;
import com.devonfw.quarkus.ordermanagement.service.v1.model.OrderSearchCriteriaDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

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

    public OrderDto findOrder(Long id) {
        Optional<OrderEntity> orderEntityOptional = orderRepository.findById(id);
        if (orderEntityOptional.isPresent()) {
            return orderMapper.map(orderEntityOptional.get());
        }
        return null;
    }

    public Page<OrderDto> findOrdersByCriteria(OrderSearchCriteriaDto cto) {
        List<OrderEntity> orders = orderRepository.findOrdersByCriteria(cto.getPriceMax(), cto.getPriceMin());
        return new PageImpl<>(orderMapper.map(orders), PageRequest.of(cto.getPageNumber(), cto.getPageSize()), orders.size());
    }

    public Page<ItemDto> findItemsByOrderId(Long id) {
        List<ItemEntity> itemFoundByOrderId = itemRepository.findAllByProductorderId(id);
        return new PageImpl<>(itemMapper.map(itemFoundByOrderId));
    }
}
