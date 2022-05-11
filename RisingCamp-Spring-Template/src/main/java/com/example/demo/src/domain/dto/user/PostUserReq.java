package com.example.demo.src.domain.dto.user;

import com.example.demo.src.domain.entitiy.user.PushNotificationAgreement;
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
