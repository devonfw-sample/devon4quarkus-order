package com.devonfw.quarkus.ordermanagement.service.v1.model;

import lombok.Getter;
import lombok.Setter;

import javax.ws.rs.DefaultValue;
import java.math.BigDecimal;

@Getter
@Setter
public class OrderSearchCriteriaDto {
    @DefaultValue("0")
    private int pageNumber = 0;

    @DefaultValue("10")
    private int pageSize = 10;

    private BigDecimal priceMin;

    private BigDecimal priceMax;
}
