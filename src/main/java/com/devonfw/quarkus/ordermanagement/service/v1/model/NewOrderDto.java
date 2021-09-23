package com.devonfw.quarkus.ordermanagement.service.v1.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NewOrderDto {
    private String paymentDate;
    private List<Long> orderedProducts;
}
