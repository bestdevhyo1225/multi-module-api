package com.hyoseok.product.usecase.mapper;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;

@Getter
@NoArgsConstructor
public class ProductDescriptionVarcharMapper {

    private final HashMap<String, String> defaultAndAdditionalMap = new HashMap<>();

    @Builder
    public ProductDescriptionVarcharMapper(String name,
                                           String userCode1,
                                           String userCode2,
                                           String hsCode,
                                           String weight,
                                           String volumeX,
                                           String volumeY,
                                           String volumeH,
                                           String productionDate,
                                           String limitDate,
                                           List<String> sizeInfos,
                                           List<String> materialInfos,
                                           List<String> notes) {
        defaultAndAdditionalMap.put("name", name);
        defaultAndAdditionalMap.put("user_code1", userCode1);
        defaultAndAdditionalMap.put("user_code2", userCode2);
        defaultAndAdditionalMap.put("hs_code", hsCode);
        defaultAndAdditionalMap.put("weight", weight);
        defaultAndAdditionalMap.put("volume_x", volumeX);
        defaultAndAdditionalMap.put("volume_y", volumeY);
        defaultAndAdditionalMap.put("volume_h", volumeH);
        defaultAndAdditionalMap.put("production_date", productionDate);
        defaultAndAdditionalMap.put("limit_date", limitDate);
        defaultAndAdditionalMap.put("size_info", sizeInfos.get(0));
        defaultAndAdditionalMap.put("material_info", materialInfos.get(0));
        defaultAndAdditionalMap.put("note", notes.get(0));
    }

}
