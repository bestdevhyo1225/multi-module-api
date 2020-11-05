package com.hyoseok.product.usecase;

import com.hyoseok.product.domain.*;
import com.hyoseok.product.usecase.mapper.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@DisplayName("ProductCommandService 테스트")
class ProductCommandServiceTests {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ProductCommandService productCommandService;

    @Autowired
    private ProductQueryRepository productQueryRepository;

    @Test
    @DisplayName("Product 애그리거트를 저장하고, Id를 반환한다.")
    void createProductSubAggregate() {
        // given
        ProductMapper productMapper = ProductMapper.builder()
                .isSale(true)
                .isUsed(true)
                .supplierId(1)
                .supplyPrice(35000.0000)
                .recommendPrice(35000.0000)
                .consumerPrice(30000.0000)
                .maximum(30)
                .minimum(10)
                .build();

        ProductDescriptionTextMapper productDescTextMapper = ProductDescriptionTextMapper.builder()
                .value("description value")
                .build();

        ProductDescriptionVarcharMapper productDescVarcharMapper = ProductDescriptionVarcharMapper.builder()
                .name("상품1")
                .userCode1("code1")
                .userCode2("code2")
                .hsCode("hs")
                .weight("100")
                .volumeX("10")
                .volumeY("15")
                .volumeH("10")
                .productionDate("2020-10-18")
                .limitDate("2021-10-18")
                .sizeInfos(List.of("size"))
                .materialInfos(List.of("material"))
                .notes(List.of("note"))
                .build();

        CreateProductImageMapper productImageMapper = CreateProductImageMapper.builder()
                .images(List.of("image1", "image2"))
                .isUrls(List.of(false, false))
                .kinds(List.of("kind1", "kind2"))
                .sortOrders(List.of(0, 1))
                .build();

        // when
        Long productId = productCommandService.createProduct(
                productMapper,
                productDescTextMapper,
                productDescVarcharMapper,
                productImageMapper
        );

        Product findProduct = productQueryRepository.findWithFetchJoinById(productId)
                .orElseThrow(() -> new NoSuchElementException(""));

        ProductDescriptionText findProductProductDescriptionText = findProduct.getProductDescriptionText();

        List<ProductDescriptionVarchar> findProductDescriptionVarchars = findProduct.getProductDescriptionVarchars();
        HashMap<String, String> defaultAndAdditionalMap = productDescVarcharMapper.getDefaultAndAdditionalMap();
        List<String> keys = List.of("name", "user_code1", "user_code2", "hs_code", "weight", "volume_x",
                "volume_y", "volume_h", "production_date", "limit_date", "size_info", "material_info", "note");

        List<ProductImage> findProductImages = findProduct.getProductImages();
        List<ProductImageVO> productImageVOList = productImageMapper.getProductImageVOList();

        // then
        assertThat(findProduct.getIsSale()).isEqualTo(productMapper.getIsSale());
        assertThat(findProduct.getIsUsed()).isEqualTo(productMapper.getIsUsed());
        assertThat(findProduct.getSupplierId()).isEqualTo(productMapper.getSupplierId());
        assertThat(findProduct.getSupplyPrice()).isEqualTo(productMapper.getSupplyPrice());
        assertThat(findProduct.getRecommendPrice()).isEqualTo(productMapper.getRecommendPrice());
        assertThat(findProduct.getConsumerPrice()).isEqualTo(productMapper.getConsumerPrice());
        assertThat(findProduct.getMaximum()).isEqualTo(productMapper.getMaximum());
        assertThat(findProduct.getMinimum()).isEqualTo(productMapper.getMinimum());

        assertThat(findProductProductDescriptionText.getValue()).isEqualTo(productDescTextMapper.getValue());

        IntStream.range(0, keys.size()).forEach(i -> {
            assertThat(findProductDescriptionVarchars.get(i).getKey()).isEqualTo(keys.get(i));
            assertThat(findProductDescriptionVarchars.get(i).getValue()).isEqualTo(defaultAndAdditionalMap.get(keys.get(i)));
        });

        IntStream.range(0, findProductImages.size()).forEach(i -> {
            assertThat(findProductImages.get(i).getIsUrl()).isEqualTo(productImageVOList.get(i).getIsUrl());
            assertThat(findProductImages.get(i).getKind()).isEqualTo(productImageVOList.get(i).getKind());
            assertThat(findProductImages.get(i).getImage()).isEqualTo(productImageVOList.get(i).getImage());
            assertThat(findProductImages.get(i).getSortOrder()).isEqualTo(productImageVOList.get(i).getSortOrder());
        });
    }

