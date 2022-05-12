package com.example.demo.src.service.menu;


import com.example.demo.config.BaseException;
import com.example.demo.src.domain.dto.menu.GetMainCategoryRes;
import com.example.demo.src.domain.dto.menu.GetMainMenuRes;
import com.example.demo.src.domain.dto.menu.GetSubCategoryRes;
import com.example.demo.src.domain.dto.menu.SubCategoryDto;
import com.example.demo.src.repository.menu.MenuDao;
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
            for (Map<String, Object> category : getMainCategories) {
                mainCategories.add(new GetMainCategoryRes(category));
            }
            return mainCategories;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public GetSubCategoryRes getSubCategory(Long mainCategoryIdx, int depth) throws BaseException {
        try {
            List<SubCategoryDto> subCategories = new ArrayList<>();
            List<Map<String, Object>> getSubCategories = mainDao.getSubCategory(mainCategoryIdx, depth);
            for (Map<String, Object> category : getSubCategories) {
                subCategories.add(new SubCategoryDto(category));
            }
            return new GetSubCategoryRes(subCategories);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
