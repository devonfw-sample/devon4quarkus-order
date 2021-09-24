package com.devonfw.quarkus.ordermanagement.domain.model;

import com.devonfw.quarkus.general.domain.model.ApplicationPersistenceEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "ProductOrder")
public class OrderEntity extends ApplicationPersistenceEntity {

    private BigDecimal price;

    private Instant creationDate;

    private Instant paymentDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

}
