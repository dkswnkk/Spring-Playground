package com.example.demo.src.domain.user.user;


import com.example.demo.src.domain.user.common.Address;
import com.example.demo.src.domain.user.common.MembershipLevel;
import com.example.demo.src.domain.user.common.User;
import lombok.*;

import java.util.List;


/**
 * Res.java: From Server To Client
 * 하나 또는 복수개의 회원정보 조회 요청(Get Request)의 결과(Respone)를 보여주는 데이터의 형태
 * <p>
 * GetUserRes는 클라이언트한테 response줄 때 DTO고
 * User 클래스는 스프링에서 사용하는 Objec이다.
 */
@Data
public class GetUserRes {
    private int userIdx;
    private String profileImage;
    private String email;
    private String name;
    private String phoneNumber;
    private MembershipLevel membershipLevel;
    private int coupay;
    private int coupangCash;
    private List<Address> addressList;

    public GetUserRes(User user, List<Address> address) {
        this.userIdx = user.getUserIdx();
        this.profileImage = user.getProfileImage();
        this.email = user.getEmail();
        this.name = user.getName();
        this.phoneNumber = user.getName();
        this.membershipLevel = user.getMembershipLevel();
        this.coupay = user.getCoupay();
        this.coupangCash = user.getCoupangCash();
        this.addressList = address;
    }
}
