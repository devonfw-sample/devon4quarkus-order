package com.devonfw.quarkus.ordermanagement.logic;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.devonfw.quarkus.general.restclient.product.ProductsRestClient;
import com.devonfw.quarkus.general.restclient.product.models.ProductDto;
import com.devonfw.quarkus.ordermanagement.domain.model.ItemEntity;
import com.devonfw.quarkus.ordermanagement.domain.model.OrderEntity;
import com.devonfw.quarkus.ordermanagement.domain.model.OrderStatus;
import com.devonfw.quarkus.ordermanagement.domain.repo.ItemRepository;
import com.devonfw.quarkus.ordermanagement.domain.repo.OrderRepository;
import com.devonfw.quarkus.ordermanagement.service.v1.model.NewOrderDto;

@Named
@Transactional
@ApplicationScoped
public class UcManageOrder {

  @Inject
  OrderRepository orderRepository;

  @Inject
  ItemRepository itemRepository;

  @Inject
  @RestClient
  ProductsRestClient productsRestClient;

  public void saveOrder(NewOrderDto dto) {

    OrderEntity entity = new OrderEntity();
    entity.setStatus(OrderStatus.OPEN);
    entity.setCreationDate(Instant.now());
    entity.setPaymentDate(dto.getPaymentDate());

    List<ItemEntity> listItems = new ArrayList<>();
    BigDecimal totalPrice = new BigDecimal(0.0);
    for (Long id : dto.getOrderedProductIds()) {
      Response response = this.productsRestClient.getProductById(String.valueOf(id));
      ProductDto productDto = response.readEntity(ProductDto.class);
      ItemEntity itemEntity = new ItemEntity();
      itemEntity.setTitle(productDto.title);
      itemEntity.setProductId(productDto.id);
      itemEntity.setCreationDate(Instant.now());
      itemEntity.setPrice(productDto.price);
      totalPrice = totalPrice.add(itemEntity.getPrice());
      listItems.add(itemEntity);
    }

    entity.setPrice(totalPrice);

    OrderEntity created = this.orderRepository.save(entity);
    for (ItemEntity e : listItems) {
      e.setProductorder(created);
      this.itemRepository.save(e);
    }
  }

  public void deleteOrder(Long id) {

    this.itemRepository.deleteAllByProductorderId(id);
    this.orderRepository.deleteById(id);
  }

  public void editOrderStatus(Long id, OrderStatus status) {

    Optional<OrderEntity> orderEntityOptional = this.orderRepository.findById(id);
    if (orderEntityOptional.isPresent()) {
      OrderEntity orderEntity = orderEntityOptional.get();
      orderEntity.setStatus(status);
      this.orderRepository.save(orderEntity);
    }
  }
}
