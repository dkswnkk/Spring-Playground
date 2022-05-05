package com.example.demo.src.domain.dto;

import com.example.demo.src.domain.entitiy.PushNotificationAgreement;
import lombok.Data;

import java.util.List;

@Data
public class GetPushNotificationAgreementRes {
    private boolean orderNotification;
    private boolean restockNotification;
    private boolean reviewNotification;
    private boolean serviceCenterNotification;
    private boolean sellerShopNotification;
    private boolean adNotification;

    public GetPushNotificationAgreementRes(PushNotificationAgreement pushNotificationAgreement) {
        this.orderNotification = pushNotificationAgreement.isOrderNotification();
        this.restockNotification = pushNotificationAgreement.isRestockNotification();
        this.reviewNotification = pushNotificationAgreement.isReviewNotification();
        this.serviceCenterNotification = pushNotificationAgreement.isServiceCenterNotification();
        this.sellerShopNotification = pushNotificationAgreement.isSellerShopNotification();
        this.adNotification = pushNotificationAgreement.isAdNotification();
    }
}
