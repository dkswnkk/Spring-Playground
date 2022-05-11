package com.example.demo.src.domain.entitiy.review;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@AllArgsConstructor
@Getter
public class Review {
    private Long reviewIdx;
    private Long userIdx;
    private Long orderListIdx;
    private Long productIdx;
    private Double rate;
    private String title;
    private String content;
    private int helpedCount;
    private int unHelpedCount;
    private Boolean status;
    private Timestamp createdAt;
    private Timestamp updateAt;
}
