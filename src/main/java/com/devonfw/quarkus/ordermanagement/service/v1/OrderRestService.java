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

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.springframework.data.domain.Page;
import org.tkit.quarkus.rs.models.PageResultDTO;

import com.devonfw.quarkus.ordermanagement.logic.UcFindOrder;
import com.devonfw.quarkus.ordermanagement.logic.UcManageOrder;
import com.devonfw.quarkus.ordermanagement.service.v1.model.ItemDto;
import com.devonfw.quarkus.ordermanagement.service.v1.model.NewOrderDto;
import com.devonfw.quarkus.ordermanagement.service.v1.model.OrderDto;

@Path("/ordermanagement/v1/order")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderRestService {

  @Inject
  UcFindOrder ucFindOrder;

  @Inject
  UcManageOrder ucManageOrder;

  @APIResponses({
  @APIResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = PagedProductResponse.class))),
  @APIResponse(responseCode = "500") })
  @Operation(operationId = "Get Orders", description = "Returns list of Orders matching given criteria, uses pagination")

  @GET
  public Page<OrderDto> getAll() {

    return this.ucFindOrder.findOrders();
  }

  @APIResponses({
  @APIResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = OrderDto.class))),
  @APIResponse(responseCode = "404", description = "Order not found"), @APIResponse(responseCode = "500") })
  @Operation(operationId = "getOrderById", description = "Returns Order with given id")
  @GET
  @Path("{id}")
  public OrderDto getOrderById(@PathParam("id") String id) {

    return this.ucFindOrder.findOrder(id);
  }

  @GET
  @Path("item/{id}")
  public Page<ItemDto> getItemsByOrderId(@PathParam("id") String id) {

    return this.ucFindOrder.findItemsByOrderId(id);
  }

  @APIResponses({
  @APIResponse(responseCode = "200", description = "OK, New Order created", content = @Content(schema = @Schema(implementation = NewOrderDto.class))),
  @APIResponse(responseCode = "400", description = "Client side error, invalid request"),
  @APIResponse(responseCode = "500") })
  @Operation(operationId = "createNewOrder", description = "Stores new Order in DB")
  @POST
  public OrderDto createNewOrder(NewOrderDto dto) {

    OrderDto odto = null;
    try {
      odto = this.ucManageOrder.saveOrder(dto);
    } catch (Exception e) {

    }
    return odto;
  }

  @APIResponses({
  @APIResponse(responseCode = "204", description = "OK", content = @Content(schema = @Schema(implementation = OrderDto.class))),
  @APIResponse(responseCode = "404", description = "Order not found"), @APIResponse(responseCode = "500") })
  @Operation(operationId = "deleteOrderById", description = "Deletes the Order with given id")
  @DELETE
  @Path("{id}")
  public void deleteOrderById(@PathParam("id") String id) {

    this.ucManageOrder.deleteOrder(id);
  }

  @PUT
  @Path("edit-status/{id}/{status}")
  public void editOrderStatus(@PathParam("id") String id, @PathParam("status") String status) {

    this.ucManageOrder.editOrderStatus(id, status);
  }
}

class PagedProductResponse extends PageResultDTO<OrderDto> {
}
