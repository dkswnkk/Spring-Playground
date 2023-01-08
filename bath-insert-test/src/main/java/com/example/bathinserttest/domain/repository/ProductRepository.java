package com.example.bathinserttest.domain.repository;

import com.example.bathinserttest.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
