package com.hyoseok.product.usecase;

import com.hyoseok.product.domain.*;
import com.hyoseok.product.exception.ErrorMessage;
import com.hyoseok.product.exception.NotFoundProductException;
import com.hyoseok.product.usecase.dto.ProductDetailDto;
import com.hyoseok.product.usecase.dto.ProductPagination;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductQueryService {

    private final ProductQueryRepository productQueryRepository;
    private final ProductRedisRepository productRedisRepository;

    public List<ProductDetailDto> findProducts() {
        return productQueryRepository.findAllWithFetchJoin().stream()
                .map(product -> ProductDetailDto.createProductDetail(
                        product.getId(),
                        product.getIsSale(),
                        product.getIsUsed(),
                        product.getSupplierId(),
                        product.getSupplyPrice(),
                        product.getRecommendPrice(),
                        product.getConsumerPrice(),
                        product.getMaximum(),
                        product.getMinimum(),
                        product.getProductDescriptionText(),
                        product.getProductDescriptionVarchars(),
                        product.getProductImages()
                ))
                .collect(toList());
    }

    public ProductDetailDto findProduct(Long id) {
        ProductCache productCache = productRedisRepository.findById(id.toString()).orElse(null);

        if (productCache != null) {
            return ProductDetailDto.createProductDetailFromProductCache(
                    productCache.getId(),
                    productCache.getIsSale(),
                    productCache.getIsUsed(),
                    productCache.getSupplierId(),
                    productCache.getSupplyPrice(),
                    productCache.getRecommendPrice(),
                    productCache.getConsumerPrice(),
                    productCache.getMaximum(),
                    productCache.getMinimum(),
                    productCache.getProductDescriptionText(),
                    productCache.getProductDescriptionVarchar(),
                    productCache.getProductImages()
            );
        }

        Product product = productQueryRepository.findWithFetchJoinById(id)
                .orElseThrow(() -> new NotFoundProductException(ErrorMessage.NOT_FOUND_PRODUCT_IN_DATABASE));

        ProductDetailDto productDetailDto = ProductDetailDto.createProductDetail(
                product.getId(),
                product.getIsSale(),
                product.getIsUsed(),
                product.getSupplierId(),
                product.getSupplyPrice(),
                product.getRecommendPrice(),
                product.getConsumerPrice(),
                product.getMaximum(),
                product.getMinimum(),
                product.getProductDescriptionText(),
                product.getProductDescriptionVarchars(),
                product.getProductImages()
        );

        productRedisRepository.save(
                ProductCache.create(
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

        return productDetailDto;
    }

    public Product findPureProduct(Long id) {
        return productQueryRepository.findWithFetchJoinById(id)
                .orElseThrow(() -> new NotFoundProductException(ErrorMessage.NOT_FOUND_PRODUCT_IN_DATABASE));
    }

    public Page<ProductPagination> paginationProducts(boolean useSearchBtn, int pageNo, int pageSize) {
        return productQueryRepository.paginationCountSearchBtn(useSearchBtn, PageRequest.of(pageNo - 1, pageSize));
    }

}
