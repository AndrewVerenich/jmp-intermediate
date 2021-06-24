package com.epam.serverless.saveproduct;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private Integer id;
    private String name;
    private BigDecimal price;
    private String pictureUrl;
}
