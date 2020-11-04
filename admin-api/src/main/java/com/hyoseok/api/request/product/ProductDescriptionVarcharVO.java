package com.hyoseok.api.request.product;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@NoArgsConstructor
public class ProductDescriptionVarcharVO {
    private List<String> sizeInfos;
    private List<String> materialInfos;
    private List<String> notes;
}
