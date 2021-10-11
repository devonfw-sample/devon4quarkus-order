package com.devonfw.quarkus.ordermanagement.logic;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.devonfw.quarkus.ordermanagement.service.v1.mapper.OrderMapper;
import com.devonfw.quarkus.ordermanagement.service.v1.model.NewOrderDto;
import com.devonfw.quarkus.ordermanagement.service.v1.model.OrderDto;

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

  @Inject
  OrderMapper mapper;

  private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

  public OrderDto saveOrder(NewOrderDto dto) throws ParseException {

    OrderEntity entity = new OrderEntity();
    entity.setStatus(OrderStatus.OPEN);
    entity.setCreationDate(Instant.now());
    if (dto.getPaymentDate() != null) {
      try {
        entity.setPaymentDate(this.dateFormatter.parse(dto.getPaymentDate()).toInstant());
      } catch (ParseException e) {
        throw new ParseException("payment date is null", 0);
      }
    }

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
      e.setOrder(created);
      this.itemRepository.save(e);
    }
    return this.mapper.map(created);
  }

  public void deleteOrder(String id) {

    this.itemRepository.deleteByOrderId(Long.valueOf(id));
    this.orderRepository.deleteById(Long.valueOf(id));
  }

  public void editOrderStatus(String id, String status) {

    Optional<OrderEntity> orderEntityOptional = this.orderRepository.findById(Long.valueOf(id));
    if (orderEntityOptional.isPresent()) {
      OrderEntity orderEntity = orderEntityOptional.get();
      orderEntity.setStatus(OrderStatus.valueOf(status.toUpperCase()));
      this.orderRepository.save(orderEntity);
    }
  }
}
