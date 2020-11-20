package com.hyoseok.product.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("ProductRedisRepository 테스트")
class ProductRedisRepositoryTest {

    @Autowired
    private ProductRedisRepository productRedisRepository;

    @AfterEach
    void tearDown() {
        productRedisRepository.deleteAll();
    }

    @Test
    @DisplayName("등록 및 조회")
    void saveAndFind() {
        // given
        RedisProduct redisProduct = RedisProduct.builder()
                .id("1")
                .isSale(true)
                .isUsed(true)
                .supplierId(1)
                .supplyPrice(20000.0000)
                .recommendPrice(33000.0000)
                .consumerPrice(33000.0000)
                .maximum(10)
                .minimum(1)
                .refreshDatetime(LocalDateTime.now())
                .build();

        // when
        productRedisRepository.save(redisProduct);
        RedisProduct findRedisProduct = productRedisRepository.findById(redisProduct.getId())
                .orElseThrow(() -> new NoSuchElementException("찾을 수 없음"));

        // then
        assertThat(findRedisProduct.getId()).isEqualTo(redisProduct.getId());
        assertThat(findRedisProduct.getIsSale()).isEqualTo(redisProduct.getIsSale());
        assertThat(findRedisProduct.getIsSale()).isEqualTo(redisProduct.getIsUsed());
        assertThat(findRedisProduct.getSupplierId()).isEqualTo(redisProduct.getSupplierId());
        assertThat(findRedisProduct.getSupplyPrice()).isEqualTo(redisProduct.getSupplyPrice());
        assertThat(findRedisProduct.getRecommendPrice()).isEqualTo(redisProduct.getRecommendPrice());
        assertThat(findRedisProduct.getConsumerPrice()).isEqualTo(redisProduct.getConsumerPrice());
        assertThat(findRedisProduct.getMaximum()).isEqualTo(redisProduct.getMaximum());
        assertThat(findRedisProduct.getMinimum()).isEqualTo(redisProduct.getMinimum());
        assertThat(findRedisProduct.getRefreshDatetime()).isEqualTo(redisProduct.getRefreshDatetime());

        System.out.println("findRedisProduct.getProductDescriptionText() = " + findRedisProduct.getProductDescriptionText());
        System.out.println("findRedisProduct.getProductDescriptionVarchar() = " + findRedisProduct.getProductDescriptionVarchar());
        System.out.println("findRedisProduct.getProductImages() = " + findRedisProduct.getProductImages());
    }

    @Test
    @DisplayName("수정")
    void update() {
        // given
        String id = "1";
        productRedisRepository.save(
                RedisProduct.builder()
                        .id(id)
                        .isSale(true)
                        .isUsed(true)
                        .supplierId(1)
                        .supplyPrice(20000.0000)
                        .recommendPrice(33000.0000)
                        .consumerPrice(33000.0000)
                        .maximum(10)
                        .minimum(1)
                        .refreshDatetime(LocalDateTime.now())
                        .build()
        );

        // when
        RedisProduct findRedisProduct = productRedisRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("찾을 수 없음"));

        int supplierId = 1;
        double supplyPrice = 20000.0000;
        double recommendPrice = 33000.0000;
        double consumerPrice = 33000.0000;
        int maximum = 10;
        int minimum = 1;
        LocalDateTime refreshDatetime = LocalDateTime.of(2020, 11, 22, 13, 0, 0);
        findRedisProduct.refresh(
                true,
                true,
                supplierId,
                supplyPrice,
                recommendPrice,
                consumerPrice,
                maximum,
                minimum,
                refreshDatetime
        );

        productRedisRepository.save(findRedisProduct);

        RedisProduct findRefreshRedisProduct = productRedisRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("찾을 수 없음"));

        // then
        assertThat(findRefreshRedisProduct.getIsSale()).isTrue();
        assertThat(findRefreshRedisProduct.getIsSale()).isTrue();
        assertThat(findRefreshRedisProduct.getSupplierId()).isEqualTo(supplierId);
        assertThat(findRefreshRedisProduct.getSupplyPrice()).isEqualTo(supplyPrice);
        assertThat(findRefreshRedisProduct.getRecommendPrice()).isEqualTo(recommendPrice);
        assertThat(findRefreshRedisProduct.getConsumerPrice()).isEqualTo(consumerPrice);
        assertThat(findRefreshRedisProduct.getMaximum()).isEqualTo(maximum);
        assertThat(findRefreshRedisProduct.getMinimum()).isEqualTo(minimum);
        assertThat(findRefreshRedisProduct.getRefreshDatetime()).isEqualTo(refreshDatetime);
    }

}
