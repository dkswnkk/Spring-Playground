package com.example.demo.src.domain.dto;

import com.example.demo.src.domain.entitiy.MainMenu;
import lombok.Data;

import java.util.List;

@Data
public class GetMainMenuRes {
    private int mainMenuIdx;
    private String title;
    private String url;
}
