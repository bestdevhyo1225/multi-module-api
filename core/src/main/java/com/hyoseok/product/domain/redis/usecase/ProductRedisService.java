package com.hyoseok.product.domain.redis.usecase;

import com.hyoseok.product.domain.redis.entity.ProductRedis;
import com.hyoseok.product.domain.redis.entity.ProductRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductRedisService {

    private final ProductRedisRepository productRedisRepository;

    public ProductRedis findProductRedis(String id) {
        return productRedisRepository.findById(id).orElse(null);
    }

    public void createOrUpdate(ProductRedis productRedis) {
        productRedisRepository.save(productRedis);
    }

}
