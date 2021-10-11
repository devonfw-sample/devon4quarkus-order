package com.devonfw.quarkus.ordermanagement.service.v1;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.Test;
import org.tkit.quarkus.test.WithDBData;
import org.tkit.quarkus.test.docker.DockerComposeTestResource;

import com.devonfw.quarkus.ordermanagement.domain.model.OrderStatus;
import com.devonfw.quarkus.ordermanagement.service.v1.model.OrderDto;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
//Before you run this test, tkit-test extension starts docker containers from resources/docker-compose.yaml
//we get a real postgresdb for our tests which will be stopped after tests. No manual test setup is needed.

@QuarkusTest
@QuarkusTestResource(DockerComposeTestResource.class)
public class OrderRestServiceTest {
  @Test
  // we also started a micro container, that can populated DB with data from excel
  // annotating class or method with @WithDBData allows us to scope data for each test even if we use the same DB
  @WithDBData(value = "data/order.xls", deleteBeforeInsert = true)
  void getAll() {

    Response response = given().when().contentType(MediaType.APPLICATION_JSON).get("/ordermanagement/v1/order").then()
        .statusCode(200).extract().response();

    int orders = Integer.valueOf(response.jsonPath().getString("totalElements"));
    assertEquals(2, orders);
  }

  @Test
  void getNonExistingTest() {

    Response response = given().when().contentType(MediaType.APPLICATION_JSON)
        .get("/ordermanagement/v1/order/doesnoexist").then().log().all().statusCode(500).extract().response();
  }

  @Test
  @WithDBData(value = "data/empty.xls", deleteBeforeInsert = true)
  void createNewOrder() {

    OrderDto order = new OrderDto();
    order.setCreationDate(Instant.now());
    order.setPaymentDate(Instant.now());
    order.setPrice(BigDecimal.valueOf(1));
    order.setStatus(OrderStatus.OPEN);

    Response response = given().when().body(order).contentType(MediaType.APPLICATION_JSON)
        .post("/ordermanagement/v1/order").then().log().all().statusCode(200).header("Location", nullValue()).extract()
        .response();

    assertEquals(200, response.statusCode());

    response = given().when().contentType(MediaType.APPLICATION_JSON).get("/ordermanagement/v1/order").then().log()
        .all().statusCode(200).extract().response();

    int orders = Integer.valueOf(response.jsonPath().getString("totalElements"));
    assertEquals(1, orders);
    List<LinkedHashMap<String, String>> created = response.jsonPath().getList("content");
    assertNotNull(created);
    assertEquals(order.getCreationDate(), created.get(0).get("creationDate"));
  }

  @Test
  @WithDBData(value = "data/order.xls", deleteBeforeInsert = true)
  public void testGetById() {

    given().when().log().all().contentType(MediaType.APPLICATION_JSON).get("/ordermanagement/v1/order/1").then()
        .statusCode(200).body("paymentDate", equalTo(Instant.now()));
  }

  @Test
  @WithDBData(value = "data/order.xls", deleteBeforeInsert = true)
  public void deleteById() {

    // delete
    given().when().log().all().contentType(MediaType.APPLICATION_JSON).delete("/ordermanagement/v1/order/1").then()
        .statusCode(204);

    // after deletion it should be deleted
    given().when().log().all().contentType(MediaType.APPLICATION_JSON).get("/ordermanagement/v1/order/1").then()
        .statusCode(500);
  }

  @Test
  @WithDBData(value = "data/order.xls", deleteBeforeInsert = true)
  public void getOrderStatus() {

    given().when().log().all().contentType(MediaType.APPLICATION_JSON).get("/ordermanagement/v1/order/1").then()
        .statusCode(200).body("status", equalTo(OrderStatus.OPEN.toString()));
  }
}
