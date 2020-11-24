package com.hyoseok.product.domain.rds.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Product 엔티티 테스트")
class ProductTest {

    @Test
    @DisplayName("Product 애그리거트 생성")
    void create() {
        // given
        boolean isSale = true;
        boolean isUsed = true;
        int supplierId = 1;
        double supplyPrice = 20000.0000;
        double recommendPrice = 30000.0000;
        double consumerPrice = 30000.0000;
        int maximum = 10;
        int minimum = 1;
        ProductDescriptionText productDescriptionText = ProductDescriptionText.create("description");
        ProductDescriptionVarchar productDescriptionVarchar = ProductDescriptionVarchar.create("name", "상품 이름");
        ProductImage productImage = ProductImage.create(true, "kind", "image", 0);

        // when
        Product product = Product.create(
                isSale,
                isUsed,
                supplierId,
                supplyPrice,
                recommendPrice,
                consumerPrice,
                maximum,
                minimum,
                productDescriptionText,
                List.of(productDescriptionVarchar),
                List.of(productImage)
        );

        // then
        assertThat(product.getIsSale()).isEqualTo(isSale);
        assertThat(product.getIsUsed()).isEqualTo(isUsed);
        assertThat(product.getSupplierId()).isEqualTo(supplierId);
        assertThat(product.getSupplyPrice()).isEqualTo(supplyPrice);
        assertThat(product.getRecommendPrice()).isEqualTo(recommendPrice);
        assertThat(product.getConsumerPrice()).isEqualTo(consumerPrice);
        assertThat(product.getMaximum()).isEqualTo(maximum);
        assertThat(product.getMinimum()).isEqualTo(minimum);
        assertThat(product.getProductDescriptionText().getKey()).isEqualTo(productDescriptionText.getKey());
        assertThat(product.getProductDescriptionText().getValue()).isEqualTo(productDescriptionText.getValue());
        assertThat(product.getProductDescriptionVarchars().get(0).getKey()).isEqualTo(productDescriptionVarchar.getKey());
        assertThat(product.getProductDescriptionVarchars().get(0).getValue()).isEqualTo(productDescriptionVarchar.getValue());
        assertThat(product.getProductImages().get(0).getSortOrder()).isEqualTo(productImage.getSortOrder());
        assertThat(product.getProductImages().get(0).getKind()).isEqualTo(productImage.getKind());
        assertThat(product.getProductImages().get(0).getImage()).isEqualTo(productImage.getImage());
        assertThat(product.getProductImages().get(0).getIsUrl()).isEqualTo(productImage.getIsUrl());
    }

    @Test
    @DisplayName("Product 애그리거트 변경")
    void change() {
        // given
        boolean isSale = true;
        boolean isUsed = true;
        int supplierId = 1;
        double supplyPrice = 20000.0000;
        double recommendPrice = 30000.0000;
        double consumerPrice = 30000.0000;
        int maximum = 10;
        int minimum = 1;
        ProductDescriptionText productDescriptionText = ProductDescriptionText.create("description");
        ProductDescriptionVarchar productDescriptionVarchar = ProductDescriptionVarchar.create("name", "상품 이름");
        ProductImage productImage = ProductImage.create(true, "kind", "image", 0);
        Product product = Product.create(
                isSale,
                isUsed,
                supplierId,
                supplyPrice,
                recommendPrice,
                consumerPrice,
                maximum,
                minimum,
                productDescriptionText,
                List.of(productDescriptionVarchar),
                List.of(productImage)
        );

        // when
        double changeSupplyPrice = 35000.0000;
        int changeMaximum = 25;
        product.change(
                isSale,
                isUsed,
                supplierId,
                changeSupplyPrice,
                recommendPrice,
                consumerPrice,
                changeMaximum,
                minimum
        );

        // then
        assertThat(product.getIsSale()).isEqualTo(isSale);
        assertThat(product.getIsUsed()).isEqualTo(isUsed);
        assertThat(product.getSupplierId()).isEqualTo(supplierId);
        assertThat(product.getSupplyPrice()).isEqualTo(changeSupplyPrice);
        assertThat(product.getRecommendPrice()).isEqualTo(recommendPrice);
        assertThat(product.getConsumerPrice()).isEqualTo(consumerPrice);
        assertThat(product.getMaximum()).isEqualTo(changeMaximum);
        assertThat(product.getMinimum()).isEqualTo(minimum);
    }

}
