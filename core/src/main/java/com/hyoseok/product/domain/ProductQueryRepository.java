package com.hyoseok.product.domain;

import java.util.List;
import java.util.Optional;

public interface ProductQueryRepository {
    List<Product> findAllWithFetchJoin();
    Optional<Product> findWithFetchJoinById(Long id);
}
