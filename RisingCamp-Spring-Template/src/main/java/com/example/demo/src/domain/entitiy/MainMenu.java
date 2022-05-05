package com.example.demo.src.domain.entitiy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;


@Getter
@Slf4j
@AllArgsConstructor
public class MainMenu {
    private int mainMenuIdx;
    private String title;
    private String url;

    public MainMenu(Map<String, Object> menu) {
        log.debug(menu.toString());
        this.mainMenuIdx = Integer.parseInt(String.valueOf(menu.get("mainMenuIdx")));
        this.title = (String) menu.get("title");
        this.url = (String) menu.get("imageUrl");
    }
}
