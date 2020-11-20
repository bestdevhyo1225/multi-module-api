package com.hyoseok.product.domain;

import org.springframework.data.repository.CrudRepository;

public interface ProductRedisRepository extends CrudRepository<ProductCache, String> {
}
