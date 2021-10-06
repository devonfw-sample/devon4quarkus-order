package com.devonfw.quarkus.ordermanagement.service.v1.model;

import com.devonfw.quarkus.ordermanagement.domain.model.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
public class OrderDto {

    private Long id;

    private BigDecimal price;

    private Instant creationDate;

    private Instant paymentDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

}
