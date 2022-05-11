package com.example.demo.src.controller;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.domain.dto.menu.GetSubCategoryRes;
import com.example.demo.src.domain.dto.product.GetMainCategoryProductRes;
import com.example.demo.src.domain.dto.product.GetMainMenuProductRes;
import com.example.demo.src.service.MenuProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/product")
public class ProductController {

    private final MenuProvider menuProvider;

    @GetMapping("/{mainMenuIdx}")
    public BaseResponse<List<GetMainMenuProductRes>> getMainMenuProduct(@PathVariable Long mainMenuIdx) {
        try {
            List<GetMainMenuProductRes> getMainMenuProductRes = menuProvider.getMainMenuProduct(mainMenuIdx);
            return new BaseResponse<>(getMainMenuProductRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("/{mainMenuIdx}/{mainCategoryIdx}")
    public BaseResponse<List<GetMainCategoryProductRes>> getMainCategoryProduct(@PathVariable Long mainMenuIdx, @PathVariable Long mainCategoryIdx) {
        try {
            List<GetMainCategoryProductRes> getMainCategoryProductRes = menuProvider.getMainCategoryProductRes(mainMenuIdx, mainCategoryIdx);
            return new BaseResponse<>(getMainCategoryProductRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

}
