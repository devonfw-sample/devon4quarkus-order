package com.devonfw.quarkus.ordermanagement.domain.model;

import com.devonfw.quarkus.general.domain.model.ApplicationPersistenceEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "Item")
public class ItemEntity extends ApplicationPersistenceEntity {

    private String title;

    private BigDecimal price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "productorder_id")
    private OrderEntity order;

    private Instant creationDate;

    private Long productId;
}
