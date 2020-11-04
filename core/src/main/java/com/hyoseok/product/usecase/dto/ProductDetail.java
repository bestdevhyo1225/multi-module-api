package com.hyoseok.product.usecase.dto;

import com.hyoseok.product.domain.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private List<ProductImageDetail> productImages;

    public ProductDetail(Product product) {
        this.id = product.getId();
        this.isSale = product.getIsSale();
        this.isUsed = product.getIsSale();
        this.supplierId = product.getSupplierId();
        this.supplyPrice = product.getSupplyPrice();
        this.recommendPrice = product.getRecommendPrice();
        this.consumerPrice = product.getConsumerPrice();
        this.maximum = product.getMaximum();
        this.minimum = product.getMinimum();

        this.productDescriptionText.put(
                product.getProductDescriptionText().getKey(), product.getProductDescriptionText().getValue()
        );

        product.getProductDescriptionVarchars().forEach(descriptionVarchar ->
                this.productDescriptionVarchar.put(descriptionVarchar.getKey(), descriptionVarchar.getValue())
        );

        this.productImages = product.getProductImages().stream()
                .map(ProductImageDetail::new)
                .collect(toList());
    }
}
