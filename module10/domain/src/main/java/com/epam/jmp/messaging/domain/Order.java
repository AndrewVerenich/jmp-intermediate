package com.epam.jmp.messaging.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order implements Serializable {
    private int id;
    private String user;
    private GoodsType goodsType;
    private double volume;
    private int itemsNumber;
    private double total;
}
