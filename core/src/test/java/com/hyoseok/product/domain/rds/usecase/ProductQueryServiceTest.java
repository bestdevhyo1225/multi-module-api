package com.hyoseok.product.domain.rds.usecase;

import com.hyoseok.product.domain.rds.entity.*;
import com.hyoseok.product.domain.rds.usecase.dto.ProductDetailDto;
import com.hyoseok.product.domain.rds.usecase.dto.ProductImageDetailDto;
import com.hyoseok.product.domain.rds.usecase.dto.ProductPagination;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional(readOnly = true)
@DisplayName("ProductQueryService 테스트")
class ProductQueryServiceTest {

    @Autowired
    private ProductQueryService productQueryService;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("Product 상세 조회")
    void findProductDetailDto() {
        // given
        Product savedProduct = productRepository.save(
                Product.create(
                        true,
                        true,
                        1,
                        20000.0000,
                        30000.0000,
                        30000.0000,
                        10,
                        1,
                        ProductDescriptionText.create("descriptionValue"),
                        List.of(ProductDescriptionVarchar.create("name", "상품 이름")),
                        List.of(ProductImage.create(true, "kind", "image", 0))
                )
        );

        // when
        ProductDetailDto productDetailDto = productQueryService.findProductDetailDto(savedProduct.getId());
        Map<String, String> productDescriptionText = productDetailDto.getProductDescriptionText();
        Map<String, String> productDescriptionVarchar = productDetailDto.getProductDescriptionVarchar();
        List<ProductImageDetailDto> productImages = productDetailDto.getProductImages();

        // then
        assertThat(productDetailDto.getIsSale()).isEqualTo(savedProduct.getIsSale());
        assertThat(productDetailDto.getIsUsed()).isEqualTo(savedProduct.getIsUsed());
        assertThat(productDetailDto.getSupplierId()).isEqualTo(savedProduct.getSupplierId());
        assertThat(productDetailDto.getSupplyPrice()).isEqualTo(savedProduct.getSupplyPrice());
        assertThat(productDetailDto.getRecommendPrice()).isEqualTo(savedProduct.getRecommendPrice());
        assertThat(productDetailDto.getConsumerPrice()).isEqualTo(savedProduct.getConsumerPrice());
        assertThat(productDetailDto.getMaximum()).isEqualTo(savedProduct.getMaximum());
        assertThat(productDetailDto.getMinimum()).isEqualTo(savedProduct.getMinimum());
        assertThat(productDescriptionText.get("description")).contains("descriptionValue");
        assertThat(productDescriptionVarchar.get("name")).contains("상품 이름");
        assertThat(productImages.get(0).getId()).isEqualTo(savedProduct.getProductImages().get(0).getId());
        assertThat(productImages.get(0).getImage()).isEqualTo(savedProduct.getProductImages().get(0).getImage());
        assertThat(productImages.get(0).getKind()).isEqualTo(savedProduct.getProductImages().get(0).getKind());
        assertThat(productImages.get(0).getSortOrder()).isEqualTo(savedProduct.getProductImages().get(0).getSortOrder());
        assertThat(productImages.get(0).isUrl()).isEqualTo(savedProduct.getProductImages().get(0).getIsUrl());
    }

    @Test
    @DisplayName("Product 페이지네이션")
    void paginationProducts() {
        // given
        Product savedProduct = productRepository.save(
                Product.create(
                        true,
                        true,
                        1,
                        20000.0000,
                        30000.0000,
                        30000.0000,
                        10,
                        1,
                        ProductDescriptionText.create("descriptionValue"),
                        List.of(ProductDescriptionVarchar.create("name", "상품 이름")),
                        List.of(ProductImage.create(true, "kind", "image", 0))
                )
        );

        // when
        Page<ProductPagination> productPaginations = productQueryService.paginationProducts(true, 1, 10);
        List<ProductPagination> contents = productPaginations.getContent();
        int totalPages = productPaginations.getTotalPages();
        int number = productPaginations.getNumber();
        int size = productPaginations.getSize();

        // then
        assertThat(size).isEqualTo(10);
        assertThat(number).isZero();
        assertThat(totalPages).isEqualTo(10);
        assertThat(contents.get(0).getId()).isEqualTo(savedProduct.getId());
        assertThat(contents.get(0).getIsSale()).isEqualTo(savedProduct.getIsSale());
        assertThat(contents.get(0).getIsUsed()).isEqualTo(savedProduct.getIsUsed());
        assertThat(contents.get(0).getSupplyPrice()).isEqualTo(savedProduct.getSupplyPrice());
        assertThat(contents.get(0).getRecommendPrice()).isEqualTo(savedProduct.getRecommendPrice());
        assertThat(contents.get(0).getConsumerPrice()).isEqualTo(savedProduct.getConsumerPrice());
    }

}
