package com.hyoseok.product.usecase.mapper;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Getter
@NoArgsConstructor
public class UpdateProductImageMapper {
    private final List<ProductImageVO> productImageVOList = new ArrayList<>();

    @Builder
    public UpdateProductImageMapper(List<Long> imageIds,
                                    List<Boolean> isUrls,
                                    List<String> kinds,
                                    List<String> images,
                                    List<Integer> sortOrders) {
        IntStream.range(0, images.size())
                .mapToObj(index ->
                        new ProductImageVO(
                                imageIds.get(index),
                                isUrls.get(index),
                                kinds.get(index),
                                images.get(index),
                                sortOrders.get(index)
                        )
                ).forEach(productImageVOList::add);
    }
}
