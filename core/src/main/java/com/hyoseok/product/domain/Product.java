package com.hyoseok.product.domain;

import com.hyoseok.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@Getter
@DynamicUpdate
@NoArgsConstructor(access = PROTECTED)
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(nullable = false)
    private Boolean isSale;

    @Column(nullable = false)
    private Boolean isUsed;

    @Column(nullable = false)
    private int supplierId;

    @Column(nullable = false)
    private double supplyPrice;

    @Column(nullable = false)
    private double recommendPrice;

    @Column(nullable = false)
    private double consumerPrice;

    @Column(nullable = false)
    private int maximum;

    @Column(nullable = false)
    private int minimum;

    @OneToOne(mappedBy = "product", fetch = LAZY, cascade = ALL)
    private ProductDescriptionText productDescriptionText;

    @OneToMany(mappedBy = "product", cascade = ALL)
    private List<ProductDescriptionVarchar> productDescriptionVarchars = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = ALL)
    private List<ProductImage> productImages = new ArrayList<>();

    public void setProductDescriptionText(ProductDescriptionText productDescriptionText) {
        this.productDescriptionText = productDescriptionText;
        productDescriptionText.setProduct(this);
    }

    public void addProductDescriptionVarchar(ProductDescriptionVarchar productDescriptionVarchar) {
        this.productDescriptionVarchars.add(productDescriptionVarchar);
        productDescriptionVarchar.setProduct(this);
    }

    public void addProductImage(ProductImage productImage) {
        this.productImages.add(productImage);
        productImage.setProduct(this);
    }

    public static Product create(Boolean isSale,
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
        Product product = new Product();

        product.isSale = isSale;
        product.isUsed = isUsed;
        product.supplierId = supplierId;
        product.supplyPrice = supplyPrice;
        product.recommendPrice = recommendPrice;
        product.consumerPrice = consumerPrice;
        product.maximum = maximum;
        product.minimum = minimum;
        product.setProductDescriptionText(productDescriptionText);
        productDescriptionVarchars.forEach(product::addProductDescriptionVarchar);
        productImages.forEach(product::addProductImage);

        return product;
    }

    public void change(Boolean isSale, Boolean isUsed, int supplierId, double supplyPrice,
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
