package com.hyoseok.product.domain.redis.usecase;

import com.hyoseok.product.domain.rds.entity.ProductImage;
import com.hyoseok.product.domain.rds.usecase.dto.ProductImageDetailDto;
import com.hyoseok.product.domain.redis.entity.ProductRedis;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("ProductRedisService 테스트")
class ProductRedisServiceTest {

    @Autowired
    private ProductRedisService productRedisService;

    @Test
    @DisplayName("ProductRedis 생성 후, 조회")
    void findProductRedis() {
        // given
        Map<String, String> productDescriptionText = new HashMap<>();
        Map<String, String> productDescriptionVarchar = new HashMap<>();
        List<ProductImageDetailDto> productImages = new ArrayList<>();

        productDescriptionText.put("description", "상품 설명");
        productDescriptionVarchar.put("name", "상품 이름");
        productImages.add(new ProductImageDetailDto(ProductImage.create(true, "kind", "image", 0)));

        ProductRedis productRedis = ProductRedis.create(
                "1",
                true,
                true,
                1,
                20000.0000,
                30000.0000,
                30000.0000,
                10,
                1,
                productDescriptionText,
                productDescriptionVarchar,
                productImages,
                LocalDateTime.now()
        );

        productRedisService.createOrUpdate(productRedis);

        // when
        ProductRedis findProductRedis = productRedisService.findProductRedis(productRedis.getId());
        Map<String, String> productDescriptionTextMap = findProductRedis.getProductDescriptionText();
        Map<String, String> productDescriptionVarcharMap = findProductRedis.getProductDescriptionVarchar();
        ProductImageDetailDto productImageDetailDto = findProductRedis.getProductImages().get(0);

        // then
        assertThat(findProductRedis.getId()).isEqualTo(productRedis.getId());
        assertThat(findProductRedis.getIsSale()).isEqualTo(productRedis.getIsSale());
        assertThat(findProductRedis.getIsUsed()).isEqualTo(productRedis.getIsUsed());
        assertThat(findProductRedis.getSupplierId()).isEqualTo(productRedis.getSupplierId());
        assertThat(findProductRedis.getSupplyPrice()).isEqualTo(productRedis.getSupplyPrice());
        assertThat(findProductRedis.getRecommendPrice()).isEqualTo(productRedis.getRecommendPrice());
        assertThat(findProductRedis.getConsumerPrice()).isEqualTo(productRedis.getConsumerPrice());
        assertThat(findProductRedis.getMaximum()).isEqualTo(productRedis.getMaximum());
        assertThat(findProductRedis.getMinimum()).isEqualTo(productRedis.getMinimum());
        assertThat(findProductRedis.getRefreshDatetime()).isEqualTo(productRedis.getRefreshDatetime());
        assertThat(productDescriptionTextMap.get("description")).contains("상품 설명");
        assertThat(productDescriptionVarcharMap.get("name")).contains("상품 이름");
        assertThat(productImageDetailDto.getId()).isEqualTo(productImages.get(0).getId());
        assertThat(productImageDetailDto.getKind()).isEqualTo(productImages.get(0).getKind());
        assertThat(productImageDetailDto.getImage()).isEqualTo(productImages.get(0).getImage());
        assertThat(productImageDetailDto.isUrl()).isEqualTo(productImages.get(0).isUrl());
        assertThat(productImageDetailDto.getSortOrder()).isEqualTo(productImages.get(0).getSortOrder());
    }


}
