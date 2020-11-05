package com.hyoseok.api;

import com.hyoseok.api.request.product.CreateProductRequest;
import com.hyoseok.api.request.product.UpdateProductRequest;
import com.hyoseok.api.response.product.CreateProductResponse;
import com.hyoseok.api.response.product.FindProductsResponse;
import com.hyoseok.api.response.product.FindProductResponse;
import com.hyoseok.product.usecase.ProductCommandService;
import com.hyoseok.product.usecase.ProductQueryService;
import com.hyoseok.product.usecase.mapper.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/api/v1/products")
public class AdminApiController {

    private final ProductQueryService productQueryService;
    private final ProductCommandService productCommandService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public FindProductsResponse findProducts() {
        return new FindProductsResponse(productQueryService.findProducts());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FindProductResponse findProduct(@PathVariable("id") Long id) {
        return new FindProductResponse(productQueryService.findProduct(id));
    }

    @PostMapping
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

        Long productId = productCommandService.createProduct(
                productMapper,
                productDescriptionTextMapper,
                productDescriptionVarcharMapper,
                productImageMapper
        );

        return new CreateProductResponse(productId);
    }

    @PatchMapping
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

        productCommandService.updateProduct(
                productMapper,
                productDescriptionTextMapper,
                productDescriptionVarcharMapper
        );
    }

}
