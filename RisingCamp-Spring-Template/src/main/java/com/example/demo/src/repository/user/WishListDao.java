package com.example.demo.src.repository.user;

import com.example.demo.src.domain.dto.user.PostUserReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Slf4j
@RequiredArgsConstructor
public class WishListDao {
    private final JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> getWishList(Long userIdx) {
        String getWishListQuery = "select distinct P.productIdx," +
                "                P.title," +
                "                PI.imageUrl," +
                "                PO.price," +
                "                PO.`option` as options," +
                "                PO.stock" +
                " from User U" +
                "         join WishList WL on U.userIdx = WL.userIdx" +
                "         join Product P on P.productIdx = WL.productIdx" +
                "         join ProductImage PI on P.productIdx = PI.productIdx" +
                "         join ProductOption PO on P.productIdx = PO.productIdx" +
                " where U.status = true" +
                "  AND WL.status = true" +
                "  AND P.status = true" +
                "  AND PI.thumbnail = true" +
                "  AND PO.defaultStatus = true" +
                "  AND U.userIdx = ?" +
                " order by WL.updateAt desc";
        return this.jdbcTemplate.queryForList(getWishListQuery, userIdx);
    }

    public void deleteWishList(Long userIdx, Long productIdx) {
        String deleteWishListQuery = "update WishList WL join User U on U.userIdx = WL.userIdx" +
                " set WL.status = false" +
                " where U.status = true" +
                "  AND U.userIdx = ?" +
                " AND WL.productIdx = ?";
        this.jdbcTemplate.update(deleteWishListQuery, userIdx, productIdx);
    }

    public void insertWishList(Long userIdx, Long productIdx) {
        String insertWishListQuery = String.format("insert into WishList (userIdx, productIdx) VALUES (%d, %d)", userIdx, productIdx);
        this.jdbcTemplate.update(insertWishListQuery);
    }

    public void updateWishList(Long userIdx, Long productIdx) {
        String insertWishListQuery = "update WishList WL set WL.status = true where productIdx = ? AND userIdx = ?";
        this.jdbcTemplate.update(insertWishListQuery, productIdx, userIdx);
    }

    public Boolean checkWishList(Long userIdx, Long productIdx) {
        String checkWishListQuery = "select EXISTS(select WL.status from WishList WL where userIdx = ? AND productIdx = ?);";
        return this.jdbcTemplate.queryForObject(checkWishListQuery, Boolean.class, userIdx, productIdx);
    }

}
