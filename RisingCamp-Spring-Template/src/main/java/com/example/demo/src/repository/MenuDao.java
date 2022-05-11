package com.example.demo.src.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
public class MenuDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Map<String, Object>> getMainMenu() {
        String getMainMenuQuery = "select * from MainMenu where status = true";
        return this.jdbcTemplate.queryForList(getMainMenuQuery);
    }

    public List<Map<String, Object>> getMainCategory(Long mainMenuIdx) {
        String getMainCategoryQuery = "select * from MainCategory where mainMenuIdx = ? AND status = true";
        return this.jdbcTemplate.queryForList(getMainCategoryQuery, mainMenuIdx);
    }

    public List<Map<String, Object>> getSubCategory(Long parentCategoryIdx, int depth) {
        String getSubCategoryQuery = "select * from SubCategory where depth = ? AND parentCategoryIdx = ? AND status = true";
        return this.jdbcTemplate.queryForList(getSubCategoryQuery, depth, parentCategoryIdx);
    }
}
