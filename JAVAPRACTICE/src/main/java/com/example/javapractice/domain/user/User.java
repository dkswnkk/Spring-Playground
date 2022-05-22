package com.example.javapractice.domain.user;

import lombok.*;

import javax.validation.constraints.Min;

@Getter
@ToString
@NoArgsConstructor
public class User {
    @NonNull
    private String name;
    @Min(value = 19, message = "나이는 19살 이상이어야 합니다.")
    private int age;

    @Builder
    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
