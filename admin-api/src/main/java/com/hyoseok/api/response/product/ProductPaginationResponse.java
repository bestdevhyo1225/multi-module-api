package com.hyoseok.api.response.product;

import com.hyoseok.product.usecase.dto.ProductPagination;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductPaginationResponse {
    int pageNo;
    int pageSize;
    int totalPages;
    List<ProductPagination> items;
}
