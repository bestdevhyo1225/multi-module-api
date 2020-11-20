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
    private final Map<String, String> productDescriptionText = new HashMap<>();
    private final Map<String, String> productDescriptionVarchar = new HashMap<>();
    private List<ProductImageDetail> productImages = new ArrayList<>();

    @Builder
    public ProductDetail(Long id,
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
        this.id = id;
        this.isSale = isSale;
        this.isUsed = isUsed;
        this.supplierId = supplierId;
        this.supplyPrice = supplyPrice;
        this.recommendPrice = recommendPrice;
        this.consumerPrice = consumerPrice;
        this.maximum = maximum;
        this.minimum = minimum;

        if (productDescriptionText != null) {
            this.productDescriptionText.put(
                    productDescriptionText.getKey(), productDescriptionText.getValue()
            );
        }

        if (productDescriptionVarchars != null) {
            productDescriptionVarchars.forEach(descriptionVarchar ->
                    this.productDescriptionVarchar.put(descriptionVarchar.getKey(), descriptionVarchar.getValue())
            );
        }

        if (productImages != null) {
            this.productImages = productImages.stream()
                    .map(ProductImageDetail::new)
                    .collect(toList());
        }
    }

}
