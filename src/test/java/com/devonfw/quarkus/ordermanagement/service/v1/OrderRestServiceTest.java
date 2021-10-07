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

@QuarkusTest
@QuarkusTestResource(DockerComposeTestResource.class)
public class OrderRestServiceTest {
  @Test
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
    order.setCreationDate(Instant.EPOCH);
    order.setPaymentDate(Instant.EPOCH);
    order.setPrice(BigDecimal.valueOf(1));
    order.setStatus(OrderStatus.PAID);

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
        .statusCode(200).body("paymentDate", equalTo("2021-10-05 20:20:00"));
  }

  @Test
  @WithDBData(value = "data/order.xls", deleteBeforeInsert = true)
  public void deleteById() {

    // delete
    given().when().log().all().contentType(MediaType.APPLICATION_JSON).delete("/ordermanagement/v1/order/1").then()
        .statusCode(200).body("creationDate", equalTo("2021-10-01 12:00:00"));

    // after deletion it should be deleted
    given().when().log().all().contentType(MediaType.APPLICATION_JSON).get("/ordermanagement/v1/order/1").then()
        .statusCode(500);
  }

  @Test
  @WithDBData(value = "data/order.xls", deleteBeforeInsert = true)
  public void getOrderStatus() {

    given().when().log().all().contentType(MediaType.APPLICATION_JSON).get("/ordermanagement/v1/order/1").then()
        .statusCode(200).body("status", equalTo(OrderStatus.PAID));
  }
}
