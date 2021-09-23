package com.devonfw.quarkus.ordermanagement;

import com.devonfw.quarkus.ordermanagement.logic.UcFindOrder;
import com.devonfw.quarkus.ordermanagement.logic.UcManageOrder;
import com.devonfw.quarkus.ordermanagement.service.v1.model.NewOrderDto;
import com.devonfw.quarkus.ordermanagement.service.v1.model.OrderDto;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.springframework.data.domain.Page;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderRestService {

    @Inject
    UcFindOrder ucFindOrder;

    @Inject
    UcManageOrder ucManageOrder;

    @GET
    public Page<OrderDto> getAll() {

        return this.ucFindOrder.findOrder();
    }

    @GET
    @Path("{id}")
    public OrderDto getOrderById(@PathParam("id") String id) {

        return this.ucFindOrder.findOrder(id);
    }

    @POST
    public OrderDto createNewOrder(NewOrderDto dto) {

        return this.ucManageOrder.saveOrder(dto);
    }

    @DELETE
    @Path("{id}")
    public OrderDto deleteOrderById(@Parameter(description = "Order unique id") @PathParam("id") String id) {

        return this.ucManageOrder.deleteOrder(id);
    }


}
