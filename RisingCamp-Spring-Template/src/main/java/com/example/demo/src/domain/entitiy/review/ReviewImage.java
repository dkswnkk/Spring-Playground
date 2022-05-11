package com.example.demo.src.domain.entitiy.review;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class ReviewImage {
    private Long reviewImageIdx;
    private Long reviewIdx;
    private Boolean thumbnail;
    private String url;
    private Boolean status;
    private Timestamp createdAt;
    private Timestamp updateAt;
}
