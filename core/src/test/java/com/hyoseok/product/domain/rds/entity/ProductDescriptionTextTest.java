package com.hyoseok.product.domain.rds.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ProductDescriptionText 엔티티 테스트")
class ProductDescriptionTextTest {

    @Test
    @DisplayName("ProductDescriptionText 엔티티 생성")
    void create() {
        // given
        String value = "descriptionValue";

        // when
        ProductDescriptionText productDescriptionText = ProductDescriptionText.create(value);

        // then
        assertThat(productDescriptionText.getKey()).isEqualTo("description");
        assertThat(productDescriptionText.getValue()).isEqualTo(value);
        assertThat(productDescriptionText.getLanguage()).isEqualTo(Language.KOREAN);
    }

    @Test
    @DisplayName("ProductDescriptionText 엔티티 변경")
    void change() {
        // given
        String value = "descriptionValue";
        ProductDescriptionText productDescriptionText = ProductDescriptionText.create(value);

        // when
        String changeValue = "changeDescriptionValue";
        productDescriptionText.change(changeValue);

        // then
        assertThat(productDescriptionText.getKey()).isEqualTo("description");
        assertThat(productDescriptionText.getValue()).isEqualTo(changeValue);
        assertThat(productDescriptionText.getLanguage()).isEqualTo(Language.KOREAN);
    }

}
