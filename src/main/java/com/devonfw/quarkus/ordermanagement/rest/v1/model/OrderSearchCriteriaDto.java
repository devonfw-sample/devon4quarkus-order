package com.devonfw.quarkus.ordermanagement.rest.v1.model;

import java.math.BigDecimal;

import com.devonfw.quarkus.general.domain.model.ApplicationSearchCriteriaDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSearchCriteriaDto extends ApplicationSearchCriteriaDto {

  private BigDecimal priceMin;

  private BigDecimal priceMax;
}
