package com.example.demo.src.repository;

import com.example.demo.src.domain.dto.PostAddressReq;
import com.example.demo.src.domain.dto.PostUserReq;
import com.example.demo.src.domain.entitiy.PushNotificationAgreement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Slf4j
public class PushNotificationAgreementDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int insertAddress(int userIdx, PostAddressReq postAddressReq) {
        String insertAddressQuery = "insert into Address(userIdx, name, phoneNumber, city, street, detail, zipcode, defaultAddress)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Object[] insertAddressParams = {userIdx,
                postAddressReq.getName(),
                postAddressReq.getPhoneNumber(),
                postAddressReq.getCity(),
                postAddressReq.getStreet(),
                postAddressReq.getDetail(),
                postAddressReq.getZipcode(),
                postAddressReq.getIsDefault()
        };
        return this.jdbcTemplate.update(insertAddressQuery, insertAddressParams);
    }

    public List<PushNotificationAgreement> getAgreement(int userIdx) {
        String getAgreementQuery = "select * from PushNotificationAgreement where userIdx = ?";
        return this.jdbcTemplate.query(getAgreementQuery,
                (rs, rowNum) -> new PushNotificationAgreement(
                        rs.getBoolean("orderNotification"),
                        rs.getBoolean("restockNotification"),
                        rs.getBoolean("reviewNotification"),
                        rs.getBoolean("serviceCenterNotification"),
                        rs.getBoolean("sellerShopNotification"),
                        rs.getBoolean("adNotification")
                ),
                userIdx
        );
    }

    // 푸쉬알림 여부 등록
    public int insertPushNotificationAgreement(int userIdx, PostUserReq postUserReq) {
        String insertPushNotificationAgreementQuery = "insert into PushNotificationAgreement (userIdx, orderNotification, restockNotification, reviewNotification, serviceCenterNotification, sellerShopNotification, adNotification) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Object[] insertPushNotificationAgreementParams = new Object[]{
                userIdx,
                postUserReq.getPushNotificationAgreement().isOrderNotification(),
                postUserReq.getPushNotificationAgreement().isRestockNotification(),
                postUserReq.getPushNotificationAgreement().isReviewNotification(),
                postUserReq.getPushNotificationAgreement().isServiceCenterNotification(),
                postUserReq.getPushNotificationAgreement().isSellerShopNotification(),
                postUserReq.getPushNotificationAgreement().isServiceCenterNotification()
        };
        return this.jdbcTemplate.update(insertPushNotificationAgreementQuery, insertPushNotificationAgreementParams);
    }
}
