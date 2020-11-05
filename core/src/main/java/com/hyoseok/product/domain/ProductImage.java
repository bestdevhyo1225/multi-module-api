package com.hyoseok.product.domain;

import com.hyoseok.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@Getter
@DynamicUpdate
@NoArgsConstructor(access = PROTECTED)
public class ProductImage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "product_image_id")
    private Long id;

    @Column(nullable = false)
    private Boolean isUrl;

    @Column(length = 20, nullable = false)
    private String kind;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private int sortOrder;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public void setProduct(Product product) {
        this.product = product;
    }

    public static ProductImage create(boolean isUrl, String kind, String image, int sortOrder) {
        ProductImage productImage = new ProductImage();

        productImage.isUrl = isUrl;
        productImage.kind = kind;
        productImage.image = image;
        productImage.sortOrder = sortOrder;

        return productImage;
    }

}
