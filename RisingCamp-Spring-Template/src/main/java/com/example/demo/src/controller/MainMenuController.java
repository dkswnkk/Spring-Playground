package com.example.demo.src.controller;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.domain.dto.menu.GetMainCategoryRes;
import com.example.demo.src.domain.dto.menu.GetMainMenuRes;
import com.example.demo.src.service.MenuProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/app")
@Slf4j
@RequiredArgsConstructor
public class MainMenuController {

    private final MenuProvider menuProvider;

    @GetMapping("/menu")
    public BaseResponse<List<GetMainMenuRes>> getMainMenu() {
        try {
            List<GetMainMenuRes> getMainMenuRes = menuProvider.getMainMenu();
            return new BaseResponse<>(getMainMenuRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("/menu/{mainMenuIdx}")
    public BaseResponse<List<GetMainCategoryRes>> getMainCategory(@PathVariable Long mainMenuIdx) {
        try {
            List<GetMainCategoryRes> getMainCategoryRes = menuProvider.getMainCategory(mainMenuIdx);
            return new BaseResponse<>(getMainCategoryRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
