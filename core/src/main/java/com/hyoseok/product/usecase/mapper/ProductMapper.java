package com.hyoseok.product.usecase.mapper;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
public class ProductMapper {
    private Long id;
    private Boolean isSale;
    private Boolean isUsed;
    private int supplierId;
    private double supplyPrice;
    private double recommendPrice;
    private double consumerPrice;
    private int maximum;
    private int minimum;

    public ProductMapper(Long id, Boolean isSale, Boolean isUsed, int supplierId, double supplyPrice,
                         double recommendPrice, double consumerPrice, int maximum, int minimum) {
        this.id = id;
        this.isSale = isSale;
        this.isUsed = isUsed;
        this.supplierId = supplierId;
        this.supplyPrice = supplyPrice;
        this.recommendPrice = recommendPrice;
        this.consumerPrice = consumerPrice;
        this.maximum = maximum;
        this.minimum = minimum;
    }

    public ProductMapper(Boolean isSale, Boolean isUsed, int supplierId, double supplyPrice,
                         double recommendPrice, double consumerPrice, int maximum, int minimum) {
        this.isSale = isSale;
        this.isUsed = isUsed;
        this.supplierId = supplierId;
        this.supplyPrice = supplyPrice;
        this.recommendPrice = recommendPrice;
        this.consumerPrice = consumerPrice;
        this.maximum = maximum;
        this.minimum = minimum;
    }
}
