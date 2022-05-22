package com.example.javapractice.domain;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseFormat<T> {

    private Integer code;
    private Boolean result;
    private T data;
    private String description;

    public static <T> ResponseFormat<T> ok(T data){
        return ResponseFormat.<T>builder()
                .code(123)
                .result(true)
                .data(data)
                .description("성공")
                .build();
    }
}
