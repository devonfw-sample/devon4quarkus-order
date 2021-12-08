package com.devonfw.quarkus.ordermanagement.rest.v1.model;

import java.time.Instant;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewOrderDto {

  private Instant paymentDate;

  private List<Long> orderedProductIds;
}
