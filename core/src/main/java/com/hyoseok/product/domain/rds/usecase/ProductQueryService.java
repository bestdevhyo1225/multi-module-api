package com.hyoseok.product.domain.rds.usecase;

import com.hyoseok.product.domain.rds.entity.Product;
import com.hyoseok.product.domain.rds.entity.ProductQueryRepository;
import com.hyoseok.product.domain.rds.usecase.exception.ErrorMessage;
import com.hyoseok.product.domain.rds.usecase.exception.NotFoundProductException;
import com.hyoseok.product.domain.rds.usecase.dto.ProductPagination;
import com.hyoseok.product.domain.rds.usecase.dto.ProductDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductQueryService {

    private final ProductQueryRepository productQueryRepository;

    public ProductDetailDto findProductDetailDto(Long id) {
        Product product = productQueryRepository.findWithFetchJoinById(id)
                .orElseThrow(() -> new NotFoundProductException(ErrorMessage.NOT_FOUND_PRODUCT_IN_DATABASE));

        return ProductDetailDto.createProductDetail(
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
    }

    public Page<ProductPagination> paginationProducts(boolean useSearchBtn, int pageNo, int pageSize) {
        return productQueryRepository.paginationCountSearchBtn(useSearchBtn, PageRequest.of(pageNo - 1, pageSize));
    }

}
