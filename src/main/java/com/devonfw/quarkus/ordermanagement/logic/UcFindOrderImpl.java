package com.devonfw.quarkus.ordermanagement.logic;

import com.devonfw.quarkus.ordermanagement.domain.model.OrderEntity;
import com.devonfw.quarkus.ordermanagement.domain.repo.OrderRepository;
import com.devonfw.quarkus.ordermanagement.service.v1.mapper.OrderMapper;
import com.devonfw.quarkus.ordermanagement.service.v1.model.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

@Named
@Transactional
public class UcFindOrderImpl implements UcFindOrder {

    @Inject
    OrderRepository orderRepository;

    @Inject
    OrderMapper orderMapper;

    @Override
    public OrderDto findOrder(String id) {
        OrderEntity orderEntity = this.orderRepository.findById(Long.valueOf(id)).get();
        if (orderEntity != null) {
            return this.orderMapper.map(orderEntity);
        } else {
            return null;
        }
    }

    @Override
    public Page<OrderDto> findOrder() {
        List<OrderEntity> all = this.orderRepository.findAll();
        return new PageImpl<>(this.orderMapper.map(all));
    }
}
