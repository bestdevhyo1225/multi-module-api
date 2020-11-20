package com.hyoseok.product.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

        // when

        // t
    }

    @Test
    @DisplayName("수정")
    void update() {

    }

}
