package com.example.demo.src.domain.entitiy.menu;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@AllArgsConstructor
@Getter
public class SubCategory {
    private Long subCategoryIdx;
    private Long mainCategoryIdx;
    private Long pratentCategoryIdx;
    private int depth;
    private String title;
    private Boolean status;
    private Timestamp createdAt;
    private Timestamp updateAt;
}
