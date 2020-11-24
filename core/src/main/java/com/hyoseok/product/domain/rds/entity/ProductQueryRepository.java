package com.hyoseok.product.domain.rds.entity;

import com.hyoseok.product.domain.rds.entity.Product;
import com.hyoseok.product.domain.rds.usecase.dto.ProductPagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductQueryRepository {
    List<Product> findAllWithFetchJoin();
    Optional<Product> findWithFetchJoinById(Long id);
    Page<ProductPagination> paginationCount(Pageable pageable);
    Page<ProductPagination> paginationCountSearchBtn(boolean useSearchBtn, Pageable pageable);
}
