package com.hyoseok.product.usecase.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class FixedProductPageRequest extends PageRequest {

    public FixedProductPageRequest(Pageable pageable, long totalCount) {
        super(getPageNo(pageable, totalCount), pageable.getPageSize(), pageable.getSort());
    }

    private static int getPageNo(Pageable pageable, long totalCount) {
        /*
        * 요청한 페이지 번호가 기존 데이터 사이즈를 초과할 경우, 마지막 페이지의 데이터를 반환한다.
        * */
        int pageNo = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        long requestCount = (long)pageNo * (long)pageSize;

        // 실제 전체 건수가 더 많은 경우에는 그대로 반환
        if (totalCount > requestCount) return pageNo;

        return (int)Math.ceil((double)totalCount / pageNo); // ex: 71~79이면 8이 되기 위해
    }

}
