package com.example.junit5.Entity;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Member {
    public String name;
    public int age;
    MemberRole memberRole;
}

enum MemberRole {
    ADMIN, VIP, BASIC
}
