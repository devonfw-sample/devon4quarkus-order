package com.devonfw.quarkus.ordermanagement.rest.v1;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.data.domain.Page;

import com.devonfw.quarkus.ordermanagement.domain.model.OrderStatus;
import com.devonfw.quarkus.ordermanagement.logic.UcFindOrder;
import com.devonfw.quarkus.ordermanagement.logic.UcManageOrder;
import com.devonfw.quarkus.ordermanagement.service.v1.model.ItemDto;
import com.devonfw.quarkus.ordermanagement.service.v1.model.NewOrderDto;
import com.devonfw.quarkus.ordermanagement.service.v1.model.OrderDto;
import com.devonfw.quarkus.ordermanagement.service.v1.model.OrderSearchCriteriaDto;

@Path("/ordermanagement/v1/order")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderRestService {

  @Inject
  UcFindOrder ucFindOrder;

  @Inject
  UcManageOrder ucManageOrder;

  @POST
  @Path("/search")
  public Page<OrderDto> getOrderByCriteria(OrderSearchCriteriaDto searchCriteriaDto) {

    return this.ucFindOrder.findOrdersByCriteria(searchCriteriaDto);
  }

  @GET
  @Path("{id}")
  public OrderDto getOrderById(@PathParam("id") Long id) {

    return this.ucFindOrder.findOrder(id);
  }

  @GET
  @Path("item/{id}")
  public Page<ItemDto> getItemsByOrderId(@PathParam("id") Long id) {

    return this.ucFindOrder.findItemsByOrderId(id);
  }

  @POST
  public void createNewOrder(NewOrderDto dto) {

    this.ucManageOrder.saveOrder(dto);
  }

  @DELETE
  @Path("{id}")
  public void deleteOrderById(@PathParam("id") Long id) {

    this.ucManageOrder.deleteOrder(id);
  }

  @PUT
  @Path("edit-status/{id}/{status}")
  public void editOrderStatus(@PathParam("id") Long id, @PathParam("status") String statusStr) {

    OrderStatus status = OrderStatus.valueOf(statusStr.toUpperCase());
    this.ucManageOrder.editOrderStatus(id, status);
  }
}
