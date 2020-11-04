package com.hyoseok.api.response.product;

import com.hyoseok.product.usecase.dto.ProductDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FindProductResponse {
    ProductDetail product;
}
