package com.example.demo.src.repository.user;

import com.example.demo.src.domain.entitiy.user.PushNotificationAgreement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
public class RecentlyViewProductDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired //readme 참고
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /*
    count(reviewIdx) as reviewCnt, ifnull(sum(rate) / count(reviewIdx), 0.0) as reviewAvg
     */
    public List<Map<String, Object>> getRecentlyViewProduct(Long userIdx) {
        String getRecentlyViewProductQuery = "select distinct" +
                " P.productIdx, " +
                " P.title," +
                " PI.imageUrl," +
                " PO.deliveryStatus," +
                " PO.price," +
                " PO.`option` as options," +
                " PO.detail," +
                " ifnull(sum(rate) / count(reviewIdx), 0.0) as reviewAvg," +
                " count(R.productIdx) as reviewCount" +
                " from RecentlyViewProduct RVP" +
                " join User U on RVP.userIdx = U.userIdx" +
                " join Product P on RVP.productIdx = P.productIdx" +
                " join ProductImage PI on P.productIdx = PI.productIdx" +
                " join ProductOption PO on P.productIdx = PO.productIdx" +
                " join Review R on P.productIdx = R.productIdx" +
                " where U.status = true" +
                "  AND RVP.status = true" +
                "  AND P.status = true" +
                "  AND PI.thumbnail = true" +
                "  AND PO.defaultStatus = true" +
                "  AND U.userIdx = ?" +
                " order by RVP.updateAt desc";

        return this.jdbcTemplate.queryForList(getRecentlyViewProductQuery, userIdx);
    }







    /*
    select distinct  *
from RecentlyViewProduct RVP
         join User U on RVP.userIdx = U.userIdx
         join Product P on RVP.productIdx = P.productIdx
where U.status = true
  AND RVP.status = true
  AND P.status = true
  AND U.userIdx = 1
order by RVP.updateAt desc;
     */

}
