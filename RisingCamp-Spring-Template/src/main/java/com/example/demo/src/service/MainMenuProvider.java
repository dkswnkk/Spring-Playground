package com.example.demo.src.service;


import com.example.demo.config.BaseException;
import com.example.demo.src.domain.entitiy.MainMenu;
import com.example.demo.src.repository.MainDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@Slf4j
@RequiredArgsConstructor
public class MainMenuProvider {

    private final MainDao mainDao;

    public List<MainMenu> getMainMenu() throws BaseException {
        try {
            List<MainMenu> mainMenus = new ArrayList<>();
            List<Map<String, Object>> getMainMenu = mainDao.getMainMenu();
            for (Map<String, Object> menu : getMainMenu) {
                mainMenus.add(new MainMenu(menu));
            }
            return mainMenus;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


}
