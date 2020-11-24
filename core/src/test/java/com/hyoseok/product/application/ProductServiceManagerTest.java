package com.hyoseok.product.application;

import com.hyoseok.product.domain.rds.usecase.ProductCommandService;
import com.hyoseok.product.domain.rds.usecase.ProductQueryService;
import com.hyoseok.product.domain.rds.usecase.dto.ProductDetailDto;
import com.hyoseok.product.domain.rds.usecase.dto.ProductImageDetailDto;
import com.hyoseok.product.domain.rds.usecase.mapper.CreateProductImageMapper;
import com.hyoseok.product.domain.rds.usecase.mapper.ProductDescriptionTextMapper;
import com.hyoseok.product.domain.rds.usecase.mapper.ProductDescriptionVarcharMapper;
import com.hyoseok.product.domain.rds.usecase.mapper.ProductMapper;
import com.hyoseok.product.domain.redis.entity.ProductRedis;
import com.hyoseok.product.domain.redis.usecase.ProductRedisService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional(readOnly = true)
@DisplayName("ProductServiceManager 테스트")
class ProductServiceManagerTest {

    @Autowired
    private ProductServiceManager productServiceManager;

    @Autowired
    private ProductCommandService productCommandService;

    @Autowired
    private ProductQueryService productQueryService;

    @Autowired
    private ProductRedisService productRedisService;

    @Test
    @DisplayName("Product 상세 내역이 Redis에 있다면, 조회한다.")
    void findProductInRedis() {
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

        ProductDetailDto productDetailDto = productQueryService.findProductDetailDto(createdProductId);

        productRedisService.createOrUpdate(
                ProductRedis.create(
                        productDetailDto.getId().toString(),
                        productDetailDto.getIsSale(),
                        productDetailDto.getIsUsed(),
                        productDetailDto.getSupplierId(),
                        productDetailDto.getSupplyPrice(),
                        productDetailDto.getRecommendPrice(),
                        productDetailDto.getConsumerPrice(),
                        productDetailDto.getMaximum(),
                        productDetailDto.getMinimum(),
                        productDetailDto.getProductDescriptionText(),
                        productDetailDto.getProductDescriptionVarchar(),
                        productDetailDto.getProductImages(),
                        LocalDateTime.now()
                )
        );

        // when
        ProductDetailDto product = productServiceManager.findProduct(createdProductId);
        Map<String, String> productDescriptionText = product.getProductDescriptionText();
        Map<String, String> productDescriptionVarchar = product.getProductDescriptionVarchar();
        Map<String, String> defaultAndAdditionalMap = productDescriptionVarcharMapper.getDefaultAndAdditionalMap();
        List<ProductImageDetailDto> productImages = product.getProductImages();

        // then
        assertThat(product.getIsSale()).isEqualTo(productMapper.getIsSale());
        assertThat(product.getIsUsed()).isEqualTo(productMapper.getIsUsed());
        assertThat(product.getSupplierId()).isEqualTo(productMapper.getSupplierId());
        assertThat(product.getSupplyPrice()).isEqualTo(productMapper.getSupplyPrice());
        assertThat(product.getRecommendPrice()).isEqualTo(productMapper.getRecommendPrice());
        assertThat(product.getConsumerPrice()).isEqualTo(productMapper.getConsumerPrice());
        assertThat(product.getMaximum()).isEqualTo(productMapper.getMaximum());
        assertThat(product.getMinimum()).isEqualTo(productMapper.getMinimum());
        assertThat(productDescriptionText.get("description")).contains(productDescriptionTextMapper.getValue());
        assertThat(productDescriptionVarchar.get("name")).contains(defaultAndAdditionalMap.get("name"));
        assertThat(productImages.get(0).getImage()).isEqualTo(productImageMapper.getProductImageVOList().get(0).getImage());
        assertThat(productImages.get(0).getKind()).isEqualTo(productImageMapper.getProductImageVOList().get(0).getKind());
        assertThat(productImages.get(0).getSortOrder()).isEqualTo(productImageMapper.getProductImageVOList().get(0).getSortOrder());
        assertThat(productImages.get(0).isUrl()).isEqualTo(productImageMapper.getProductImageVOList().get(0).getIsUrl());
    }

