package com.example.demo.src.domain.dto.menu;

import lombok.Data;

import java.util.Map;

@Data
public class SubCategoryDto {
    private Long subCategoryIdx;
    private Long mainCategoryIdx;
    private Long parentCategoryIdx;
    private int depth;
    private String title;

    public SubCategoryDto(Map<String, Object> category) {
        this.subCategoryIdx = (Long) category.get("subCategoryIdx");
        this.mainCategoryIdx = (Long) category.get("mainCategoryIdx");
        this.parentCategoryIdx = (Long) category.get("parentCategoryIdx");
        this.depth = (Integer) category.get("depth");
        this.title = (String) category.get("title");
    }
}
