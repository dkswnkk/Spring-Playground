package com.example.demo.src.repository.user;

import com.example.demo.src.domain.dto.user.PostUserReq;
import com.example.demo.src.domain.entitiy.user.PushNotificationAgreement;
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

    public List<PushNotificationAgreement> getAgreement(Long userIdx) {
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
    public void insertPushNotificationAgreement(Long userIdx, PostUserReq postUserReq) {
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
        this.jdbcTemplate.update(insertPushNotificationAgreementQuery, insertPushNotificationAgreementParams);
    }

//    public PushNotificationAgreement getPushNotification(Long userIdx) {
//        String getPushOrderNotificationQuery = "select * from PushNotificationAgreement where status = true AND userIdx = ?";
//        return this.jdbcTemplate.queryForObject(getPushOrderNotificationQuery,
//                (rs, rowNum) -> new PushNotificationAgreement(
//                        rs.getBoolean("orderNotification"),
//                        rs.getBoolean("restockNotification"),
//                        rs.getBoolean("reviewNotification"),
//                        rs.getBoolean("serviceCenterNotification"),
//                        rs.getBoolean("sellerShopNotification"),
//                        rs.getBoolean("adNotification")
//                ), // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
//                userIdx); // 한 개의 회원정보를 얻기 위한 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환
//    }


    public void updatePushNotification(Long userIdx, String notificationName) {
        String updatePushOrderNotificationQuery = String.format("update PushNotificationAgreement set %s = !%s where status = true AND userIdx = %d", notificationName, notificationName, userIdx);
        this.jdbcTemplate.update(updatePushOrderNotificationQuery);
    }
}

