package com.hyoseok.product.domain.rds.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ProductDescriptionVarchar 엔티티 테스트")
class ProductDescriptionVarcharTest {

    @Test
    @DisplayName("ProductDescriptionVarchar 엔티티 생성")
    void create() {
        // given
        String key = "name";
        String value = "상품1";

        // when
        ProductDescriptionVarchar productDescriptionVarchar = ProductDescriptionVarchar.create(key, value);

        // then
        assertThat(productDescriptionVarchar.getKey()).isEqualTo(key);
        assertThat(productDescriptionVarchar.getValue()).isEqualTo(value);
        assertThat(productDescriptionVarchar.getLanguage()).isEqualTo(Language.KOREAN);
    }

    @Test
    @DisplayName("ProductDescriptionVarchar 엔티티 변경")
    void change() {
        // given
        String key = "name";
        String value = "상품1";
        ProductDescriptionVarchar productDescriptionVarchar = ProductDescriptionVarchar.create(key, value);

        // when
        String changeValue = "상품 이름 변경";
        productDescriptionVarchar.change(changeValue);

        // then
        assertThat(productDescriptionVarchar.getKey()).isEqualTo(key);
        assertThat(productDescriptionVarchar.getValue()).isEqualTo(changeValue);
        assertThat(productDescriptionVarchar.getLanguage()).isEqualTo(Language.KOREAN);
    }

}
