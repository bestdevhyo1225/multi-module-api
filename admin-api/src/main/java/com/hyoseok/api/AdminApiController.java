package com.hyoseok.api;

import com.hyoseok.api.request.product.CreateProductRequest;
import com.hyoseok.api.request.product.UpdateProductRequest;
import com.hyoseok.api.response.product.CreateProductResponse;
import com.hyoseok.api.response.product.FindProductResponse;
import com.hyoseok.api.response.product.ProductPaginationResponse;
import com.hyoseok.product.application.ProductServiceManager;
import com.hyoseok.product.domain.rds.usecase.mapper.CreateProductImageMapper;
import com.hyoseok.product.domain.rds.usecase.mapper.ProductDescriptionTextMapper;
import com.hyoseok.product.domain.rds.usecase.mapper.ProductDescriptionVarcharMapper;
import com.hyoseok.product.domain.rds.usecase.mapper.ProductMapper;
import com.hyoseok.product.domain.rds.usecase.ProductCommandService;
import com.hyoseok.product.domain.rds.usecase.ProductQueryService;
import com.hyoseok.product.domain.rds.usecase.dto.ProductPagination;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/api")
public class AdminApiController {

    private final ProductQueryService productQueryService;
    private final ProductCommandService productCommandService;
    private final ProductServiceManager productServiceManager;

    @GetMapping("/v1/products/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FindProductResponse findProduct(@PathVariable("id") Long id) {
        return new FindProductResponse(productServiceManager.findProduct(id));
    }

    @GetMapping("/v1/products")
    @ResponseStatus(HttpStatus.OK)
    public ProductPaginationResponse paginationProducts(@RequestParam(value = "useSearchBtn") boolean useSearchBtn,
                                                        @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
                                                        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        Page<ProductPagination> page = productQueryService.paginationProducts(useSearchBtn, pageNo, pageSize);

        return new ProductPaginationResponse(pageNo, pageSize, page.getTotalPages(), page.getContent());
    }

    @PostMapping("v1/products")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateProductResponse createProduct(@Valid @RequestBody CreateProductRequest request) {
        ProductMapper productMapper = ProductMapper.builder()
                .isSale(request.getIsSale())
                .isUsed(request.getIsUsed())
                .supplierId(request.getSupplierId())
                .supplyPrice(request.getSupplyPrice())
                .recommendPrice(request.getRecommendPrice())
                .consumerPrice(request.getConsumerPrice())
                .maximum(request.getMaximum())
                .minimum(request.getMinimum())
                .build();

        ProductDescriptionTextMapper productDescriptionTextMapper = ProductDescriptionTextMapper.builder()
                .value(request.getProductDescriptionText().getDescription())
                .build();

        ProductDescriptionVarcharMapper productDescriptionVarcharMapper = ProductDescriptionVarcharMapper.builder()
                .name(request.getName())
                .userCode1(request.getUserCode1())

                .userCode2(request.getUserCode2())
                .hsCode(request.getHsCode())
                .weight(request.getWeight())
                .volumeX(request.getVolumeX())
                .volumeY(request.getVolumeY())
                .volumeH(request.getVolumeH())
                .productionDate(request.getProductionDate())
                .limitDate(request.getLimitDate())
                .sizeInfos(request.getProductDescriptionVarchar().getSizeInfos())
                .materialInfos(request.getProductDescriptionVarchar().getMaterialInfos())
                .notes(request.getProductDescriptionVarchar().getNotes())
                .build();

        CreateProductImageMapper productImageMapper = CreateProductImageMapper.builder()
                .images(request.getImages())
                .isUrls(request.getIsUrls())
                .kinds(request.getKinds())
                .sortOrders(request.getSortOrders())
                .build();

        Long productId = productCommandService.create(
                productMapper,
                productDescriptionTextMapper,
                productDescriptionVarcharMapper,
                productImageMapper
        );

        return new CreateProductResponse(productId);
    }

    @PatchMapping("v1/products")
    @ResponseStatus(HttpStatus.OK)
    public void updateProduct(@Valid @RequestBody UpdateProductRequest request) {
        ProductMapper productMapper = ProductMapper.builder()
                .id(request.getProductId())
                .isSale(request.getIsSale())
                .isUsed(request.getIsUsed())
                .supplierId(request.getSupplierId())
                .supplyPrice(request.getSupplyPrice())
                .recommendPrice(request.getRecommendPrice())
                .consumerPrice(request.getConsumerPrice())
                .maximum(request.getMaximum())
                .minimum(request.getMinimum())
                .build();

        ProductDescriptionTextMapper productDescriptionTextMapper = ProductDescriptionTextMapper.builder()
                .value(request.getProductDescriptionText().getDescription())
                .build();

        ProductDescriptionVarcharMapper productDescriptionVarcharMapper = ProductDescriptionVarcharMapper.builder()
                .name(request.getName())
                .userCode1(request.getUserCode1())
                .userCode2(request.getUserCode2())
                .hsCode(request.getHsCode())
                .weight(request.getWeight())
                .volumeX(request.getVolumeX())
                .volumeY(request.getVolumeY())
                .volumeH(request.getVolumeH())
                .productionDate(request.getProductionDate())
                .limitDate(request.getLimitDate())
                .sizeInfos(request.getProductDescriptionVarchar().getSizeInfos())
                .materialInfos(request.getProductDescriptionVarchar().getMaterialInfos())
                .notes(request.getProductDescriptionVarchar().getNotes())
                .build();

        productServiceManager.updateProduct(
                productMapper,
                productDescriptionTextMapper,
                productDescriptionVarcharMapper
        );
    }

}
