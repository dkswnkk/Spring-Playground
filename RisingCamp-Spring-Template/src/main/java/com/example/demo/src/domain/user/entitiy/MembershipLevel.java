package com.example.demo.src.domain.user.entitiy;

import java.util.Arrays;

import lombok.Getter;

@Getter
public enum MembershipLevel {

    BASIC("BASIC"),
    ROCKET("ROCKET");

    private String value;

    MembershipLevel(String value) {
        this.value = value;
    }

    public static MembershipLevel of(String membership) {
        return Arrays.stream(MembershipLevel.values())
                .filter(membershipLevel -> membershipLevel.toString().equalsIgnoreCase(membership))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
