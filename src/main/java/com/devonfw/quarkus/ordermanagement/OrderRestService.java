package com.devonfw.quarkus.ordermanagement;

import com.devonfw.quarkus.ordermanagement.logic.UcFindOrder;
import com.devonfw.quarkus.ordermanagement.logic.UcManageOrder;
import com.devonfw.quarkus.ordermanagement.service.v1.model.ItemDto;
import com.devonfw.quarkus.ordermanagement.service.v1.model.NewOrderDto;
import com.devonfw.quarkus.ordermanagement.service.v1.model.OrderDto;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.springframework.data.domain.Page;

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


@Path("/order/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderRestService {

    @Inject
    UcFindOrder ucFindOrder;

    @Inject
    UcManageOrder ucManageOrder;

    @GET
    public Page<OrderDto> getAll() {
        return ucFindOrder.findOrder();
    }

    @GET
    @Path("{id}")
    public OrderDto getOrderById(@PathParam("id") String id) {
        return ucFindOrder.findOrder(id);
    }

    @GET
    @Path("item/{id}")
    public Page<ItemDto> getItemByOrderId(@PathParam("id") String id) {
        return ucFindOrder.findItemByOrderId(id);
    }

    @POST
    public void createNewOrder(NewOrderDto dto) {
        ucManageOrder.saveOrder(dto);
    }

    @DELETE
    @Path("{id}")
    public void deleteOrderById(@PathParam("id") String id) {
        ucManageOrder.deleteOrder(id);
    }

    @PUT
    @Path("edit-status/{id}/{status}")
    public void editOrderStatus(@PathParam("id") String id, @PathParam("status") String status) {
        ucManageOrder.editOrderStatus(id, status);
    }
}
