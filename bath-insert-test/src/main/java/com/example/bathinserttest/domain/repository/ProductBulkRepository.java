package com.example.bathinserttest.domain.repository;

import com.example.bathinserttest.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductBulkRepository {

    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void saveAll(List<Product> products) {
        String sql = "INSERT INTO product (title, price) " +
                "VALUES (?, ?)";

        jdbcTemplate.batchUpdate(sql,
                products,
                products.size(),
                (PreparedStatement ps, Product product) -> {
                    ps.setString(1, product.getTitle());
                    ps.setLong(2, product.getPrice());
                });
    }
}