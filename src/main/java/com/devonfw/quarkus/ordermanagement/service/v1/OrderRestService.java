package com.devonfw.quarkus.ordermanagement.service.v1;

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

  /**
   * Search orders based on search criteria
   *
   * @param searchCriteriaDto the search criteria
   * @return the orders that match the search criteria
   */
  @POST
  @Path("/search")
  public Page<OrderDto> getOrderByCriteria(OrderSearchCriteriaDto searchCriteriaDto) {

    return this.ucFindOrder.findOrdersByCriteria(searchCriteriaDto);
  }

  /**
   * Get an order by its id
   *
   * @param the id of the order
   * @return the order with the given id
   */
  @GET
  @Path("{id}")
  public OrderDto getOrderById(@PathParam("id") Long id) {

    return this.ucFindOrder.findOrder(id);
  }

  /**
   * Get items belonging to the order
   *
   * @param the id of the order
   * @return the items belonging to the order with the given id
   */
  @GET
  @Path("item/{id}")
  public Page<ItemDto> getItemsByOrderId(@PathParam("id") Long id) {

    return this.ucFindOrder.findItemsByOrderId(id);
  }

  /**
   * Create a new order
   *
   * @param dto the order to create, paymentDate format is yyyy-mm-ddThh:mm:ssZ
   */
  @POST
  public void createNewOrder(NewOrderDto dto) {

    this.ucManageOrder.saveOrder(dto);
  }

  /**
   * Delete an order with the given order id
   *
   * @param id the id of the order to delete
   */
  @DELETE
  @Path("{id}")
  public void deleteOrderById(@PathParam("id") Long id) {

    this.ucManageOrder.deleteOrder(id);
  }

  /**
   * Update the status of a given order
   *
   * @param id the id of the order
   * @param statusStr the new status
   */
  @PUT
  @Path("edit-status/{id}/{status}")
  public void editOrderStatus(@PathParam("id") Long id, @PathParam("status") String statusStr) {

    OrderStatus status = OrderStatus.valueOf(statusStr.toUpperCase());
    this.ucManageOrder.editOrderStatus(id, status);
  }
}
