package com.example.demo.src.service.user.recentlyviewproduct;

import com.example.demo.config.BaseException;
import com.example.demo.src.domain.dto.user.GetRecentlyViewProductRes;
import com.example.demo.src.repository.user.RecentlyViewProductDao;
import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RecentlyViewProductProvider {

    private final RecentlyViewProductDao recentlyViewProductDao;

    @Transactional(readOnly = true)
    public List<GetRecentlyViewProductRes> getRecentlyViewProduct(Long userIdx) throws BaseException {
        List<Map<String, Object>> recentlyViewProducts = recentlyViewProductDao.getRecentlyViewProduct(userIdx);
        List<GetRecentlyViewProductRes> getRecentlyViewProductResList = new ArrayList<>();
        for (Map<String, Object> recentlyViewProduct : recentlyViewProducts) {
            getRecentlyViewProductResList.add(new GetRecentlyViewProductRes(recentlyViewProduct));
        }
        return getRecentlyViewProductResList;
    }
}
