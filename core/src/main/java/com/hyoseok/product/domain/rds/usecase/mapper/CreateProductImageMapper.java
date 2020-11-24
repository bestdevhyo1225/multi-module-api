package com.hyoseok.product.domain.rds.usecase.mapper;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Getter
@NoArgsConstructor
public class CreateProductImageMapper {

    private final List<ProductImageVO> productImageVOList = new ArrayList<>();

    @Builder
    public CreateProductImageMapper(List<Boolean> isUrls,
                                    List<String> kinds,
                                    List<String> images,
                                    List<Integer> sortOrders) {
        IntStream.range(0, images.size())
                .mapToObj(index ->
                        new ProductImageVO(
                                isUrls.get(index),
                                kinds.get(index),
                                images.get(index),
                                sortOrders.get(index)
                        )
                ).forEach(productImageVOList::add);
    }

}
