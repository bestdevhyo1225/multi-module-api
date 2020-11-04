package com.hyoseok.product.usecase;

import com.hyoseok.product.data.ProductQueryRepositoryImpl;
import com.hyoseok.product.domain.Product;
import com.hyoseok.product.usecase.dto.ProductDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static java.util.stream.Collectors.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductQueryService {

    private final ProductQueryRepositoryImpl productQueryRepository;

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

}
