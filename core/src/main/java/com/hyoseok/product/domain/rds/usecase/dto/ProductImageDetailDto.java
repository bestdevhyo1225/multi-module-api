package com.hyoseok.product.domain.rds.usecase.dto;

import com.hyoseok.product.domain.rds.entity.ProductImage;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductImageDetailDto {
    private Long id;
    private boolean isUrl;
    private String kind;
    private String image;
    private int sortOrder;

    public ProductImageDetailDto(ProductImage productImage) {
        this.id = productImage.getId();
        this.isUrl = productImage.getIsUrl();
        this.kind = productImage.getKind();
        this.image = productImage.getImage();
        this.sortOrder = productImage.getSortOrder();
    }
}
