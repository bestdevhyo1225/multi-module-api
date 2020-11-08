package com.hyoseok.product.usecase.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductPagination {
    private Long id;
    private Boolean isSale;
    private Boolean isUsed;
    private double supplyPrice;
    private double recommendPrice;
    private double consumerPrice;
}
