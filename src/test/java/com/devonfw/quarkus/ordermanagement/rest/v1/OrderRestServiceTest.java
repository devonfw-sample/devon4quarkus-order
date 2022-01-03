package com.devonfw.quarkus.ordermanagement.rest.v1;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.data.domain.PageRequest;

import com.devonfw.quarkus.general.restclient.product.ProductRestClient;
import com.devonfw.quarkus.general.restclient.product.models.ProductDto;
import com.devonfw.quarkus.ordermanagement.rest.v1.model.NewOrderDto;
import com.devonfw.quarkus.ordermanagement.rest.v1.model.OrderSearchCriteriaDto;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.response.Response;

@QuarkusTest
@QuarkusTestResource(PostgresResource.class)
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class OrderRestServiceTest {

  private static String BASE_PATH = "/ordermanagement/v1/order";

  @InjectMock
  @RestClient
  private ProductRestClient productRestClient;

  @BeforeAll
  private void setup() {

    mockRestClient();
  }

  @Test
  @Order(1)
  public void createNewOrder() {

    NewOrderDto order = new NewOrderDto();
    order.setPaymentDate(Instant.now());
    order.setOrderedProductIds(Arrays.asList(10L, 20L));

    given().when().body(order).contentType(MediaType.APPLICATION_JSON).post(BASE_PATH).then().statusCode(204);

    // get items of created order
    Response response_items = given().when().contentType(MediaType.APPLICATION_JSON).get(BASE_PATH + "/item/1").then()
        .statusCode(200).extract().response();
    List<LinkedHashMap<String, String>> items = response_items.jsonPath().getList("content");
    assertEquals(items.size(), 2);
    assertEquals(items.get(0).get("title"), "new product 1");
    assertEquals(items.get(1).get("title"), "new product 2");
  }

  @Test
  @Order(2)
  public void getOrderByCriteria() {

    OrderSearchCriteriaDto cto = new OrderSearchCriteriaDto();
    cto.setPriceMin(BigDecimal.valueOf(0));
    cto.setPriceMax(BigDecimal.valueOf(100));
    cto.setPageable(PageRequest.of(0, 10));

    io.restassured.response.Response r = given().when().contentType(MediaType.APPLICATION_JSON).body(cto)
        .post(BASE_PATH + "/search");

    r.then().statusCode(200).body("totalElements", equalTo(1));

    cto.setPriceMax(BigDecimal.valueOf(20));
    given().when().contentType(MediaType.APPLICATION_JSON).body(cto).post(BASE_PATH + "/search").then().statusCode(200)
        .body("totalElements", equalTo(0));
  }

  @Test
  @Order(3)
  public void getOrderById() {

    given().when().contentType(MediaType.APPLICATION_JSON).get(BASE_PATH + "/1").then().statusCode(200)
        .body("price", equalTo(28.5F)).body("status", equalTo("OPEN"));

    given().when().contentType(MediaType.APPLICATION_JSON).get(BASE_PATH + "/2").then().statusCode(204);
  }

  @Test
  @Order(4)
  public void editOrder() {

    given().when().contentType(MediaType.APPLICATION_JSON).put(BASE_PATH + "/edit-status/1/paid").then()
        .statusCode(204);

    given().when().contentType(MediaType.APPLICATION_JSON).get(BASE_PATH + "/1").then().statusCode(200).body("status",
        equalTo("PAID"));
    // reset
    given().when().contentType(MediaType.APPLICATION_JSON).put(BASE_PATH + "/edit-status/1/open").then()
        .statusCode(204);
  }

  @Test
  @Order(5)
  public void deleteOrder() {

    given().when().contentType(MediaType.APPLICATION_JSON).delete(BASE_PATH + "/1").then().statusCode(204);

    given().when().contentType(MediaType.APPLICATION_JSON).get(BASE_PATH + "/1").then().statusCode(204);
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

    Mockito.when(this.productRestClient.productV1IdGet("10"))
        .thenReturn(javax.ws.rs.core.Response.ok().entity(product_1).build());
    Mockito.when(this.productRestClient.productV1IdGet("20"))
        .thenReturn(javax.ws.rs.core.Response.ok().entity(product_2).build());
  }
}