    @Test
    @DisplayName("Product 애그리거트의 엔티티 필드 값이 변경되면, 수정한다.")
    void updateProduct() {
        // given
        ProductMapper createProductMapper = ProductMapper.builder()
                .isSale(true)
                .isUsed(true)
                .supplierId(1)
                .supplyPrice(35000.0000)
                .recommendPrice(35000.0000)
                .consumerPrice(30000.0000)
                .maximum(30)
                .minimum(10)
                .build();

        ProductDescriptionTextMapper createProductDescTextMapper = ProductDescriptionTextMapper.builder()
                .value("description value")
                .build();

        ProductDescriptionVarcharMapper createProductDescVarcharMapper = ProductDescriptionVarcharMapper.builder()
                .name("상품1")
                .userCode1("code1")
                .userCode2("code2")
                .hsCode("hs")
                .weight("100")
                .volumeX("10")
                .volumeY("15")
                .volumeH("10")
                .productionDate("2020-10-18")
                .limitDate("2021-10-18")
                .sizeInfos(List.of("size"))
                .materialInfos(List.of("material"))
                .notes(List.of("note"))
                .build();

        CreateProductImageMapper createProductImageMapper = CreateProductImageMapper.builder()
                .images(List.of("image1", "image2"))
                .isUrls(List.of(false, false))
                .kinds(List.of("kind1", "kind2"))
                .sortOrders(List.of(0, 1))
                .build();

        Long productId = productCommandService.createProduct(
                createProductMapper,
                createProductDescTextMapper,
                createProductDescVarcharMapper,
                createProductImageMapper
        );

        // when
        ProductMapper productMapper = ProductMapper.builder()
                .id(productId)
                .isSale(false)
                .isUsed(false)
                .supplierId(1)
                .supplyPrice(25000.0000)
                .recommendPrice(25000.0000)
                .consumerPrice(20000.0000)
                .maximum(20)
                .minimum(10)
                .build();

        ProductDescriptionTextMapper productDescTextMapper = ProductDescriptionTextMapper.builder()
                .value("description value")
                .build();

        ProductDescriptionVarcharMapper productDescVarcharMapper = ProductDescriptionVarcharMapper.builder()
                .name("상품 이름 변경")
                .userCode1("code1 변경")
                .userCode2("code2 변경")
                .hsCode("hs 변경")
                .weight("100")
                .volumeX("10")
                .volumeY("15 변경")
                .volumeH("10")
                .productionDate("2020-10-18")
                .limitDate("2021-10-18")
                .sizeInfos(List.of("size"))
                .materialInfos(List.of("material"))
                .notes(List.of("note"))
                .build();

        productCommandService.updateProduct(
                productMapper,
                productDescTextMapper,
                productDescVarcharMapper
        );

        entityManager.flush();
        entityManager.clear();

        Product findProduct = productQueryRepository.findWithFetchJoinById(productId)
                .orElseThrow(() -> new NoSuchElementException(""));

        ProductDescriptionText findProductProductDescriptionText = findProduct.getProductDescriptionText();

        List<ProductDescriptionVarchar> findProductDescriptionVarchars = findProduct.getProductDescriptionVarchars();
        HashMap<String, String> defaultAndAdditionalMap = productDescVarcharMapper.getDefaultAndAdditionalMap();
        List<String> keys = List.of("name", "user_code1", "user_code2", "hs_code", "weight", "volume_x",
                "volume_y", "volume_h", "production_date", "limit_date", "size_info", "material_info", "note");

        // then
        assertThat(findProduct.getIsSale()).isEqualTo(productMapper.getIsSale());
        assertThat(findProduct.getIsUsed()).isEqualTo(productMapper.getIsUsed());
        assertThat(findProduct.getSupplierId()).isEqualTo(productMapper.getSupplierId());
        assertThat(findProduct.getSupplyPrice()).isEqualTo(productMapper.getSupplyPrice());
        assertThat(findProduct.getRecommendPrice()).isEqualTo(productMapper.getRecommendPrice());
        assertThat(findProduct.getConsumerPrice()).isEqualTo(productMapper.getConsumerPrice());
        assertThat(findProduct.getMaximum()).isEqualTo(productMapper.getMaximum());
        assertThat(findProduct.getMinimum()).isEqualTo(productMapper.getMinimum());

        assertThat(findProductProductDescriptionText.getValue()).isEqualTo(productDescTextMapper.getValue());

        IntStream.range(0, keys.size()).forEach(i -> {
            assertThat(findProductDescriptionVarchars.get(i).getKey()).isEqualTo(keys.get(i));
            assertThat(findProductDescriptionVarchars.get(i).getValue()).isEqualTo(defaultAndAdditionalMap.get(keys.get(i)));
        });
    }