    @Test
    @DisplayName("Product 상세 내역이 Redis에 없다면, RDS에서 조회한 후, Redis에 추가한다.")
    void findProductInRDS() {
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
                .weight("15")
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
        ProductDetailDto product = productServiceManager.findProduct(createdProductId);
        Map<String, String> productDescriptionText = product.getProductDescriptionText();
        Map<String, String> productDescriptionVarchar = product.getProductDescriptionVarchar();
        Map<String, String> defaultAndAdditionalMap = productDescriptionVarcharMapper.getDefaultAndAdditionalMap();
        List<ProductImageDetailDto> productImages = product.getProductImages();

        // then
        assertThat(product.getIsSale()).isEqualTo(productMapper.getIsSale());
        assertThat(product.getIsUsed()).isEqualTo(productMapper.getIsUsed());
        assertThat(product.getSupplierId()).isEqualTo(productMapper.getSupplierId());
        assertThat(product.getSupplyPrice()).isEqualTo(productMapper.getSupplyPrice());
        assertThat(product.getRecommendPrice()).isEqualTo(productMapper.getRecommendPrice());
        assertThat(product.getConsumerPrice()).isEqualTo(productMapper.getConsumerPrice());
        assertThat(product.getMaximum()).isEqualTo(productMapper.getMaximum());
        assertThat(product.getMinimum()).isEqualTo(productMapper.getMinimum());
        assertThat(productDescriptionText.get("description")).contains(productDescriptionTextMapper.getValue());
        assertThat(productDescriptionVarchar.get("name")).contains(defaultAndAdditionalMap.get("name"));
        assertThat(productDescriptionVarchar.get("weight")).contains(defaultAndAdditionalMap.get("weight"));
        assertThat(productImages.get(0).getImage()).isEqualTo(productImageMapper.getProductImageVOList().get(0).getImage());
        assertThat(productImages.get(0).getKind()).isEqualTo(productImageMapper.getProductImageVOList().get(0).getKind());
        assertThat(productImages.get(0).getSortOrder()).isEqualTo(productImageMapper.getProductImageVOList().get(0).getSortOrder());
        assertThat(productImages.get(0).isUrl()).isEqualTo(productImageMapper.getProductImageVOList().get(0).getIsUrl());
    }

    @Test
    @DisplayName("Product가 변경되었고, Redis에 Product 데이터가 있다면 갱신한다.")
    void refreshProduct() {
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

        ProductDetailDto productDetailDto = productQueryService.findProductDetailDto(createdProductId);

        productRedisService.createOrUpdate(
                ProductRedis.create(
                        productDetailDto.getId().toString(),
                        productDetailDto.getIsSale(),
                        productDetailDto.getIsUsed(),
                        productDetailDto.getSupplierId(),
                        productDetailDto.getSupplyPrice(),
                        productDetailDto.getRecommendPrice(),
                        productDetailDto.getConsumerPrice(),
                        productDetailDto.getMaximum(),
                        productDetailDto.getMinimum(),
                        productDetailDto.getProductDescriptionText(),
                        productDetailDto.getProductDescriptionVarchar(),
                        productDetailDto.getProductImages(),
                        LocalDateTime.now()
                )
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

        productServiceManager.updateProduct(
                updateProductMapper,
                updateDescriptionTextMapper,
                updateDescriptionVarcharMapper
        );

        ProductRedis productRedis = productRedisService.findProductRedis(createdProductId.toString());
        Map<String, String> productDescriptionText = productRedis.getProductDescriptionText();
        Map<String, String> productDescriptionVarchar = productRedis.getProductDescriptionVarchar();

        // then
        assertThat(productRedis.getIsSale()).isEqualTo(updateProductMapper.getIsSale());
        assertThat(productRedis.getIsUsed()).isEqualTo(updateProductMapper.getIsUsed());
        assertThat(productRedis.getSupplierId()).isEqualTo(updateProductMapper.getSupplierId());
        assertThat(productRedis.getSupplyPrice()).isEqualTo(updateProductMapper.getSupplyPrice());
        assertThat(productRedis.getRecommendPrice()).isEqualTo(updateProductMapper.getRecommendPrice());
        assertThat(productRedis.getConsumerPrice()).isEqualTo(updateProductMapper.getConsumerPrice());
        assertThat(productRedis.getMaximum()).isEqualTo(updateProductMapper.getMaximum());
        assertThat(productRedis.getMinimum()).isEqualTo(updateProductMapper.getMinimum());
        assertThat(productDescriptionText.get("description")).contains("description 변경");
        assertThat(productDescriptionVarchar.get("name")).contains("상품 이름 변경");
        assertThat(productDescriptionVarchar.get("user_code1")).contains("유저 코드1 변경");
        assertThat(productDescriptionVarchar.get("user_code2")).contains("유저 코드2 변경");
    }

}
