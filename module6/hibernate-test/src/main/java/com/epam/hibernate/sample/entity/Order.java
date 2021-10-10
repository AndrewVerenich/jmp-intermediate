package com.epam.hibernate.sample.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ORDERS")
@Data
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private transient BigDecimal price;
    @ColumnDefault("'n/a'")
    private String description;

}
