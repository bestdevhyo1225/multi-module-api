package com.hyoseok.product.domain.redis.entity;

import com.hyoseok.product.domain.rds.usecase.dto.ProductImageDetailDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ProductRedis 도메인 테스트")
class ProductRedisTest {

    @Test
    @DisplayName("ProductRedis 생성")
    void create() {
        // given
        String id = "1";
        Boolean isSale = true;
        Boolean isUsed = true;
        int supplierId = 1;
        double supplyPrice = 20000.0000;
        double recommendPrice = 30000.0000;
        double consumerPrice = 30000.0000;
        int maximum = 10;
        int minimum = 1;
        Map<String, String> productDescriptionText = new HashMap<>();
        Map<String, String> productDescriptionVarchar = new HashMap<>();
        List<ProductImageDetailDto> productImages = new ArrayList<>();
        LocalDateTime refreshDatetime = LocalDateTime.now();

        // when
        ProductRedis productRedis = ProductRedis.create(
                id,
                isSale,
                isUsed,
                supplierId,
                supplyPrice,
                recommendPrice,
                consumerPrice,
                maximum,
                minimum,
                productDescriptionText,
                productDescriptionVarchar,
                productImages,
                refreshDatetime
        );

        // then
        assertThat(productRedis.getId()).isEqualTo(id);
        assertThat(productRedis.getIsSale()).isEqualTo(isSale);
        assertThat(productRedis.getIsUsed()).isEqualTo(isUsed);
        assertThat(productRedis.getSupplierId()).isEqualTo(supplierId);
        assertThat(productRedis.getSupplyPrice()).isEqualTo(supplyPrice);
        assertThat(productRedis.getRecommendPrice()).isEqualTo(recommendPrice);
        assertThat(productRedis.getConsumerPrice()).isEqualTo(consumerPrice);
        assertThat(productRedis.getMaximum()).isEqualTo(maximum);
        assertThat(productRedis.getMinimum()).isEqualTo(minimum);
        assertThat(productRedis.getProductDescriptionText()).isEqualTo(productDescriptionText);
        assertThat(productRedis.getProductDescriptionVarchar()).isEqualTo(productDescriptionVarchar);
        assertThat(productRedis.getProductImages()).isEqualTo(productImages);
        assertThat(productRedis.getRefreshDatetime()).isEqualTo(refreshDatetime);
    }

    @Test
    @DisplayName("ProductRedis 갱신 (refreshDatetime 기준)")
    void refresh() {
        // given
        String id = "1";
        Boolean isSale = true;
        Boolean isUsed = true;
        int supplierId = 1;
        double supplyPrice = 20000.0000;
        double recommendPrice = 30000.0000;
        double consumerPrice = 30000.0000;
        int maximum = 10;
        int minimum = 1;
        Map<String, String> productDescriptionText = new HashMap<>();
        Map<String, String> productDescriptionVarchar = new HashMap<>();
        List<ProductImageDetailDto> productImages = new ArrayList<>();
        LocalDateTime refreshDatetime = LocalDateTime.of(2020,11,20, 0,0,0);
        ProductRedis productRedis = ProductRedis.create(
                id,
                isSale,
                isUsed,
                supplierId,
                supplyPrice,
                recommendPrice,
                consumerPrice,
                maximum,
                minimum,
                productDescriptionText,
                productDescriptionVarchar,
                productImages,
                refreshDatetime
        );

        // when
        double changeSupplyPrice = 35000.0000;
        double changeRecommendPrice = 40000.0000;
        double changeConsumerPrice = 40000.0000;
        LocalDateTime changeRefreshDatetime = LocalDateTime.now();
        productRedis.refresh(
                isSale,
                isUsed,
                supplierId,
                changeSupplyPrice,
                changeRecommendPrice,
                changeConsumerPrice,
                maximum,
                minimum,
                productDescriptionText,
                productDescriptionVarchar,
                productImages,
                changeRefreshDatetime
        );

        // then
        assertThat(productRedis.getId()).isEqualTo(id);
        assertThat(productRedis.getIsSale()).isEqualTo(isSale);
        assertThat(productRedis.getIsUsed()).isEqualTo(isUsed);
        assertThat(productRedis.getSupplierId()).isEqualTo(supplierId);
        assertThat(productRedis.getSupplyPrice()).isEqualTo(changeSupplyPrice);
        assertThat(productRedis.getRecommendPrice()).isEqualTo(changeRecommendPrice);
        assertThat(productRedis.getConsumerPrice()).isEqualTo(changeConsumerPrice);
        assertThat(productRedis.getMaximum()).isEqualTo(maximum);
        assertThat(productRedis.getMinimum()).isEqualTo(minimum);
        assertThat(productRedis.getProductDescriptionText()).isEqualTo(productDescriptionText);
        assertThat(productRedis.getProductDescriptionVarchar()).isEqualTo(productDescriptionVarchar);
        assertThat(productRedis.getProductImages()).isEqualTo(productImages);
        assertThat(productRedis.getRefreshDatetime()).isEqualTo(changeRefreshDatetime);
    }

}
