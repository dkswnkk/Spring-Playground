package com.example.demo.src.domain.dto.address;


import lombok.Data;

@Data
public class PostAddressReq {
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
