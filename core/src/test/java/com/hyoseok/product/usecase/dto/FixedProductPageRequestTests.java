package com.hyoseok.product.usecase.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.*;

@DisplayName("FixedProductPageRequest 유닛 테스트")
class FixedProductPageRequestTests {

    @ParameterizedTest
    @CsvSource({
            "10, 100, 10", // 페이지 번호: 10, 전체 건수: 100, 변환 후 받은 페이지 번호: 10
            "10, 101, 10", // 페이지 번호: 10, 전체 건수: 101, 변환 후 받은 페이지 번호: 10
            "10, 91, 10", // 페이지 번호: 10, 전체 건수: 91, 변환 후 받은 페이지 번호: 10
            "10, 90, 9", // 페이지 번호: 10, 전체 건수: 90, 변환 후 받은 페이지 번호: 9
            "10, 79, 8", // 페이지 번호: 10, 전체 건수: 79, 변환 후 받은 페이지 번호: 8
            "10, 11, 2", // 페이지 번호: 10, 전체 건수: 11, 변환 후 받은 페이지 번호: 2
            "10, 10, 1" // 페이지 번호: 10, 전체 건수: 10, 변환 후 받은 페이지 번호: 1
    })
    void dto_exchange_page_request(int pageNo, long totalCount, int expectedPageNo) throws Exception {
        // given
        Pageable pageRequest = PageRequest.of(pageNo, 10);

        // when
        Pageable result = new FixedProductPageRequest(pageRequest, totalCount);

        // then
        assertThat(result.getPageNumber()).isEqualTo(expectedPageNo);
    }

}
