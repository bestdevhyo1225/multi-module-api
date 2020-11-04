package com.hyoseok.api.response.product;

import com.hyoseok.product.usecase.dto.ProductDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FindProductsResponse {
    private List<ProductDetail> products;
}
