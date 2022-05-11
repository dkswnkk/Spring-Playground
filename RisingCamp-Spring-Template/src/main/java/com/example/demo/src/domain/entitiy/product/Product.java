package com.example.demo.src.domain.entitiy.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class Product {
    private Long productIdx;
    private String title;
    private int deliveryStatus;
    private boolean optionStatus;
    private Boolean status;
    private Timestamp createdAt;
    private Timestamp updateAt;
}
