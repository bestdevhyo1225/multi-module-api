package com.hyoseok.product.data;

import com.hyoseok.product.domain.rds.entity.*;
import com.hyoseok.product.domain.rds.usecase.dto.ProductPagination;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional(readOnly = true)
@DisplayName("ProductQueryRepository 클래스 테스트")
class ProductQueryRepositoryImplTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductQueryRepository productQueryRepository;

    @Test
    @DisplayName("Product 애그리거트 - 리스트 조회")
    void findAllWithFetchJoin() {
        // given
        Product product = productRepository.save(
                Product.create(
                        true,
                        true,
                        1,
                        20000.0000,
                        30000.0000,
                        30000.0000,
                        10,
                        1,
                        ProductDescriptionText.create("description"),
                        List.of(ProductDescriptionVarchar.create("name", "상품 이름")),
                        List.of(ProductImage.create(true, "kind", "image", 0))
                )
        );

        // when
        List<Product> products = productQueryRepository.findAllWithFetchJoin();
        ProductDescriptionText productDescriptionText = products.get(0).getProductDescriptionText();
        ProductDescriptionVarchar productDescriptionVarchar = products.get(0).getProductDescriptionVarchars().get(0);
        ProductImage productImage = products.get(0).getProductImages().get(0);

        // then
        assertThat(products.get(0).getIsSale()).isEqualTo(product.getIsSale());
        assertThat(products.get(0).getIsUsed()).isEqualTo(product.getIsUsed());
        assertThat(products.get(0).getSupplierId()).isEqualTo(product.getSupplierId());
        assertThat(products.get(0).getSupplyPrice()).isEqualTo(product.getSupplyPrice());
        assertThat(products.get(0).getRecommendPrice()).isEqualTo(product.getRecommendPrice());
        assertThat(products.get(0).getConsumerPrice()).isEqualTo(product.getConsumerPrice());
        assertThat(products.get(0).getMaximum()).isEqualTo(product.getMaximum());
        assertThat(products.get(0).getMinimum()).isEqualTo(product.getMinimum());
        assertThat(productDescriptionText.getKey()).isEqualTo(product.getProductDescriptionText().getKey());
        assertThat(productDescriptionText.getValue()).isEqualTo(product.getProductDescriptionText().getValue());
        assertThat(productDescriptionVarchar.getKey()).isEqualTo(product.getProductDescriptionVarchars().get(0).getKey());
        assertThat(productDescriptionVarchar.getValue()).isEqualTo(product.getProductDescriptionVarchars().get(0).getValue());
        assertThat(productImage.getImage()).isEqualTo(product.getProductImages().get(0).getImage());
        assertThat(productImage.getIsUrl()).isEqualTo(product.getProductImages().get(0).getIsUrl());
        assertThat(productImage.getKind()).isEqualTo(product.getProductImages().get(0).getKind());
        assertThat(productImage.getSortOrder()).isEqualTo(product.getProductImages().get(0).getSortOrder());
    }

    @Test
    @DisplayName("Product 애그리거트 - 개별 조회")
    void findWithFetchJoinById() {
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
                        ProductDescriptionText.create("description"),
                        List.of(ProductDescriptionVarchar.create("name", "상품 이름")),
                        List.of(ProductImage.create(true, "kind", "image", 0))
                )
        );

        // when
        Product product = productQueryRepository.findWithFetchJoinById(savedProduct.getId())
                .orElseThrow(() -> new NoSuchElementException(""));
        ProductDescriptionText productDescriptionText = product.getProductDescriptionText();
        ProductDescriptionVarchar productDescriptionVarchar = product.getProductDescriptionVarchars().get(0);
        ProductImage productImage = product.getProductImages().get(0);

        // then
        assertThat(product.getIsSale()).isEqualTo(savedProduct.getIsSale());
        assertThat(product.getIsUsed()).isEqualTo(savedProduct.getIsUsed());
        assertThat(product.getSupplierId()).isEqualTo(savedProduct.getSupplierId());
        assertThat(product.getSupplyPrice()).isEqualTo(savedProduct.getSupplyPrice());
        assertThat(product.getRecommendPrice()).isEqualTo(savedProduct.getRecommendPrice());
        assertThat(product.getConsumerPrice()).isEqualTo(savedProduct.getConsumerPrice());
        assertThat(product.getMaximum()).isEqualTo(savedProduct.getMaximum());
        assertThat(product.getMinimum()).isEqualTo(savedProduct.getMinimum());
        assertThat(productDescriptionText.getKey()).isEqualTo(savedProduct.getProductDescriptionText().getKey());
        assertThat(productDescriptionText.getValue()).isEqualTo(savedProduct.getProductDescriptionText().getValue());
        assertThat(productDescriptionVarchar.getKey()).isEqualTo(savedProduct.getProductDescriptionVarchars().get(0).getKey());
        assertThat(productDescriptionVarchar.getValue()).isEqualTo(savedProduct.getProductDescriptionVarchars().get(0).getValue());
        assertThat(productImage.getImage()).isEqualTo(savedProduct.getProductImages().get(0).getImage());
        assertThat(productImage.getIsUrl()).isEqualTo(savedProduct.getProductImages().get(0).getIsUrl());
        assertThat(productImage.getKind()).isEqualTo(savedProduct.getProductImages().get(0).getKind());
        assertThat(productImage.getSortOrder()).isEqualTo(savedProduct.getProductImages().get(0).getSortOrder());
    }

    @Test
    @DisplayName("Product 페이지네이션 - 카운트")
    void paginationCount() {
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
                        ProductDescriptionText.create("description"),
                        List.of(ProductDescriptionVarchar.create("name", "상품 이름")),
                        List.of(ProductImage.create(true, "kind", "image", 0))
                )
        );

        // when
        Page<ProductPagination> productPaginations = productQueryRepository.paginationCount(PageRequest.of(0, 10));
        List<ProductPagination> contents = productPaginations.getContent();
        int totalPages = productPaginations.getTotalPages();
        int number = productPaginations.getNumber();
        int size = productPaginations.getSize();

        // then
        assertThat(size).isEqualTo(10);
        assertThat(number).isZero();
        assertThat(totalPages).isEqualTo(1);
        assertThat(contents.get(0).getId()).isEqualTo(savedProduct.getId());
        assertThat(contents.get(0).getIsSale()).isEqualTo(savedProduct.getIsSale());
        assertThat(contents.get(0).getIsUsed()).isEqualTo(savedProduct.getIsUsed());
        assertThat(contents.get(0).getSupplyPrice()).isEqualTo(savedProduct.getSupplyPrice());
        assertThat(contents.get(0).getRecommendPrice()).isEqualTo(savedProduct.getRecommendPrice());
        assertThat(contents.get(0).getConsumerPrice()).isEqualTo(savedProduct.getConsumerPrice());
    }

    @Test
    @DisplayName("Product 페이지네이션 - 검색 버튼 클릭")
    void paginationCountUseSearchBtn1() {
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
                        ProductDescriptionText.create("description"),
                        List.of(ProductDescriptionVarchar.create("name", "상품 이름")),
                        List.of(ProductImage.create(true, "kind", "image", 0))
                )
        );

        // when
        Page<ProductPagination> productPaginations = productQueryRepository
                .paginationCountSearchBtn(true, PageRequest.of(0, 10));
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

    @Test
    @DisplayName("Product 페이지네이션 - 페이지 버튼 클릭")
    void paginationCountUseSearchBtn2() {
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
                        ProductDescriptionText.create("description"),
                        List.of(ProductDescriptionVarchar.create("name", "상품 이름")),
                        List.of(ProductImage.create(true, "kind", "image", 0))
                )
        );

        // when
        Page<ProductPagination> productPaginations = productQueryRepository
                .paginationCountSearchBtn(false, PageRequest.of(0, 10));
        List<ProductPagination> contents = productPaginations.getContent();
        int totalPages = productPaginations.getTotalPages();
        int number = productPaginations.getNumber();
        int size = productPaginations.getSize();

        // then
        assertThat(size).isEqualTo(10);
        assertThat(number).isZero();
        assertThat(totalPages).isEqualTo(1);
        assertThat(contents.get(0).getId()).isEqualTo(savedProduct.getId());
        assertThat(contents.get(0).getIsSale()).isEqualTo(savedProduct.getIsSale());
        assertThat(contents.get(0).getIsUsed()).isEqualTo(savedProduct.getIsUsed());
        assertThat(contents.get(0).getSupplyPrice()).isEqualTo(savedProduct.getSupplyPrice());
        assertThat(contents.get(0).getRecommendPrice()).isEqualTo(savedProduct.getRecommendPrice());
        assertThat(contents.get(0).getConsumerPrice()).isEqualTo(savedProduct.getConsumerPrice());
    }

}
