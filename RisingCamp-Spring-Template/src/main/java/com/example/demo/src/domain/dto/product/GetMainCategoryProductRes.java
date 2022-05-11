package com.example.demo.src.domain.dto.product;

import lombok.Data;

import java.util.Map;

@Data
public class GetMainCategoryProductRes {
    private String url;
    private String title;
    private String option;
    private String detail;
    private int deliveryStatus;
    private int price;
    private String expectedDelivery;
    private int reviewCount;
    private float reviewAvg;

    public GetMainCategoryProductRes(Map<String, Object> product, Map<String, Object> review) {
        this.url = (String) product.get("imageUrl");
        this.title = (String) product.get("title");
        this.option = (String) product.get("options");
        this.detail = (String) product.get("datail");
        this.deliveryStatus = (int) product.get("deliveryStatus");
        this.price = (int) product.get("price");
        this.expectedDelivery = product.get("expectedDelivery").toString();
        this.reviewCount = Integer.parseInt(review.get("reviewCnt").toString());
        this.reviewAvg = Float.parseFloat(review.get("reviewAvg").toString()) ;
    }
}
