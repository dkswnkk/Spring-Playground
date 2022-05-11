package com.example.demo.src.service;


import com.example.demo.config.BaseException;
import com.example.demo.src.domain.dto.menu.GetMainCategoryRes;
import com.example.demo.src.domain.dto.menu.GetMainMenuRes;
import com.example.demo.src.domain.dto.menu.GetSubCategoryRes;
import com.example.demo.src.domain.dto.menu.SubCategoryDto;
import com.example.demo.src.domain.dto.product.GetMainCategoryProductRes;
import com.example.demo.src.domain.dto.product.GetMainMenuProductRes;
import com.example.demo.src.domain.entitiy.product.Product;
import com.example.demo.src.repository.MenuDao;
import com.example.demo.src.repository.ProductDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@Slf4j
@RequiredArgsConstructor
public class MenuProvider {

    private final MenuDao mainDao;
    private final ProductDao productDao;

    @Transactional(readOnly = true)
    public List<GetMainMenuRes> getMainMenu() throws BaseException {
        try {
            List<GetMainMenuRes> mainMenus = new ArrayList<>();
            List<Map<String, Object>> getMainMenu = mainDao.getMainMenu();
            for (Map<String, Object> menu : getMainMenu) {
                mainMenus.add(new GetMainMenuRes(menu));
            }
            return mainMenus;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public List<GetMainCategoryRes> getMainCategory(Long mainMenuIdx) throws BaseException {
        try {
            List<GetMainCategoryRes> mainCategories = new ArrayList<>();
            List<Map<String, Object>> getMainCategories = mainDao.getMainCategory(mainMenuIdx);
            for (Map<String, Object> cagegory : getMainCategories) {
                mainCategories.add(new GetMainCategoryRes(cagegory));
            }
            return mainCategories;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public GetSubCategoryRes getSubCategory(Long parentCategoryIdx, int depth) throws BaseException {
        try {
            List<SubCategoryDto> subCategories = new ArrayList<>();
            List<Map<String, Object>> getSubCategories = mainDao.getSubCategory(parentCategoryIdx, depth);
            for (Map<String, Object> cagegory : getSubCategories) {
                subCategories.add(new SubCategoryDto(cagegory));
            }
            return new GetSubCategoryRes(subCategories);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public List<GetMainMenuProductRes> getMainMenuProduct(Long mainMenuIdx) throws BaseException {
        try {
            List<GetMainMenuProductRes> getMainMenuProductRes = new ArrayList<>();
            List<Map<String, Object>> getProduct = productDao.getMainMenuProductList(mainMenuIdx);

            for (Map<String, Object> product : getProduct) {
                Map<String, Object> review = productDao.getPreviewProductReview((Long) product.get("productIdx"));
                getMainMenuProductRes.add(new GetMainMenuProductRes(product, review));
            }
            return getMainMenuProductRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public List<GetMainCategoryProductRes> getMainCategoryProductRes(Long mainMenuIdx, Long mainCategoryIdx) throws BaseException {
        try {
            List<GetMainCategoryProductRes> getMainCategoryProductRes = new ArrayList<>();
            List<Map<String, Object>> getProduct = productDao.getMainCategoryProductList(mainMenuIdx, mainCategoryIdx);

            for (Map<String, Object> product : getProduct) {
                Map<String, Object> review = productDao.getPreviewProductReview((Long) product.get("productIdx"));
                getMainCategoryProductRes.add(new GetMainCategoryProductRes(product, review));
            }
            return getMainCategoryProductRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


}
