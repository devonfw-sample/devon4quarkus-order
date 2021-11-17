package com.devonfw.quarkus.general.domain.model;

import org.springframework.data.domain.Pageable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class ApplicationSearchCriteriaDto {

  private Pageable pageable;

  private boolean determineTotal = false;

}