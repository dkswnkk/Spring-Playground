package com.example.demo.src.domain.dto.user.wish;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class GetWishListRes {
    private Long productIdx;
    private String title;
    private String url;
    private int price;
    private String option;
    private int stock;

    public GetWishListRes(Map<String, Object> wishList) {
        this.productIdx = (Long) wishList.get("productIdx");
        this.title = (String) wishList.get("title");
        this.url = (String) wishList.get("imageUrl");
        this.price = (int) wishList.get("price");
        this.option = (String) wishList.get("options");
        this.stock = (int) wishList.get("stock");
    }
}
