package com.producttask.product.model;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Product {

    private int productId;
    private String productName;
    private int prodQuant;
    private double price;
}
