package com.devonfw.quarkus.ordermanagement;

import com.devonfw.quarkus.general.restclient.product.models.ProductDto;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderRestService {
    @Inject
    TestResource test;

    @GET
    @Path("/{id}")
    public ProductDto getProductById(@PathParam("id") String id) {
        return test.getProductById(id);
    }
}

