package com.example.demo.src.domain.entitiy;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@AllArgsConstructor
@Getter
public class MainCategory {
    private Long mainCategoryIdx;
    private Long mainMenuIdx;
    private String title;
    private String imageUrl;
    private Boolean status;
    private Timestamp createdAt;
    private Timestamp updateAt;
}
