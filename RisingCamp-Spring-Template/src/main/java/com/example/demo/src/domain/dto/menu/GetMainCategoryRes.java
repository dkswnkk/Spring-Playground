package com.example.demo.src.domain.dto.menu;

import lombok.Data;

import java.util.Map;

@Data
public class GetMainCategoryRes {
    private Long mainCategoryIdx;
    private String title;
    private String url;


    public GetMainCategoryRes(Map<String, Object> category) {
        this.mainCategoryIdx = (Long) category.get("mainCategoryIdx");
        this.title = (String) category.get("title");
        this.url = (String) category.get("imageUrl");
    }
}
