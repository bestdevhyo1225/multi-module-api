package com.hyoseok.product.usecase.dto;

import com.hyoseok.product.domain.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
public class ProductDetailDto {
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
    private List<ProductImageDetailDto> productImages;

    public static ProductDetailDto createProductDetail(Long id,
                                                       Boolean isSale,
                                                       Boolean isUsed,
                                                       int supplierId,
                                                       double supplyPrice,
                                                       double recommendPrice,
                                                       double consumerPrice,
                                                       int maximum,
                                                       int minimum,
                                                       ProductDescriptionText productDescriptionText,
                                                       List<ProductDescriptionVarchar> productDescriptionVarchars,
                                                       List<ProductImage> productImages) {
        ProductDetailDto productDetailDto = new ProductDetailDto();

        productDetailDto.id = id;
        productDetailDto.isSale = isSale;
        productDetailDto.isUsed = isUsed;
        productDetailDto.supplierId = supplierId;
        productDetailDto.supplyPrice = supplyPrice;
        productDetailDto.recommendPrice = recommendPrice;
        productDetailDto.consumerPrice = consumerPrice;
        productDetailDto.maximum = maximum;
        productDetailDto.minimum = minimum;
        productDetailDto.productDescriptionText = new HashMap<>();
        productDetailDto.productDescriptionVarchar = new HashMap<>();
        productDetailDto.productImages = new ArrayList<>();

        if (productDescriptionText != null) {
            productDetailDto.productDescriptionText.put(productDescriptionText.getKey(), productDescriptionText.getValue());
        }

        if (productDescriptionVarchars != null) {
            productDescriptionVarchars.forEach(descriptionVarchar ->
                    productDetailDto.productDescriptionVarchar.put(descriptionVarchar.getKey(), descriptionVarchar.getValue())
            );
        }

        if (productImages != null) {
            productImages.forEach(productImage -> productDetailDto.productImages.add(new ProductImageDetailDto(productImage)));
        }

        return productDetailDto;
    }

    public static ProductDetailDto createProductDetailFromProductCache(String id,
                                                                       Boolean isSale,
                                                                       Boolean isUsed,
                                                                       int supplierId,
                                                                       double supplyPrice,
                                                                       double recommendPrice,
                                                                       double consumerPrice,
                                                                       int maximum,
                                                                       int minimum,
                                                                       Map<String, String> productDescriptionText,
                                                                       Map<String, String> productDescriptionVarchar,
                                                                       List<ProductImageDetailDto> productImages) {
        ProductDetailDto productDetailDto = new ProductDetailDto();

        productDetailDto.id = Long.parseLong(id);
        productDetailDto.isSale = isSale;
        productDetailDto.isUsed = isUsed;
        productDetailDto.supplierId = supplierId;
        productDetailDto.supplyPrice = supplyPrice;
        productDetailDto.recommendPrice = recommendPrice;
        productDetailDto.consumerPrice = consumerPrice;
        productDetailDto.maximum = maximum;
        productDetailDto.minimum = minimum;
        productDetailDto.productDescriptionText = productDescriptionText;
        productDetailDto.productDescriptionVarchar = productDescriptionVarchar;
        productDetailDto.productImages = productImages;

        return productDetailDto;
    }

}
