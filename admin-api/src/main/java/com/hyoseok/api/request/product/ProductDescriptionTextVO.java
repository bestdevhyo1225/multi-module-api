package com.hyoseok.api.request.product;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class ProductDescriptionTextVO {
    @NotNull(message = "description는 반드시 입력해야 합니다.")
    private String description;
}
