package com.hyoseok.api.response.product;

import com.hyoseok.product.usecase.dto.ProductDetailDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FindProductsResponse {
    private List<ProductDetailDto> products;
}
