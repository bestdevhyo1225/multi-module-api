package com.hyoseok.product.application;

import com.hyoseok.product.domain.rds.entity.Product;
import com.hyoseok.product.domain.rds.usecase.ProductCommandService;
import com.hyoseok.product.domain.rds.usecase.ProductQueryService;
import com.hyoseok.product.domain.rds.usecase.dto.ProductDetailDto;
import com.hyoseok.product.domain.rds.usecase.mapper.ProductDescriptionTextMapper;
import com.hyoseok.product.domain.rds.usecase.mapper.ProductDescriptionVarcharMapper;
import com.hyoseok.product.domain.rds.usecase.mapper.ProductMapper;
import com.hyoseok.product.domain.redis.entity.ProductRedis;
import com.hyoseok.product.domain.redis.usecase.ProductRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProductServiceManager {

    private final ProductRedisService productRedisService;
    private final ProductQueryService productQueryService;
    private final ProductCommandService productCommandService;

    public ProductDetailDto findProduct(Long id) {
        ProductRedis productRedis = productRedisService.findProductRedis(id.toString());

        if (productRedis != null) {
            return ProductDetailDto.createProductDetailFromProductCache(
                    productRedis.getId(),
                    productRedis.getIsSale(),
                    productRedis.getIsUsed(),
                    productRedis.getSupplierId(),
                    productRedis.getSupplyPrice(),
                    productRedis.getRecommendPrice(),
                    productRedis.getConsumerPrice(),
                    productRedis.getMaximum(),
                    productRedis.getMinimum(),
                    productRedis.getProductDescriptionText(),
                    productRedis.getProductDescriptionVarchar(),
                    productRedis.getProductImages()
            );
        }

        ProductDetailDto productDetailDto = productQueryService.findProductDetailDto(id);

        productRedisService.createOrUpdate(
                ProductRedis.create(
                        productDetailDto.getId().toString(),
                        productDetailDto.getIsSale(),
                        productDetailDto.getIsUsed(),
                        productDetailDto.getSupplierId(),
                        productDetailDto.getSupplyPrice(),
                        productDetailDto.getRecommendPrice(),
                        productDetailDto.getConsumerPrice(),
                        productDetailDto.getMaximum(),
                        productDetailDto.getMinimum(),
                        productDetailDto.getProductDescriptionText(),
                        productDetailDto.getProductDescriptionVarchar(),
                        productDetailDto.getProductImages(),
                        LocalDateTime.now()
                )
        );

        return productDetailDto;
    }

    public void updateProduct(ProductMapper productMapper,
                              ProductDescriptionTextMapper productDescriptionTextMapper,
                              ProductDescriptionVarcharMapper productDescriptionVarcharMapper) {


        Product updatedProduct = productCommandService.update(
                productMapper, productDescriptionTextMapper, productDescriptionVarcharMapper
        );

        ProductRedis productRedis = productRedisService.findProductRedis(updatedProduct.getId().toString());

        if (productRedis == null) return;

        ProductDetailDto productDetail = ProductDetailDto.createProductDetail(
                updatedProduct.getId(),
                updatedProduct.getIsSale(),
                updatedProduct.getIsUsed(),
                updatedProduct.getSupplierId(),
                updatedProduct.getSupplyPrice(),
                updatedProduct.getRecommendPrice(),
                updatedProduct.getConsumerPrice(),
                updatedProduct.getMaximum(),
                updatedProduct.getMinimum(),
                updatedProduct.getProductDescriptionText(),
                updatedProduct.getProductDescriptionVarchars(),
                updatedProduct.getProductImages()
        );

        productRedis.refresh(
                productDetail.getIsSale(),
                productDetail.getIsUsed(),
                productDetail.getSupplierId(),
                productDetail.getSupplyPrice(),
                productDetail.getRecommendPrice(),
                productDetail.getConsumerPrice(),
                productDetail.getMaximum(),
                productDetail.getMinimum(),
                productDetail.getProductDescriptionText(),
                productDetail.getProductDescriptionVarchar(),
                productDetail.getProductImages(),
                LocalDateTime.now()
        );

        productRedisService.createOrUpdate(productRedis);
    }

}
