package com.example.demo.src.service.product;

import com.example.demo.config.BaseException;
import com.example.demo.src.domain.dto.product.GetMainCategoryProductRes;
import com.example.demo.src.domain.dto.product.GetMainMenuProductRes;
import com.example.demo.src.domain.dto.product.GetSubCategoryProductRes;
import com.example.demo.src.repository.product.ProductDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductProvider {

    private final ProductDao productDao;

    @Transactional(readOnly = true)
    public List<GetMainMenuProductRes> getMainMenuProduct(Long mainMenuIdx) throws BaseException {
        List<GetMainMenuProductRes> getMainMenuProductRes = new ArrayList<>();
        List<Map<String, Object>> getProduct = productDao.getMainMenuProductList(mainMenuIdx);
        for (Map<String, Object> product : getProduct) {
            Map<String, Object> review = productDao.getPreviewProductReview((Long) product.get("productIdx"));
            getMainMenuProductRes.add(new GetMainMenuProductRes(product, review));
        }
        return getMainMenuProductRes;

    }

    @Transactional(readOnly = true)
    public List<GetMainCategoryProductRes> getMainCategoryProductRes(Long mainMenuIdx, Long mainCategoryIdx) throws BaseException {

        List<GetMainCategoryProductRes> getMainCategoryProductRes = new ArrayList<>();
        List<Map<String, Object>> getProduct = productDao.getMainCategoryProductList(mainMenuIdx, mainCategoryIdx);

        for (Map<String, Object> product : getProduct) {
            Map<String, Object> review = productDao.getPreviewProductReview((Long) product.get("productIdx"));
            getMainCategoryProductRes.add(new GetMainCategoryProductRes(product, review));
        }
        return getMainCategoryProductRes;
    }

    @Transactional(readOnly = true)
    public List<GetSubCategoryProductRes> getSubCategoryProductRes(Long mainMenuIdx, Long mainCategoryIdx, Long subCategoryIdx) throws BaseException {
        List<GetSubCategoryProductRes> getSubCategoryProductRes = new ArrayList<>();
        List<Map<String, Object>> getProduct = productDao.getSubCategoryProductList(mainMenuIdx, mainCategoryIdx, subCategoryIdx);

        for (Map<String, Object> product : getProduct) {
            Map<String, Object> review = productDao.getPreviewProductReview((Long) product.get("productIdx"));
            getSubCategoryProductRes.add(new GetSubCategoryProductRes(product, review));
        }
        return getSubCategoryProductRes;

    }
}
