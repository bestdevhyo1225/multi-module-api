package com.hyoseok.api;

import com.hyoseok.product.application.ProductServiceManager;
import com.hyoseok.product.domain.rds.usecase.ProductCommandService;
import com.hyoseok.product.domain.rds.usecase.ProductQueryService;
import com.hyoseok.product.domain.rds.usecase.dto.ProductDetailDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(AdminApiController.class)
@DisplayName("Admin API 테스트")
public class AdminApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductServiceManager productServiceManager;

    @MockBean
    private ProductQueryService productQueryService;

    @MockBean
    private ProductCommandService productCommandService;

    @Test
    @DisplayName("상품 상세를 조회한다.")
    public void createProduct() throws Exception {
        // given
        ProductDetailDto productDetailDto = ProductDetailDto.createProductDetail(
                1L,
                true,
                true,
                1,
                20000.0000,
                20000.0000,
                30000.0000,
                10,
                1,
                null,
                null,
                null
        );


        given(productServiceManager.findProduct(1L))
                .willReturn(productDetailDto);

        // when
        mockMvc.perform(
                get("/admin/api/v1/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());


        // then
    }

}
