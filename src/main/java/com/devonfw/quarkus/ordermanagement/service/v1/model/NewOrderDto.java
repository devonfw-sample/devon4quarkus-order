package com.devonfw.quarkus.ordermanagement.service.v1.model;

import java.time.Instant;
import java.util.List;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewOrderDto {

  @Schema(nullable = false, description = "Payment date in format dd-MM-yyyy")
  private Instant paymentDate;

  @Schema(nullable = false, description = "List ids of ordered products")
  private List<Long> orderedProductIds;
}
