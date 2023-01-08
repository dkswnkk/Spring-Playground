package com.example.bathinserttest.domain;

import com.example.bathinserttest.domain.repository.ProductBulkRepository;
import com.example.bathinserttest.domain.repository.ProductRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class ProductTest {

    private static final int COUNT = 10_000;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductBulkRepository productBulkRepository;

    @BeforeAll
    void init() {
        Product product = new Product("초기", 10_000L);
        productRepository.save(product);
    }

    @Test
    @DisplayName("normal insert")
    void 일반_insert() {
        for (long i = 2; i <= COUNT; i++) {
            String title = "이름: " + i;
            long price = i + 1L;
            Product product = new Product(title, price);
            productRepository.save(product);
        }
    }

    @Test
    @DisplayName("bulk insert")
    void 벌크_insert() {
        List<Product> products = new ArrayList<>();
        for (long i = 0; i < COUNT; i++) {
            String title = "이름: " + i;
            long price = i + 1L;
            Product product = new Product(title, price);
            products.add(product);
        }
        productBulkRepository.saveAll(products);
    }

}