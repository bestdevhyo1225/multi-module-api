package com.hyoseok.product.domain;

import com.hyoseok.product.usecase.dto.ProductImageDetail;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Getter
@RedisHash(value = "product", timeToLive = 60 * 60) // timeToLive는 seconds
public class RedisProduct implements Serializable {

    @Id
    private String id;
    private Boolean isSale;
    private Boolean isUsed;
    private int supplierId;
    private double supplyPrice;
    private double recommendPrice;
    private double consumerPrice;
    private int maximum;
    private int minimum;
    private LocalDateTime refreshDatetime;
    private final Map<String, String> productDescriptionText = new HashMap<>();
    private final Map<String, String> productDescriptionVarchar = new HashMap<>();
    private List<ProductImageDetail> productImages = new ArrayList<>();

    @Builder
    public RedisProduct(String id,
                        Boolean isSale,
                        Boolean isUsed,
                        int supplierId,
                        double supplyPrice,
                        double recommendPrice,
                        double consumerPrice,
                        int maximum,
                        int minimum,
                        LocalDateTime refreshDatetime) {

        this.id = id;
        this.isSale = isSale;
        this.isUsed = isUsed;
        this.supplierId = supplierId;
        this.supplyPrice = supplyPrice;
        this.recommendPrice = recommendPrice;
        this.consumerPrice = consumerPrice;
        this.maximum = maximum;
        this.minimum = minimum;
        this.refreshDatetime = refreshDatetime;
    }

    public void refresh(Boolean isSale,
                        Boolean isUsed,
                        int supplierId,
                        double supplyPrice,
                        double recommendPrice,
                        double consumerPrice,
                        int maximum,
                        int minimum,
                        LocalDateTime refreshDatetime) {
        // 최신 데이터가 저장된 데이터보다 빠르면 그냥 리턴
        if (refreshDatetime.isBefore(this.refreshDatetime)) return;

        this.isSale = isSale;
        this.isUsed = isUsed;
        this.supplierId = supplierId;
        this.supplyPrice = supplyPrice;
        this.recommendPrice = recommendPrice;
        this.consumerPrice = consumerPrice;
        this.maximum = maximum;
        this.minimum = minimum;
        this.refreshDatetime = refreshDatetime;
    }

}
