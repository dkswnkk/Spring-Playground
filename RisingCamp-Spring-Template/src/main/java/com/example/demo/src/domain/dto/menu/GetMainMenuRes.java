package com.example.demo.src.domain.dto.menu;


import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class GetMainMenuRes {
    private Long mainMenuIdx;
    private String title;
    private String url;

    public GetMainMenuRes(Map<String, Object> menu) {
        this.mainMenuIdx = (Long) menu.get("mainMenuIdx");
        this.title = (String) menu.get("title");
        this.url = (String) menu.get("imageUrl");
    }
}
