package com.hyoseok.product.domain.rds.usecase;

import com.hyoseok.product.domain.rds.entity.*;
import com.hyoseok.product.domain.rds.usecase.mapper.CreateProductImageMapper;
import com.hyoseok.product.domain.rds.usecase.mapper.ProductDescriptionTextMapper;
import com.hyoseok.product.domain.rds.usecase.mapper.ProductDescriptionVarcharMapper;
import com.hyoseok.product.domain.rds.usecase.mapper.ProductMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@DisplayName("ProductCommandService 테스트")
class ProductCommandServiceTest {

    @Autowired
    private ProductCommandService productCommandService;

    @Autowired
    private ProductQueryRepository productQueryRepository;

    @Test
    @DisplayName("Product 애그리거트 생성")
    void createProductAggregate() {
        // given
        ProductMapper productMapper = ProductMapper.builder()
                .isSale(true)
                .isUsed(true)
                .supplierId(1)
                .supplyPrice(20000.0000)
                .recommendPrice(30000.0000)
                .consumerPrice(30000.0000)
                .maximum(15)
                .minimum(1)
                .build();

        ProductDescriptionTextMapper productDescriptionTextMapper = ProductDescriptionTextMapper.builder()
                .value("descriptionValue")
                .build();

        ProductDescriptionVarcharMapper productDescriptionVarcharMapper = ProductDescriptionVarcharMapper.builder()
                .name("상품 이름")
                .userCode1("유저 코드1")
                .userCode2("유저 코드2")
                .hsCode("HS 코드")
                .weight("5")
                .volumeX("0")
                .volumeY("0")
                .volumeH("0")
                .productionDate("2020-11-24")
                .limitDate("2021-11-24")
                .sizeInfos(List.of(""))
                .materialInfos(List.of(""))
                .notes(List.of(""))
                .build();

        CreateProductImageMapper productImageMapper = CreateProductImageMapper.builder()
                .images(List.of("image"))
                .isUrls(List.of(true))
                .kinds(List.of("kind"))
                .sortOrders(List.of(0))
                .build();

        // when
        Long createdProductId = productCommandService.create(
                productMapper,
                productDescriptionTextMapper,
                productDescriptionVarcharMapper,
                productImageMapper
        );

        Product product = productQueryRepository.findWithFetchJoinById(createdProductId)
                .orElseThrow(() -> new NoSuchElementException(""));

        ProductDescriptionText productDescriptionText = product.getProductDescriptionText();
        List<ProductDescriptionVarchar> productDescriptionVarchars = product.getProductDescriptionVarchars();
        List<String> keys = List.of("name", "user_code1", "user_code2", "hs_code", "weight", "volume_x",
                "volume_y", "volume_h", "production_date", "limit_date", "size_info", "material_info", "note");
        ProductImage productImage = product.getProductImages().get(0);

        // then
        assertThat(product.getIsSale()).isEqualTo(productMapper.getIsSale());
        assertThat(product.getIsUsed()).isEqualTo(productMapper.getIsUsed());
        assertThat(product.getSupplierId()).isEqualTo(productMapper.getSupplierId());
        assertThat(product.getSupplyPrice()).isEqualTo(productMapper.getSupplyPrice());
        assertThat(product.getRecommendPrice()).isEqualTo(productMapper.getRecommendPrice());
        assertThat(product.getConsumerPrice()).isEqualTo(productMapper.getConsumerPrice());
        assertThat(product.getMaximum()).isEqualTo(productMapper.getMaximum());
        assertThat(product.getMinimum()).isEqualTo(productMapper.getMinimum());
        assertThat(productDescriptionText.getValue()).isEqualTo(productDescriptionTextMapper.getValue());

        IntStream.range(0, keys.size()).forEach(i -> {
            Map<String, String> defaultAndAdditionalMap = productDescriptionVarcharMapper.getDefaultAndAdditionalMap();
            assertThat(productDescriptionVarchars.get(i).getValue()).isEqualTo(defaultAndAdditionalMap.get(keys.get(i)));
        });

        assertThat(productImage.getSortOrder()).isEqualTo(productImageMapper.getProductImageVOList().get(0).getSortOrder());
        assertThat(productImage.getKind()).isEqualTo(productImageMapper.getProductImageVOList().get(0).getKind());
        assertThat(productImage.getImage()).isEqualTo(productImageMapper.getProductImageVOList().get(0).getImage());
        assertThat(productImage.getIsUrl()).isEqualTo(productImageMapper.getProductImageVOList().get(0).getIsUrl());
    }

