package com.example.demo.src.domain.dto.address;

import lombok.Getter;

@Getter
public class PatchAddressRes {
    private final Long addressIdx;
    private final String name;
    private final String phoneNumber;
    private final String city;
    private final String street;
    private final String detail;
    private final String zipcode;
    private final String basicTimeInfo;
    private final String basicHousePassword;
    private final String dawnTimeInfo;
    private final String dawnTimePassword;
    private final Boolean isDefault;

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
