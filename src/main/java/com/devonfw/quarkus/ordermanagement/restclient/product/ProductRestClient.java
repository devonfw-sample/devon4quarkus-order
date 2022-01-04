package com.devonfw.quarkus.ordermanagement.restclient.product;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Path;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import com.devonfw.quarkus.ordermanagement.restclient.product.DefaultApi;

@Path("/product/v1")
@RegisterRestClient(configKey = "product-service-key")
@ApplicationScoped
public interface ProductRestClient extends DefaultApi {

}
