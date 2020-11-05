package com.hyoseok.product.usecase.mapper;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductImageVO {
    private Long id;
    private Boolean isUrl;
    private String kind;
    private String image;
    private Integer sortOrder;

    public ProductImageVO(Boolean isUrl, String kind, String image, Integer sortOrder) {
        this.isUrl = isUrl;
        this.kind = kind;
        this.image = image;
        this.sortOrder = sortOrder;
    }

    public ProductImageVO(Long id, Boolean isUrl, String kind, String image, Integer sortOrder) {
        this.id = id;
        this.isUrl = isUrl;
        this.kind = kind;
        this.image = image;
        this.sortOrder = sortOrder;
    }
}