    @Test
    @DisplayName("Product 애그리거트의 엔티티 필드가 하나도 변경되지 않았다면, 수정하지 않는다.")
    void notUpdateProduct() {
        // given
        ProductMapper createProductMapper = ProductMapper.builder()
                .isSale(true)
                .isUsed(true)
                .supplierId(1)
                .supplyPrice(35000.0000)
                .recommendPrice(35000.0000)
                .consumerPrice(30000.0000)
                .maximum(30)
                .minimum(10)
                .build();

        ProductDescriptionTextMapper createProductDescTextMapper = ProductDescriptionTextMapper.builder()
                .value("description value")
                .build();

        ProductDescriptionVarcharMapper createProductDescVarcharMapper = ProductDescriptionVarcharMapper.builder()
                .name("상품1")
                .userCode1("code1")
                .userCode2("code2")
                .hsCode("hs")
                .weight("100")
                .volumeX("10")
                .volumeY("15")
                .volumeH("10")
                .productionDate("2020-10-18")
                .limitDate("2021-10-18")
                .sizeInfos(List.of("size"))
                .materialInfos(List.of("material"))
                .notes(List.of("note"))
                .build();

        CreateProductImageMapper createProductImageMapper = CreateProductImageMapper.builder()
                .images(List.of("image1", "image2"))
                .isUrls(List.of(false, false))
                .kinds(List.of("kind1", "kind2"))
                .sortOrders(List.of(0, 1))
                .build();

        Long productId = productCommandService.createProduct(
                createProductMapper,
                createProductDescTextMapper,
                createProductDescVarcharMapper,
                createProductImageMapper
        );

        // when
        ProductMapper productMapper = ProductMapper.builder()
                .id(productId)
                .isSale(true)
                .isUsed(true)
                .supplierId(1)
                .supplyPrice(35000.0000)
                .recommendPrice(35000.0000)
                .consumerPrice(30000.0000)
                .maximum(30)
                .minimum(10)
                .build();

        ProductDescriptionTextMapper productDescTextMapper = ProductDescriptionTextMapper.builder()
                .value("description value")
                .build();

        ProductDescriptionVarcharMapper productDescVarcharMapper = ProductDescriptionVarcharMapper.builder()
                .name("상품1")
                .userCode1("code1")
                .userCode2("code2")
                .hsCode("hs")
                .weight("100")
                .volumeX("10")
                .volumeY("15")
                .volumeH("10")
                .productionDate("2020-10-18")
                .limitDate("2021-10-18")
                .sizeInfos(List.of("size"))
                .materialInfos(List.of("material"))
                .notes(List.of("note"))
                .build();

        productCommandService.updateProduct(
                productMapper,
                productDescTextMapper,
                productDescVarcharMapper
        );

        entityManager.flush();
        entityManager.clear();

        Product findProduct = productQueryRepository.findWithFetchJoinById(productId)
                .orElseThrow(() -> new NoSuchElementException(""));

        ProductDescriptionText findProductProductDescriptionText = findProduct.getProductDescriptionText();

        List<ProductDescriptionVarchar> findProductDescriptionVarchars = findProduct.getProductDescriptionVarchars();
        HashMap<String, String> defaultAndAdditionalMap = productDescVarcharMapper.getDefaultAndAdditionalMap();
        List<String> keys = List.of("name", "user_code1", "user_code2", "hs_code", "weight", "volume_x",
                "volume_y", "volume_h", "production_date", "limit_date", "size_info", "material_info", "note");

        // then
        assertThat(findProduct.getIsSale()).isEqualTo(productMapper.getIsSale());
        assertThat(findProduct.getIsUsed()).isEqualTo(productMapper.getIsUsed());
        assertThat(findProduct.getSupplierId()).isEqualTo(productMapper.getSupplierId());
        assertThat(findProduct.getSupplyPrice()).isEqualTo(productMapper.getSupplyPrice());
        assertThat(findProduct.getRecommendPrice()).isEqualTo(productMapper.getRecommendPrice());
        assertThat(findProduct.getConsumerPrice()).isEqualTo(productMapper.getConsumerPrice());
        assertThat(findProduct.getMaximum()).isEqualTo(productMapper.getMaximum());
        assertThat(findProduct.getMinimum()).isEqualTo(productMapper.getMinimum());

        assertThat(findProductProductDescriptionText.getValue()).isEqualTo(productDescTextMapper.getValue());

        IntStream.range(0, keys.size()).forEach(i -> {
            assertThat(findProductDescriptionVarchars.get(i).getKey()).isEqualTo(keys.get(i));
            assertThat(findProductDescriptionVarchars.get(i).getValue()).isEqualTo(defaultAndAdditionalMap.get(keys.get(i)));
        });
    }

}
