package com.example.demo.src.service;


import com.example.demo.config.BaseException;
import com.example.demo.src.domain.dto.menu.GetMainCategoryRes;
import com.example.demo.src.domain.dto.menu.GetMainMenuRes;
import com.example.demo.src.domain.entitiy.MainMenu;
import com.example.demo.src.repository.MenuDao;
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
            for (Map<String, Object> cagegory : getMainCategories) {
                mainCategories.add(new GetMainCategoryRes(cagegory));
            }
            return mainCategories;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


}
