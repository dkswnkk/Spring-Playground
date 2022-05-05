package com.example.demo.src.controller;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.domain.dto.GetMainMenuRes;
import com.example.demo.src.domain.dto.GetUserRes;
import com.example.demo.src.domain.entitiy.Address;
import com.example.demo.src.domain.entitiy.MainMenu;
import com.example.demo.src.domain.entitiy.PushNotificationAgreement;
import com.example.demo.src.domain.entitiy.User;
import com.example.demo.src.service.MainMenuProvider;
import jdk.tools.jmod.Main;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/app/mainMenus")
@Slf4j
@RequiredArgsConstructor
public class MainMenuController {

    private final MainMenuProvider mainMenuProvider;

    @GetMapping("")
    public BaseResponse<List<MainMenu>> getMainMenu() {
        try {
            List<MainMenu> getMainMenuRes = mainMenuProvider.getMainMenu();
            return new BaseResponse<>(getMainMenuRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
