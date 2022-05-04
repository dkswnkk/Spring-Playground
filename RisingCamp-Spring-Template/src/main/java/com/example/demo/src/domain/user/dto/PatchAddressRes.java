package com.example.demo.src.domain.user.dto;

import com.example.demo.src.domain.user.entitiy.Address;
import lombok.Getter;

@Getter
public class PatchAddressRes {
    private int addressIdx;
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

    public PatchAddressRes(PatchAddressReq address) {
        this.addressIdx = address.getAddressIdx();
        this.name = address.getName();
        this.phoneNumber = address.getPhoneNumber();
        this.city = address.getCity();
        this.street = address.getStreet();
        this.detail = address.getDetail();
        this.zipcode = address.getZipcode();
        this.basicTimeInfo = address.getBasicTimeInfo();
        this.basicHousePassword = address.getBasicHousePassword();
        this.dawnTimeInfo = address.getDawnTimeInfo();
        this.dawnTimePassword = address.getDawnTimePassword();
        this.isDefault = address.getIsDefault();
    }
}
