package com.example.demo.src.repository;

import com.example.demo.src.domain.entitiy.product.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Date;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
public class ProductDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Map<String, Object>> getSubCategoryProductList(Long mainMenuIdx, Long mainCategoryIdx, Long subCategoryIdx) {
        String getSubCategoryProductListSQL = "select" +
                " P.productIdx," +
                " PI.imageUrl," +
                " P.title," +
                " PO.`option` as options," +
                " PO.detail," +
                " PO.deliveryStatus," +
                " PO.price," +
                " current_date + PO.expectedDelivery as expectedDelivery" +
                " from Product P" +
                " join ProductImage PI on P.productIdx = PI.productIdx" +
                " join ProductOption PO on P.productIdx = PO.productIdx" +
                " join CategoryProduct CP on P.productIdx = CP.productIdx" +
                " where PI.thumbnail = true" +
                " AND PI.status = true" +
                " AND PO.defaultStatus = true" +
                " AND PO.status = true" +
                " AND CP.mainMenuIdx = ?" +
                " AND CP.mainCategoryIdx = ?" +
                " AND CP.subCategoryIdx = ?";

        return this.jdbcTemplate.queryForList(getSubCategoryProductListSQL, mainMenuIdx, mainCategoryIdx, subCategoryIdx);
    }


    public List<Map<String, Object>> getMainCategoryProductList(Long mainMenuIdx, Long mainCategoryIdx) {
        String getMainMenuProductListQuery = "select" +
                " P.productIdx," +
                " PI.imageUrl," +
                " P.title," +
                " PO.`option` as options," +
                " PO.detail," +
                " PO.deliveryStatus," +
                " PO.price," +
                " current_date + PO.expectedDelivery as expectedDelivery" +
                " from Product P" +
                " join ProductImage PI on P.productIdx = PI.productIdx" +
                " join ProductOption PO on P.productIdx = PO.productIdx" +
                " join CategoryProduct CP on P.productIdx = CP.productIdx" +
                " where PI.thumbnail = true" +
                " AND PI.status = true" +
                " AND PO.defaultStatus = true" +
                " AND PO.status = true" +
                " AND CP.mainMenuIdx = ?" +
                " AND CP.mainCategoryIdx = ?";

        return this.jdbcTemplate.queryForList(getMainMenuProductListQuery, mainMenuIdx, mainCategoryIdx);
    }

    public List<Map<String, Object>> getMainMenuProductList(Long mainMenuIdx) {
        String getMainMenuProductListQuery = "select" +
                " P.productIdx," +
                " PI.imageUrl," +
                " P.title," +
                " PO.`option` as options," +
                " PO.detail," +
                " PO.deliveryStatus," +
                " PO.price," +
                " current_date + PO.expectedDelivery as expectedDelivery" +
                " from Product P" +
                " join ProductImage PI on P.productIdx = PI.productIdx" +
                " join ProductOption PO on P.productIdx = PO.productIdx" +
                " join CategoryProduct CP on P.productIdx = CP.productIdx" +
                " where PI.thumbnail = true" +
                " AND PI.status = true" +
                " AND PO.defaultStatus = true" +
                " AND PO.status = true" +
                " AND CP.mainMenuIdx = ?";

        return this.jdbcTemplate.queryForList(getMainMenuProductListQuery, mainMenuIdx);
    }


    public List<Map<String, Object>> getMainMenu() {
        String getMainMenuQuery = "select * from MainMenu where status = true";
        return this.jdbcTemplate.queryForList(getMainMenuQuery);
    }

    public Map<String, Object> getPreviewProductReview(Long productIdx) {
        String getPreviewProductReviewQuery = "select" +
                " count(reviewIdx) as reviewCnt, ifnull(sum(rate) / count(reviewIdx), 0.0) as reviewAvg" +
                " from Product P" +
                " join Review R on P.productIdx = R.productIdx" +
                " where R.status = true" +
                " AND P.status = true" +
                " And P.productIdx = ? ";
        return this.jdbcTemplate.queryForMap(getPreviewProductReviewQuery, productIdx);
    }




    /*
     return this.jdbcTemplate.queryForObject(getPwdQuery,
                (rs, rowNum) -> new User(
                        rs.getLong("U.userIdx"),
                        rs.getString("U.profileImage"),
                        rs.getString("U.email"),
                        rs.getString("U.name"),
                        rs.getString("U.password"),
                        rs.getString("U.phoneNumber"),
                        MembershipLevel.of(rs.getString("U.membershipLevel")),
                        rs.getInt("U.coupay"),
                        rs.getInt("U.coupangCash"),
                        rs.getBoolean("U.status")
                ),
                getPwdParams
        ); // 한 개의 회원정보
     */

}

