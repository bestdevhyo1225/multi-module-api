package com.hyoseok.product.data;

import com.hyoseok.product.domain.*;
import com.hyoseok.product.usecase.dto.ProductPagination;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("ProductQueryRepository 테스트")
class ProductQueryRepositoryTests {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductQueryRepository productQueryRepository;

    @BeforeEach
    void setUp() {
        for (int i = 1; i <= 30; i++) {
            Boolean isSale = true;
            Boolean isUsed = true;
            int supplierId = 1;
            double supplyPrice = 30000.0000;
            double recommendPrice = 30000.0000;
            double consumerPrice = 20000.0000;
            int maximum = 30;
            int minimum = 10;
            ProductDescriptionText productDescriptionText = ProductDescriptionText.create("text" + i);
            List<ProductDescriptionVarchar> productDescriptionVarchars = List.of(ProductDescriptionVarchar.create("key", "value" + i));
            List<ProductImage> productImages = List.of(ProductImage.create(false, "kind" + i, "image" + i, 0));

            Product product = Product.create(
                    isSale,
                    isUsed,
                    supplierId,
                    supplyPrice,
                    recommendPrice,
                    consumerPrice,
                    maximum,
                    minimum,
                    productDescriptionText,
                    productDescriptionVarchars,
                    productImages
            );

            productRepository.save(product);
        }
    }

    @AfterEach
    void tearDown() {
        System.out.println("--------- tearDown ---------");
        productRepository.deleteAll();
    }

    @Test
    void count_쿼리() {
        Page<ProductPagination> page = productQueryRepository.paginationCount(PageRequest.of(1, 10));
        List<ProductPagination> content = page.getContent();

        assertThat(page.getTotalElements()).isEqualTo(30);
        assertThat(page.getTotalPages()).isEqualTo(3);
        assertThat(content).hasSize(10);
    }

    @Test
    void 검색_버튼_사용시_10개_페이지_리턴() {
        boolean useSearchBtn = true;
        Pageable pageable = PageRequest.of(1, 10);
        Page<ProductPagination> page = productQueryRepository.paginationCountSearchBtn(useSearchBtn, pageable);

        assertThat(page.getTotalElements()).isEqualTo(100);
    }

    @Test
    void 페이지_버튼_사용시_실제_페이지_건수_리턴() {
        boolean useSearchBtn = false;
        Pageable pageable = PageRequest.of(3, 10);
        Page<ProductPagination> page = productQueryRepository.paginationCountSearchBtn(useSearchBtn, pageable);

        assertThat(page.getTotalElements()).isEqualTo(30);
    }

}
