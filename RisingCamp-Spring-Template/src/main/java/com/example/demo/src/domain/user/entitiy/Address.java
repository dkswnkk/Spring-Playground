package com.example.demo.src.domain.user.entitiy;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class Address {
    private String name;
    private String phoneNumber;
    private String city;
    private String street;
    private String detail;
    private String zipcode;
    private String basicTimeInfo;
    private String basicHousePassword;
    private String dawnTimeInfo;
    private String dawnTimePassword;
    private Boolean isDefault;

}

