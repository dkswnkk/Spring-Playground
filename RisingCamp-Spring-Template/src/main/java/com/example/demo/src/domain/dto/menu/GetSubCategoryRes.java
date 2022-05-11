package com.example.demo.src.domain.dto.menu;

import lombok.Data;

import java.util.List;
import java.util.Map;
@Data
public class GetSubCategoryRes {
    private boolean isExist;
    private int count;
    private List<SubCategoryDto> subCategoryDtoList;

    public GetSubCategoryRes(List<SubCategoryDto> subCategoryDtoList) {
        this.isExist = !subCategoryDtoList.isEmpty();
        this.count = subCategoryDtoList.size();
        this.subCategoryDtoList = subCategoryDtoList;
    }

}
