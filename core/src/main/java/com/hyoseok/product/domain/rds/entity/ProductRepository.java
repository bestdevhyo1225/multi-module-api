package com.hyoseok.product.domain.rds.entity;

import com.hyoseok.product.domain.rds.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
