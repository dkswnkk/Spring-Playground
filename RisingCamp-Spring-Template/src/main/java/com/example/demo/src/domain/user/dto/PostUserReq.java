package com.example.demo.src.domain.user.dto;

import com.example.demo.src.domain.user.entitiy.PushNotificationAgreement;
import lombok.*;

@Data
public class PostUserReq {
    private String profileImage;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private PushNotificationAgreement pushNotificationAgreement;
}
