package com.example.demo.src.service.user.wish;

import com.example.demo.config.BaseException;
import com.example.demo.src.domain.dto.user.wish.GetWishListRes;
import com.example.demo.src.domain.entitiy.user.Address;
import com.example.demo.src.repository.user.WishListDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class WishListProvider {

    private final WishListDao wishListDao;

    @Transactional(readOnly = true)
    public List<GetWishListRes> getWishList(Long userIdx) throws BaseException {
        List<Map<String, Object>> wishList = wishListDao.getWishList(userIdx);
        List<GetWishListRes> getWishListRes = new ArrayList<>();
        for (Map<String, Object> wish : wishList) {
            getWishListRes.add(new GetWishListRes(wish));
        }
        return getWishListRes;
    }
}
