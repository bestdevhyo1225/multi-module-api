package com.hyoseok.point.domain;

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
@DisplayName("PointRedisRepository 테스트")
class PointRedisRepositoryTest {

    @Autowired
    private PointRedisRepository pointRedisRepository;

    @AfterEach
    void tearDown() {
        pointRedisRepository.deleteAll();
    }

    @Test
    @DisplayName("등록 및 조회")
    void saveAndFind() {
        // given
        String id = "hyoseok";
        LocalDateTime refreshDatetime = LocalDateTime.of(2020, 11, 20, 0, 0);
        Point point = Point.builder()
                .id(id)
                .amount(1000L)
                .refreshDatetime(refreshDatetime)
                .build();

        // when
        pointRedisRepository.save(point);
        Point findPoint = pointRedisRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("찾을 수 없음"));

        // then
        assertThat(findPoint.getAmount()).isEqualTo(1000L);
        assertThat(findPoint.getRefreshDatetime()).isEqualTo(refreshDatetime);
    }

    @Test
    @DisplayName("수정")
    void update() {
        // given
        String id = "hyoseok";
        LocalDateTime refreshDatetime = LocalDateTime.of(2020, 11, 20, 0, 0);
        pointRedisRepository.save(
                Point.builder()
                        .id(id)
                        .amount(1000L)
                        .refreshDatetime(refreshDatetime)
                        .build()
        );

        // when
        Point findPoint = pointRedisRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("찾을 수 없음"));

        findPoint.refresh(2000L, LocalDateTime.of(2020, 11, 23, 5, 15));

        pointRedisRepository.save(findPoint);

        Point findRefreshPoint = pointRedisRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("찾을 수 없음"));

        // then
        assertThat(findRefreshPoint.getAmount()).isEqualTo(2000L);
    }

}
