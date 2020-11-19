package com.hyoseok.product.usecase;

import com.hyoseok.product.domain.Product;
import com.hyoseok.product.domain.ProductQueryRepository;
import com.hyoseok.product.usecase.dto.ProductDetail;
import com.hyoseok.product.usecase.dto.ProductPagination;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static java.util.stream.Collectors.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductQueryService {

    private final ProductQueryRepository productQueryRepository;

    public List<ProductDetail> findProducts() {
        return productQueryRepository.findAllWithFetchJoin().stream()
                .map(ProductDetail::new)
                .collect(toList());
    }

    public ProductDetail findProduct(Long id) {
        Product product = productQueryRepository.findWithFetchJoinById(id)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 상품입니다."));

        return new ProductDetail(product);
    }

    public Product findPureProduct(Long id) {
        return productQueryRepository.findWithFetchJoinById(id)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 상품입니다."));
    }

    public Page<ProductPagination> paginationProducts(boolean useSearchBtn, int pageNo, int pageSize) {
        return productQueryRepository.paginationCountSearchBtn(useSearchBtn, PageRequest.of(pageNo - 1, pageSize));
    }

}
