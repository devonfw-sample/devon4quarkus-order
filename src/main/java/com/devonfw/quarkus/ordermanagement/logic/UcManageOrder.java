package com.devonfw.quarkus.ordermanagement.logic;

import com.devonfw.quarkus.general.restclient.product.ProductsRestClient;
import com.devonfw.quarkus.general.restclient.product.models.ProductDto;
import com.devonfw.quarkus.ordermanagement.domain.model.ItemEntity;
import com.devonfw.quarkus.ordermanagement.domain.model.OrderEntity;
import com.devonfw.quarkus.ordermanagement.domain.model.OrderStatus;
import com.devonfw.quarkus.ordermanagement.domain.repo.ItemRepository;
import com.devonfw.quarkus.ordermanagement.domain.repo.OrderRepository;
import com.devonfw.quarkus.ordermanagement.service.v1.model.NewOrderDto;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Named
@Transactional
public class UcManageOrder {

    @Inject
    OrderRepository orderRepository;

    @Inject
    ItemRepository itemRepository;

    @Inject
    @RestClient
    ProductsRestClient productsRestClient;

    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

    public void saveOrder(NewOrderDto dto) {
        OrderEntity entity = new OrderEntity();
        entity.setStatus(OrderStatus.OPEN);
        entity.setCreationDate(Instant.now());
        if (dto.getPaymentDate() != null) {
            try {
                entity.setPaymentDate(dateFormatter.parse(dto.getPaymentDate()).toInstant());
            } catch (ParseException e) {
                return;
            }
        }

        List<ItemEntity> listItems = new ArrayList<>();
        BigDecimal totalPrice = new BigDecimal(0.0);
        for (Long id : dto.getOrderedProductIds()) {
            Response response = productsRestClient.getProductById(String.valueOf(id));
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

        OrderEntity created = orderRepository.save(entity);
        for (ItemEntity e : listItems) {
            e.setOrder(created);
            itemRepository.save(e);
        }
    }

    public void deleteOrder(String id) {
        itemRepository.deleteByOrderId(Long.valueOf(id));
        orderRepository.deleteById(Long.valueOf(id));
    }

    public void editOrderStatus(String id, String status) {
        Optional<OrderEntity> orderEntityOptional = orderRepository.findById(Long.valueOf(id));
        if (orderEntityOptional.isPresent()) {
            OrderEntity orderEntity = orderEntityOptional.get();
            orderEntity.setStatus(OrderStatus.valueOf(status.toUpperCase()));
            orderRepository.save(orderEntity);
        }
    }
}
