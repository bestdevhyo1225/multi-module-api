package com.hyoseok.product.usecase;

import com.hyoseok.product.domain.*;
import com.hyoseok.product.usecase.dto.ProductDetail;
import com.hyoseok.product.usecase.dto.ProductPagination;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static java.util.stream.Collectors.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductQueryService {

    private final ProductQueryRepository productQueryRepository;
    private final ProductRedisRepository productRedisRepository;

    public List<ProductDetail> findProducts() {
        return productQueryRepository.findAllWithFetchJoin().stream()
                .map((product) -> ProductDetail.builder()
                        .id(product.getId())
                        .isSale(product.getIsSale())
                        .isUsed(product.getIsUsed())
                        .supplierId(product.getSupplierId())
                        .supplyPrice(product.getSupplyPrice())
                        .recommendPrice(product.getRecommendPrice())
                        .consumerPrice(product.getConsumerPrice())
                        .maximum(product.getMaximum())
                        .minimum(product.getMinimum())
                        .productDescriptionText(product.getProductDescriptionText())
                        .productDescriptionVarchars(product.getProductDescriptionVarchars())
                        .productImages(product.getProductImages())
                        .build())
                .collect(toList());
    }

    public ProductDetail findProduct(Long id) {
        RedisProduct findRedisProduct = productRedisRepository.findById(id.toString()).orElse(null);

        if (findRedisProduct != null) {
            return ProductDetail.builder()
                    .id(Long.parseLong(findRedisProduct.getId()))
                    .isSale(findRedisProduct.getIsSale())
                    .isUsed(findRedisProduct.getIsUsed())
                    .supplierId(findRedisProduct.getSupplierId())
                    .supplyPrice(findRedisProduct.getSupplyPrice())
                    .recommendPrice(findRedisProduct.getRecommendPrice())
                    .consumerPrice(findRedisProduct.getConsumerPrice())
                    .maximum(findRedisProduct.getMaximum())
                    .minimum(findRedisProduct.getMinimum())
                    .build();
        }

        Product product = productQueryRepository.findWithFetchJoinById(id)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 상품입니다."));

        productRedisRepository.save(
                RedisProduct.builder()
                        .id(product.getId().toString())
                        .isSale(product.getIsSale())
                        .isUsed(product.getIsUsed())
                        .supplierId(product.getSupplierId())
                        .supplyPrice(product.getSupplyPrice())
                        .recommendPrice(product.getRecommendPrice())
                        .consumerPrice(product.getConsumerPrice())
                        .maximum(product.getMaximum())
                        .minimum(product.getMinimum())
                        .refreshDatetime(LocalDateTime.now())
                        .build()
        );

        return ProductDetail.builder()
                .id(product.getId())
                .isSale(product.getIsSale())
                .isUsed(product.getIsUsed())
                .supplierId(product.getSupplierId())
                .supplyPrice(product.getSupplyPrice())
                .recommendPrice(product.getRecommendPrice())
                .consumerPrice(product.getConsumerPrice())
                .maximum(product.getMaximum())
                .minimum(product.getMinimum())
                .productDescriptionText(product.getProductDescriptionText())
                .productDescriptionVarchars(product.getProductDescriptionVarchars())
                .productImages(product.getProductImages())
                .build();
    }

    public Product findPureProduct(Long id) {
        return productQueryRepository.findWithFetchJoinById(id)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 상품입니다."));
    }

    public Page<ProductPagination> paginationProducts(boolean useSearchBtn, int pageNo, int pageSize) {
        return productQueryRepository.paginationCountSearchBtn(useSearchBtn, PageRequest.of(pageNo - 1, pageSize));
    }

}
