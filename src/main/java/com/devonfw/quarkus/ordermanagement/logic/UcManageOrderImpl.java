package com.devonfw.quarkus.ordermanagement.logic;

import com.devonfw.quarkus.general.domain.model.OrderStatus;
import com.devonfw.quarkus.general.restclient.product.ProductsRestClient;
import com.devonfw.quarkus.general.restclient.product.models.ProductDto;
import com.devonfw.quarkus.ordermanagement.domain.model.ItemEntity;
import com.devonfw.quarkus.ordermanagement.domain.model.OrderEntity;
import com.devonfw.quarkus.ordermanagement.domain.repo.ItemRepository;
import com.devonfw.quarkus.ordermanagement.domain.repo.OrderRepository;
import com.devonfw.quarkus.ordermanagement.service.v1.mapper.OrderMapper;
import com.devonfw.quarkus.ordermanagement.service.v1.model.NewOrderDto;
import com.devonfw.quarkus.ordermanagement.service.v1.model.OrderDto;
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

@Named
@Transactional
public class UcManageOrderImpl implements UcManageOrder {

    @Inject
    OrderRepository orderRepository;

    @Inject
    ItemRepository itemRepository;

    @Inject
    OrderMapper orderMapper;

    @Inject
    @RestClient
    ProductsRestClient productsRestClient;

    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    public OrderDto saveOrder(NewOrderDto dto) {
        OrderEntity entity = new OrderEntity();
        entity.setStatus(OrderStatus.OPEN);
        entity.setCreationDate(Instant.now());
        if (dto.getPaymentDate() != null) {
            try {
                entity.setPaymentDate(dateFormatter.parse(dto.getPaymentDate()).toInstant());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        List<ItemEntity> listItems = new ArrayList<>();
        BigDecimal totalPrice = new BigDecimal(0.0);
        for (Long id : dto.getOrderedProducts()) {
            Response response = productsRestClient.getProductById(String.valueOf(id));
            ProductDto productDto = response.readEntity(ProductDto.class);
            ItemEntity itemEntity = new ItemEntity();
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
            itemRepository.save(e);
        }

        return this.orderMapper.map(created);
    }

    @Override
    public OrderDto deleteOrder(String id) {
        OrderEntity product = this.orderRepository.findById(Long.valueOf(id)).get();
        if (product != null) {
            this.orderRepository.delete(product);
            return this.orderMapper.map(product);
        } else {
            return null;
        }
    }
}
