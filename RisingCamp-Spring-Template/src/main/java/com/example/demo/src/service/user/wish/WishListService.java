package com.example.demo.src.service.user.wish;

import com.example.demo.config.BaseException;
import com.example.demo.src.repository.user.WishListDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WishListService {
    private final WishListDao wishListDao;

    @Transactional(readOnly = false)
    public void deleteWishList(Long userIdx, Long productIdx) throws BaseException {
        wishListDao.deleteWishList(userIdx, productIdx);
    }

    @Transactional(readOnly = false)
    public void insertWishList(Long userIdx, Long productIdx) throws BaseException {
        Boolean wishListCheck = wishListDao.checkWishList(userIdx, productIdx);
        if (!wishListCheck) {
            wishListDao.insertWishList(userIdx, productIdx);
        } else {
            wishListDao.updateWishList(userIdx, productIdx);
        }
    }
}
