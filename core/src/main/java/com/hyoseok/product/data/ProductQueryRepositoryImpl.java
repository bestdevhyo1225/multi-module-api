package com.hyoseok.product.data;

import com.hyoseok.product.domain.Product;
import com.hyoseok.product.domain.ProductQueryRepository;
import com.hyoseok.product.usecase.dto.FixedProductPageRequest;
import com.hyoseok.product.usecase.dto.ProductPagination;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.hyoseok.product.domain.QProduct.*;
import static java.util.stream.Collectors.*;

@Repository
public class ProductQueryRepositoryImpl extends QuerydslRepositorySupport implements ProductQueryRepository {

    private final JPAQueryFactory queryFactory;

    public ProductQueryRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Product.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public List<Product> findAllWithFetchJoin() {
        return queryFactory
                .selectFrom(product)
                .leftJoin(product.productDescriptionText).fetchJoin()
                .leftJoin(product.productDescriptionVarchars).fetchJoin()
                .fetch().stream()
                .distinct()
                .collect(toList());
    }

    @Override
    public Optional<Product> findWithFetchJoinById(Long id) {
        Product findProduct = queryFactory
                .selectFrom(product)
                .leftJoin(product.productDescriptionText).fetchJoin()
                .leftJoin(product.productImages).fetchJoin()
                .where(product.id.eq(id))
                .fetchOne();

        return Optional.ofNullable(findProduct);
    }

    public Page<ProductPagination> paginationCount(Pageable pageable) {
        JPQLQuery<ProductPagination> pagination = querydsl().applyPagination(pageable, getJpaQueryProductPagination());

        List<ProductPagination> items = pagination.fetch(); // 데이터 조회

        long totalCount = pagination.fetchCount(); // 전체 Count

        return new PageImpl<>(items, pageable, totalCount);
    }

    public Page<ProductPagination> paginationCountSearchBtn(boolean useSearchBtn, Pageable pageable) {
        JPAQuery<ProductPagination> query = getJpaQueryProductPagination();
        JPQLQuery<ProductPagination> pagination = querydsl().applyPagination(pageable, query);

        // 검색 버튼 사용
        if (useSearchBtn) {
            int fixedPageNo = 10;
            int fixedPageCount = fixedPageNo * pageable.getPageSize(); // 10개 페이지 고정
            return new PageImpl<>(pagination.fetch(), pageable, fixedPageCount);
        }

        long totalCount = pagination.fetchCount();

        Pageable pageRequest = new FixedProductPageRequest(pageable, totalCount);

        return new PageImpl<>(querydsl().applyPagination(pageRequest, query).fetch(), pageRequest, totalCount);
    }

    private JPAQuery<ProductPagination> getJpaQueryProductPagination() {
        return queryFactory
                .select(Projections.fields(ProductPagination.class,
                        product.id,
                        product.isSale,
                        product.isUsed,
                        product.supplyPrice,
                        product.recommendPrice,
                        product.consumerPrice
                ))
                .from(product)
                .orderBy(product.id.desc());
    }

    private Querydsl querydsl() {
        return Objects.requireNonNull(getQuerydsl());
    }

}
