package com.example.demo.src.domain.entitiy.menu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;


@Getter
@AllArgsConstructor
public class MainMenu {
    private Long mainMenuIdx;
    private String title;
    private String url;

    public MainMenu(Map<String, Object> menu) {
        this.mainMenuIdx = (Long) menu.get("mainMenuIdx");
        this.title = (String) menu.get("title");
        this.url = (String) menu.get("imageUrl");
    }
}
