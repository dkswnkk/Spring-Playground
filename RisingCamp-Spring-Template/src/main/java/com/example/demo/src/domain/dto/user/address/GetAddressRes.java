package com.example.demo.src.domain.dto.user.address;

import com.example.demo.src.domain.entitiy.user.Address;
import lombok.Getter;

@Getter
public class GetAddressRes {
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

    public GetAddressRes(Address address) {
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

