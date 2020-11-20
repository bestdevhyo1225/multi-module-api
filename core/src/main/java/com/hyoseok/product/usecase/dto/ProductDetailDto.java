package com.hyoseok.product.usecase.dto;

import com.hyoseok.product.domain.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

@Getter
@NoArgsConstructor
public class ProductDetail {
    private Long id;
    private Boolean isSale;
    private Boolean isUsed;
    private int supplierId;
    private double supplyPrice;
    private double recommendPrice;
    private double consumerPrice;
    private int maximum;
    private int minimum;
    private Map<String, String> productDescriptionText;
    private Map<String, String> productDescriptionVarchar;
    private List<ProductImageDetail> productImages;

    public static ProductDetail createProductDetail(Product product) {
        ProductDetail productDetail = new ProductDetail();

        productDetail.id = product.getId();
        productDetail.isSale = product.getIsSale();
        productDetail.isUsed = product.getIsUsed();
        productDetail.supplierId = product.getSupplierId();
        productDetail.supplyPrice = product.getSupplyPrice();
        productDetail.recommendPrice = product.getRecommendPrice();
        productDetail.consumerPrice = product.getConsumerPrice();
        productDetail.maximum = product.getMaximum();
        productDetail.minimum = product.getMinimum();
        productDetail.productDescriptionText = new HashMap<>();
        productDetail.productDescriptionVarchar = new HashMap<>();
        productDetail.productImages = new ArrayList<>();

        if (product.getProductDescriptionText() != null) {
            productDetail.productDescriptionText.put(
                    product.getProductDescriptionText().getKey(), product.getProductDescriptionText().getValue()
            );
        }

        if (product.getProductDescriptionVarchars() != null) {
            product.getProductDescriptionVarchars().forEach(descriptionVarchar ->
                    productDetail.productDescriptionVarchar.put(descriptionVarchar.getKey(), descriptionVarchar.getValue())
            );
        }

        if (product.getProductImages() != null) {
            product.getProductImages().forEach(
                    productImage -> productDetail.productImages.add(new ProductImageDetail(productImage))
            );
        }

        return productDetail;
    }

    public static ProductDetail createProductDetailByRedisProduct(RedisProduct redisProduct) {
        ProductDetail productDetail = new ProductDetail();

        productDetail.id = Long.parseLong(redisProduct.getId());
        productDetail.isSale = redisProduct.getIsSale();
        productDetail.isUsed = redisProduct.getIsUsed();
        productDetail.supplierId = redisProduct.getSupplierId();
        productDetail.supplyPrice = redisProduct.getSupplyPrice();
        productDetail.recommendPrice = redisProduct.getRecommendPrice();
        productDetail.consumerPrice = redisProduct.getConsumerPrice();
        productDetail.maximum = redisProduct.getMaximum();
        productDetail.minimum = redisProduct.getMinimum();
        productDetail.productDescriptionText = redisProduct.getProductDescriptionText();
        productDetail.productDescriptionVarchar = redisProduct.getProductDescriptionVarchar();
        productDetail.productImages = redisProduct.getProductImages();

        return productDetail;
    }

}
