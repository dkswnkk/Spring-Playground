package com.example.demo.src.domain.user.entitiy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PushNotificationAgreement {
    private boolean orderNotification;
    private boolean restockNotification;
    private boolean reviewNotification;
    private boolean serviceCenterNotification;
    private boolean sellerShopNotification;
    private boolean adNotification;
}
