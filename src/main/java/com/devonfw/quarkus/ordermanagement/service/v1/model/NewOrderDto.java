package com.devonfw.quarkus.ordermanagement.service.v1.model;

import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;

@Getter
@Setter
public class NewOrderDto {
    @Schema(nullable = false, description = "Payment date in format dd-MM-yyyy")
    private String paymentDate;
    @Schema(nullable = false, description = "List ids of ordered products")
    private List<Long> orderedProductIds;
}
