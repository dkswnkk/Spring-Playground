package com.example.demo.src.domain.dto.user;

import lombok.Data;

import java.util.Map;

@Data
public class GetRecentlyViewProductRes {
    private Long productIdx;
    private String title;
    private String url;
    private int deliveryStatus;
    private int price;
    private String option;
    private String detail;
    private double reviewRate;
    private Long reviewCount;

    public GetRecentlyViewProductRes(Map<String, Object> product) {
        this.productIdx = (Long) product.get("P.productIdx");
        this.title = (String) product.get("title");
        this.url = (String) product.get("imageUrl");
        this.deliveryStatus = (int) product.get("deliveryStatus");
        this.price = (int) product.get("price");
        this.option = (String) product.get("options");
        this.detail = (String) product.get("detail");
        this.reviewRate = (double) product.get("reviewAvg");
        this.reviewCount = (Long) product.get("reviewCount");
    }
}
