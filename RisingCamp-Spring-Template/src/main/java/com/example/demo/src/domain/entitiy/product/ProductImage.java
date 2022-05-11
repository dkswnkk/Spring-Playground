package com.example.demo.src.domain.entitiy.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class ProductImage {
    private Long productImageIdx;
    private Long productIdx;
    private boolean thumbnail;
    private String imageUrl;
    private Boolean status;
    private Timestamp createdAt;
    private Timestamp updateAt;
}
