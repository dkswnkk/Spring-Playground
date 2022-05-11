package com.example.demo.src.domain.entitiy.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class ProductOption {
    private Long productOptionIdx;
    private Long productIdx;
    private Boolean dafaultStatus;
    private int price;
    private String title;
    private String option;
    private String detail;
    private int expectedDelivery;
    private int deliveryStatus;
    private int stock;
    private Boolean status;
    private Timestamp createdAt;
    private Timestamp updateAt;


}
