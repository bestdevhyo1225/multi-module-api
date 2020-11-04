package com.hyoseok.api.request.product;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
public class CreateProductRequest {
    @NotNull(message = "isSale은 반드시 입력해야 합니다.")
    private Boolean isSale;
    @NotNull(message = "isUsed는 반드시 입력해야 합니다.")
    private Boolean isUsed;
    @NotNull(message = "supplierId는 반드시 입력해야 합니다.")
    private int supplierId;
    @NotNull(message = "supplyPrice는 반드시 입력해야 합니다.")
    private double supplyPrice;
    @NotNull(message = "recommendPrice는 반드시 입력해야 합니다.")
    private double recommendPrice;
    @NotNull(message = "consumerPrice는 반드시 입력해야 합니다.")
    private double consumerPrice;
    @NotNull(message = "maximum은 반드시 입력해야 합니다.")
    private int maximum;
    @NotNull(message = "minimum은 반드시 입력해야 합니다.")
    private int minimum;
    @NotNull(message = "name은 반드시 입력해야 합니다.")
    private String name;
    @NotNull(message = "userCode1은 반드시 입력해야 합니다.")
    private String userCode1;
    @NotNull(message = "userCode2는 반드시 입력해야 합니다.")
    private String userCode2;
    @NotNull(message = "hsCode는 반드시 입력해야 합니다.")
    private String hsCode;
    @NotNull(message = "weight는 반드시 입력해야 합니다.")
    private String weight;
    @NotNull(message = "volumeX는 반드시 입력해야 합니다.")
    private String volumeX;
    @NotNull(message = "volumeY는 반드시 입력해야 합니다.")
    private String volumeY;
    @NotNull(message = "volumeH는 반드시 입력해야 합니다.")
    private String volumeH;
    @NotNull(message = "productionDate는 반드시 입력해야 합니다.")
    private String productionDate;
    @NotNull(message = "limitDate는 반드시 입력해야 합니다.")
    private String limitDate;
    private List<Boolean> isUrls;
    private List<String> kinds;
    private List<String> images;
    private List<Integer> sortOrders;
    private ProductDescriptionTextVO productDescriptionText;
    private ProductDescriptionVarcharVO productDescriptionVarchar;
}
