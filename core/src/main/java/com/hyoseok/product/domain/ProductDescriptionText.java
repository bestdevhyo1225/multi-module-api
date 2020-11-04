package com.hyoseok.product.domain;

import com.hyoseok.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class ProductDescriptionText extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "product_description_text_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Language language;

    @Column(name = "`key`", length = 30, nullable = false)
    private String key;

    @Column(name = "`value`", nullable = false)
    private String value;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public void setProduct(Product product) {
        this.product = product;
    }

    public static ProductDescriptionText create(String value) {
        ProductDescriptionText productDescriptionText = new ProductDescriptionText();
        final String DESCRIPTION = "description";

        productDescriptionText.language = Language.KOREAN;
        productDescriptionText.key = DESCRIPTION;
        productDescriptionText.value = value;

        return productDescriptionText;
    }

    public void change(String value) {
        this.value = value;
    }

}
