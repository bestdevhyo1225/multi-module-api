package com.hyoseok.product.domain.rds.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("ProductImage 엔티티 테스트")
class ProductImageTest {

    @Test
    @DisplayName("ProductImage 엔티티 생성")
    void create() {
        // given
        boolean isUrl = true;
        String kind = "kind";
        String image = "image";
        int sortOrder = 0;

        // when
        ProductImage productImage = ProductImage.create(isUrl, kind, image, sortOrder);

        // then
        assertThat(productImage.getIsUrl()).isEqualTo(isUrl);
        assertThat(productImage.getImage()).isEqualTo(image);
        assertThat(productImage.getKind()).isEqualTo(kind);
        assertThat(productImage.getSortOrder()).isEqualTo(sortOrder);
    }

}
