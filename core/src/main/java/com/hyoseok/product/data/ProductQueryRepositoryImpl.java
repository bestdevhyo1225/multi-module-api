package com.hyoseok.product.data;

import com.hyoseok.product.domain.Product;
import com.hyoseok.product.domain.ProductQueryRepository;
import com.hyoseok.product.domain.QProduct;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.hyoseok.product.domain.QProduct.*;
import static java.util.stream.Collectors.*;

@Repository
@RequiredArgsConstructor
public class ProductQueryRepositoryImpl implements ProductQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Product> findAllWithFetchJoin() {
        return jpaQueryFactory
                .selectFrom(product)
                .leftJoin(product.productDescriptionText).fetchJoin()
                .leftJoin(product.productDescriptionVarchars).fetchJoin()
                .fetch().stream()
                .distinct()
                .collect(toList());
    }

    @Override
    public Optional<Product> findWithFetchJoinById(Long id) {
        Product findProduct = jpaQueryFactory
                .selectFrom(product)
                .leftJoin(product.productDescriptionText).fetchJoin()
                .leftJoin(product.productImages).fetchJoin()
                .where(product.id.eq(id))
                .fetchOne();

        return Optional.ofNullable(findProduct);
    }

}
