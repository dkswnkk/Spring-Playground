package com.example.demo.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BaseException extends Exception {
    private BaseResponseStatus status;  //BaseResoinseStatus 객체에 매핑
}
