package com.hyoseok.product.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Proudct Sub Aggregate 테스트")
class ProductSubAggregateTests {

    @Test
    @DisplayName("ProductDescriptionText 엔티티를 생성한다.")
    void createProductDescriptionText() {
        // given
        String value = "create description value";

        // when
        ProductDescriptionText productDescriptionText = ProductDescriptionText.create(value);

        // then
        assertThat(productDescriptionText.getValue()).isEqualTo(value);
    }

    @Test
    @DisplayName("ProductDescriptionVarchar 엔티티를 생성한다.")
    void createProductDescriptionVarchars() {
        // given
        List<String> keys = List.of("name", "user_code1", "user_code2");
        List<String> values = List.of("상품1", "테스트1", "테스트2");

        // when
        List<ProductDescriptionVarchar> productDescriptionVarchars = IntStream
                .range(0, keys.size())
                .mapToObj(index -> ProductDescriptionVarchar.create(keys.get(index), values.get(index)))
                .collect(Collectors.toList());

        // then
        assertThat(productDescriptionVarchars.size()).isEqualTo(3);
        assertThat(productDescriptionVarchars.get(0).getKey()).isEqualTo(keys.get(0));
        assertThat(productDescriptionVarchars.get(1).getKey()).isEqualTo(keys.get(1));
        assertThat(productDescriptionVarchars.get(2).getKey()).isEqualTo(keys.get(2));
        assertThat(productDescriptionVarchars.get(0).getValue()).isEqualTo(values.get(0));
        assertThat(productDescriptionVarchars.get(1).getValue()).isEqualTo(values.get(1));
        assertThat(productDescriptionVarchars.get(2).getValue()).isEqualTo(values.get(2));
    }

    @Test
    @DisplayName("ProductImage 엔티티를 생성한다.")
    void createProductImages() {
        // given
        boolean isUrl = false;
        String kind = "kind";
        String image = "http://images.com";
        int sortOrder = 0;

        // when
        ProductImage productImage = ProductImage.create(isUrl, kind, image, sortOrder);

        // then
        assertThat(productImage.getIsUrl()).isEqualTo(isUrl);
        assertThat(productImage.getKind()).isEqualTo(kind);
        assertThat(productImage.getImage()).isEqualTo(image);
        assertThat(productImage.getSortOrder()).isEqualTo(sortOrder);
    }

    @Test
    @DisplayName("Product 엔티티를 생성한다.")
    void createProduct() {
        // given
        Boolean isSale = true;
        Boolean isUsed = true;
        int supplierId = 1;
        double supplyPrice = 30000.0000;
        double recommendPrice = 30000.0000;
        double consumerPrice = 20000.0000;
        int maximum = 30;
        int minimum = 10;
        ProductDescriptionText productDescriptionText = ProductDescriptionText.create("text");
        List<ProductDescriptionVarchar> productDescriptionVarchars = List.of(ProductDescriptionVarchar.create("key", "value"));
        List<ProductImage> productImages = List.of(ProductImage.create(false, "kind", "image", 0));

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
                productDescriptionVarchars,
                productImages
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
        assertThat(product.getProductDescriptionText().getValue()).isEqualTo(productDescriptionText.getValue());
        assertThat(product.getProductDescriptionVarchars().get(0).getKey()).isEqualTo(productDescriptionVarchars.get(0).getKey());
        assertThat(product.getProductDescriptionVarchars().get(0).getValue()).isEqualTo(productDescriptionVarchars.get(0).getValue());
        assertThat(product.getProductImages().get(0).getIsUrl()).isEqualTo(productImages.get(0).getIsUrl());
        assertThat(product.getProductImages().get(0).getKind()).isEqualTo(productImages.get(0).getKind());
        assertThat(product.getProductImages().get(0).getImage()).isEqualTo(productImages.get(0).getImage());
        assertThat(product.getProductImages().get(0).getSortOrder()).isEqualTo(productImages.get(0).getSortOrder());
    }

    @Test
    @DisplayName("Product 엔티티의 필드를 변경한다.")
    void changeProduct() {
        // given
        Boolean isSale = true;
        Boolean isUsed = true;
        int supplierId = 1;
        double supplyPrice = 30000.0000;
        double recommendPrice = 30000.0000;
        double consumerPrice = 20000.0000;
        int maximum = 30;
        int minimum = 10;

        // when
        Product product = new Product();
        product.change(isSale, isUsed, supplierId, supplyPrice, recommendPrice, consumerPrice, maximum, minimum);

        // then
        assertThat(product.getIsSale()).isEqualTo(isSale);
        assertThat(product.getIsUsed()).isEqualTo(isUsed);
        assertThat(product.getSupplierId()).isEqualTo(supplierId);
        assertThat(product.getSupplyPrice()).isEqualTo(supplyPrice);
        assertThat(product.getRecommendPrice()).isEqualTo(recommendPrice);
        assertThat(product.getConsumerPrice()).isEqualTo(consumerPrice);
        assertThat(product.getMaximum()).isEqualTo(maximum);
        assertThat(product.getMinimum()).isEqualTo(minimum);
    }

    @Test
    @DisplayName("ProductDescriptionText 엔티티의 필드를 변경한다.")
    void changeProductDescriptionText() {
        // given
        String description = "description";

        // when
        ProductDescriptionText productDescriptionText = new ProductDescriptionText();
        productDescriptionText.change(description);

        // then
        assertThat(productDescriptionText.getValue()).isEqualTo(description);
    }

    @Test
    @DisplayName("ProductDescriptionVarchar 엔티티의 필드를 변경한다.")
    void changeProductDescriptionVarchar() {
        // given
        String value = "테스트1";

        // when
        ProductDescriptionVarchar productDescriptionVarchar = new ProductDescriptionVarchar();
        productDescriptionVarchar.change(value);

        // then
        assertThat(productDescriptionVarchar.getValue()).isEqualTo(value);
    }

}
