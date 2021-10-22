package com.devonfw.quarkus.ordermanagement.service.v1;

import com.devonfw.quarkus.general.restclient.product.ProductsRestClient;
import com.devonfw.quarkus.general.restclient.product.models.ProductDto;
import com.devonfw.quarkus.ordermanagement.service.v1.model.NewOrderDto;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.tkit.quarkus.test.WithDBData;
import org.tkit.quarkus.test.docker.DockerComposeTestResource;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Before you run this test, tkit-test extension starts docker containers from resources/docker-compose.yaml.
 * We get a real postgresdb for our tests which will be stopped after tests. No manual test setup is needed.
 */

@QuarkusTest
@QuarkusTestResource(DockerComposeTestResource.class)
public class OrderRestServiceTest {

    private static String BASE_PATH = "/ordermanagement/v1/order";

    @InjectMock
    @RestClient
    private ProductsRestClient productsRestClient;

    @Test
    @WithDBData(value = {"data/order.xls"}, deleteBeforeInsert = true, deleteAfterTest = true)
    public void getAll() {
        given().when().contentType(MediaType.APPLICATION_JSON)
                .get(BASE_PATH)
                .then()
                .statusCode(200)
                .body("totalElements", equalTo(2));
    }

    @Test
    @WithDBData(value = {"data/order.xls"}, deleteBeforeInsert = true, deleteAfterTest = true)
    public void getOrderById() {
        given().when().contentType(MediaType.APPLICATION_JSON)
                .get(BASE_PATH + "/1")
                .then()
                .statusCode(200)
                .body("price", equalTo(38.5F))
                .body("status", equalTo("OPEN"));

        given().when().contentType(MediaType.APPLICATION_JSON)
                .get(BASE_PATH + "/2")
                .then()
                .statusCode(200)
                .body("price", equalTo(10.0F))
                .body("status", equalTo("OPEN"));
    }

    @Test
    @WithDBData(value = {"data/order.xls"}, deleteBeforeInsert = true, deleteAfterTest = true)
    public void editOrder() {
        given().when().contentType(MediaType.APPLICATION_JSON)
                .put(BASE_PATH + "/edit-status/1/paid")
                .then()
                .statusCode(204);

        given().when().contentType(MediaType.APPLICATION_JSON)
                .get(BASE_PATH + "/1")
                .then()
                .statusCode(200)
                .body("status", equalTo("PAID"));
        // reset
        given().when().contentType(MediaType.APPLICATION_JSON)
                .put(BASE_PATH + "/edit-status/1/open")
                .then()
                .statusCode(204);
    }

    @Test
    public void createNewOrder() {
        mockRestClient();

        NewOrderDto order = new NewOrderDto();
        order.setPaymentDate("31-10-2021");
        order.setOrderedProductIds(Arrays.asList(10L, 20L));

        given().when().body(order).contentType(MediaType.APPLICATION_JSON).post(BASE_PATH)
                .then()
                .statusCode(204);

        // re-get the created order
        given().when().contentType(MediaType.APPLICATION_JSON)
                .get(BASE_PATH)
                .then()
                .statusCode(200)
                .body("totalElements", equalTo(1));

        // get items of created order
        io.restassured.response.Response response_items = given().when().contentType(MediaType.APPLICATION_JSON).get(BASE_PATH + "/item/1")
                .then()
                .statusCode(200)
                .extract().response();
        List<LinkedHashMap<String, String>> items = response_items.jsonPath().getList("content");
        assertEquals(items.size(), 2);
        assertEquals(items.get(0).get("title"), "new product 1");
        assertEquals(items.get(1).get("title"), "new product 2");

        // deleting created order to reset db state
        given().when().contentType(MediaType.APPLICATION_JSON)
                .delete(BASE_PATH + "/1")
                .then()
                .statusCode(204);
    }

    private void mockRestClient() {
        ProductDto product_1 = new ProductDto();
        product_1.setId(10L);
        product_1.setPrice(BigDecimal.valueOf(18.5));
        product_1.setTitle("new product 1");
        product_1.setDescription("description of product 1");

        ProductDto product_2 = new ProductDto();
        product_2.setId(20L);
        product_2.setPrice(BigDecimal.valueOf(10));
        product_2.setTitle("new product 2");
        product_2.setDescription("description of product 2");

        Mockito.when(productsRestClient.getProductById("10")).thenReturn(Response.ok().entity(product_1).build());
        Mockito.when(productsRestClient.getProductById("20")).thenReturn(Response.ok().entity(product_2).build());
    }
}
