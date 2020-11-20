package com.hyoseok.product.usecase;

import com.hyoseok.product.domain.Product;
import com.hyoseok.product.domain.ProductDescriptionText;
import com.hyoseok.product.domain.ProductDescriptionVarchar;
import com.hyoseok.product.domain.ProductQueryRepository;
import com.hyoseok.product.usecase.mapper.CreateProductImageMapper;
import com.hyoseok.product.usecase.mapper.ProductDescriptionTextMapper;
import com.hyoseok.product.usecase.mapper.ProductDescriptionVarcharMapper;
import com.hyoseok.product.usecase.mapper.ProductMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@DisplayName("ProductFacade 테스트")
class ProductFacadeTest {

    @Autowired
    private ProductFacade productFacade;

    @Autowired
    private ProductCommandService productCommandService;

    @Autowired
    private ProductQueryRepository productQueryRepository;

    @Test
    @DisplayName("Product 애그리거트를 수정한다.")
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

        Long productId = productCommandService.create(
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

        productFacade.update(productMapper, productDescTextMapper, productDescVarcharMapper);

        // JPQL을 호출하면, 자동으로 flush 됨
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
