package com.example.demo.src.domain.dto.product;

import com.example.demo.src.domain.dto.menu.GetMainMenuRes;
import com.example.demo.src.domain.entitiy.product.Product;
import com.example.demo.src.domain.entitiy.review.Review;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Data
public class GetMainMenuProductRes {
    private String url;
    private String title;
    private String option;
    private String detail;
    private int deliveryStatus;
    private int price;
    private String expectedDelivery;
    private int reviewCount;
    private float reviewAvg;

    public GetMainMenuProductRes(Map<String, Object> product, Map<String, Object> review) {
        this.url = (String) product.get("imageUrl");
        this.title = (String) product.get("title");
        this.option = (String) product.get("options");
        this.detail = (String) product.get("detail");
        this.deliveryStatus = (int) product.get("deliveryStatus");
        this.price = (int) product.get("price");
        this.expectedDelivery = product.get("expectedDelivery").toString();
        this.reviewCount = Integer.parseInt(review.get("reviewCnt").toString());
        this.reviewAvg = Float.parseFloat(review.get("reviewAvg").toString()) ;
    }
}