    @Test
    @DisplayName("Product 애그리거트 수정")
    void updateProductAggregate() {
        // given
        ProductMapper productMapper = ProductMapper.builder()
                .isSale(true)
                .isUsed(true)
                .supplierId(1)
                .supplyPrice(20000.0000)
                .recommendPrice(30000.0000)
                .consumerPrice(30000.0000)
                .maximum(15)
                .minimum(1)
                .build();

        ProductDescriptionTextMapper productDescriptionTextMapper = ProductDescriptionTextMapper.builder()
                .value("descriptionValue")
                .build();

        ProductDescriptionVarcharMapper productDescriptionVarcharMapper = ProductDescriptionVarcharMapper.builder()
                .name("상품 이름")
                .userCode1("유저 코드1")
                .userCode2("유저 코드2")
                .hsCode("HS 코드")
                .weight("5")
                .volumeX("0")
                .volumeY("0")
                .volumeH("0")
                .productionDate("2020-11-24")
                .limitDate("2021-11-24")
                .sizeInfos(List.of(""))
                .materialInfos(List.of(""))
                .notes(List.of(""))
                .build();

        CreateProductImageMapper productImageMapper = CreateProductImageMapper.builder()
                .images(List.of("image"))
                .isUrls(List.of(true))
                .kinds(List.of("kind"))
                .sortOrders(List.of(0))
                .build();

        Long createdProductId = productCommandService.create(
                productMapper,
                productDescriptionTextMapper,
                productDescriptionVarcharMapper,
                productImageMapper
        );


        // when
        ProductMapper updateProductMapper = ProductMapper.builder()
                .id(createdProductId)
                .isSale(true)
                .isUsed(true)
                .supplierId(1)
                .supplyPrice(50000.0000)
                .recommendPrice(55000.0000)
                .consumerPrice(55000.0000)
                .maximum(15)
                .minimum(1)
                .build();

        ProductDescriptionTextMapper updateDescriptionTextMapper = ProductDescriptionTextMapper.builder()
                .value("description 변경")
                .build();

        ProductDescriptionVarcharMapper updateDescriptionVarcharMapper = ProductDescriptionVarcharMapper.builder()
                .name("상품 이름 변경")
                .userCode1("유저 코드1 변경")
                .userCode2("유저 코드2 변경")
                .hsCode("HS 코드")
                .weight("5")
                .volumeX("0")
                .volumeY("0")
                .volumeH("0")
                .productionDate("2020-11-24")
                .limitDate("2021-11-24")
                .sizeInfos(List.of(""))
                .materialInfos(List.of(""))
                .notes(List.of(""))
                .build();

        Product updatedProduct = productCommandService.update(
                updateProductMapper,
                updateDescriptionTextMapper,
                updateDescriptionVarcharMapper
        );

        ProductDescriptionText updatedDescriptionText = updatedProduct.getProductDescriptionText();
        List<ProductDescriptionVarchar> updatedDescriptionVarchars = updatedProduct.getProductDescriptionVarchars();
        List<String> keys = List.of("name", "user_code1", "user_code2", "hs_code", "weight", "volume_x",
                "volume_y", "volume_h", "production_date", "limit_date", "size_info", "material_info", "note");

        // then
        assertThat(updatedProduct.getIsSale()).isEqualTo(updateProductMapper.getIsSale());
        assertThat(updatedProduct.getIsUsed()).isEqualTo(updateProductMapper.getIsUsed());
        assertThat(updatedProduct.getSupplierId()).isEqualTo(updateProductMapper.getSupplierId());
        assertThat(updatedProduct.getSupplyPrice()).isEqualTo(updateProductMapper.getSupplyPrice());
        assertThat(updatedProduct.getRecommendPrice()).isEqualTo(updateProductMapper.getRecommendPrice());
        assertThat(updatedProduct.getConsumerPrice()).isEqualTo(updateProductMapper.getConsumerPrice());
        assertThat(updatedProduct.getMaximum()).isEqualTo(updateProductMapper.getMaximum());
        assertThat(updatedProduct.getMinimum()).isEqualTo(updateProductMapper.getMinimum());
        assertThat(updatedDescriptionText.getValue()).isEqualTo(updateDescriptionTextMapper.getValue());

        IntStream.range(0, keys.size()).forEach(i -> {
            Map<String, String> defaultAndAdditionalMap = updateDescriptionVarcharMapper.getDefaultAndAdditionalMap();
            assertThat(updatedDescriptionVarchars.get(i).getValue()).isEqualTo(defaultAndAdditionalMap.get(keys.get(i)));
        });
    }

}
