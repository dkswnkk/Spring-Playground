package com.example.demo.src.domain.entitiy.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {
    private Long productIdx;
    private String url;
    private String title;
    private String option;
    private String detail;
    private int deliveryStatus;
    private int price;
    private String expectedDelivery;
}
