package com.hyoseok.product.usecase;

import com.hyoseok.product.domain.Product;
import com.hyoseok.product.usecase.mapper.ProductDescriptionTextMapper;
import com.hyoseok.product.usecase.mapper.ProductDescriptionVarcharMapper;
import com.hyoseok.product.usecase.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductManager {

    private final ProductQueryService productQueryService;
    private final ProductCommandService productCommandService;

    public void update(ProductMapper productMapper,
                       ProductDescriptionTextMapper productDescriptionTextMapper,
                       ProductDescriptionVarcharMapper productDescriptionVarcharMapper) {
        Product product = productQueryService.findPureProduct(productMapper.getId());

        productCommandService.updateProductV2(
                product, productMapper, productDescriptionTextMapper, productDescriptionVarcharMapper
        );
    }

}
