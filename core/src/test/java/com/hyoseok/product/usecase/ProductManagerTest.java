package com.hyoseok.product.usecase;

import com.hyoseok.product.usecase.mapper.CreateProductImageMapper;
import com.hyoseok.product.usecase.mapper.ProductDescriptionTextMapper;
import com.hyoseok.product.usecase.mapper.ProductDescriptionVarcharMapper;
import com.hyoseok.product.usecase.mapper.ProductMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@DisplayName("ProductManger 테스트")
class ProductManagerTest {

    @Autowired
    private ProductManager productManager;

    @Autowired
    private ProductCommandService productCommandService;

    @Test
    @DisplayName("Product Aggregate 업데이트")
    void update() {
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

        productManager.update(productMapper, productDescTextMapper, productDescVarcharMapper);
    }

}
