package com.devonfw.quarkus.ordermanagement;

import com.devonfw.quarkus.general.restclient.product.ProductsRestClient;
import com.devonfw.quarkus.general.restclient.product.models.ProductDto;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.Response;

@Named
public class TestResource {

    @Inject
    @RestClient
    ProductsRestClient productsRestClient;

    public ProductDto getProductById(String id) {
        Response response = productsRestClient.getProductById(id);
        ProductDto dto =  response.readEntity(ProductDto.class);
        return  dto;
    }
}
