package com.devonfw.quarkus.ordermanagement.service.v1.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Setter
@Getter
public class ItemDto {
    private String title;

    private BigDecimal price;

    private Instant creationDate;
}
