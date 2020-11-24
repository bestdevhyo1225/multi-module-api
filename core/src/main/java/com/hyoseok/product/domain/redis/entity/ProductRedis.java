package com.hyoseok.product.domain.redis.entity;

import com.hyoseok.product.domain.rds.usecase.dto.ProductImageDetailDto;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@RedisHash(value = "product", timeToLive = 60 * 60) // timeToLive는 seconds
public class ProductRedis implements Serializable {

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
    private Map<String, String> productDescriptionText;
    private Map<String, String> productDescriptionVarchar;
    private List<ProductImageDetailDto> productImages;

    public static ProductRedis create(String id,
                                      Boolean isSale,
                                      Boolean isUsed,
                                      int supplierId,
                                      double supplyPrice,
                                      double recommendPrice,
                                      double consumerPrice,
                                      int maximum,
                                      int minimum,
                                      Map<String, String> productDescriptionText,
                                      Map<String, String> productDescriptionVarchar,
                                      List<ProductImageDetailDto> productImages,
                                      LocalDateTime refreshDatetime) {
        ProductRedis productRedis = new ProductRedis();

        productRedis.id = id;
        productRedis.isSale = isSale;
        productRedis.isUsed = isUsed;
        productRedis.supplierId = supplierId;
        productRedis.supplyPrice = supplyPrice;
        productRedis.recommendPrice = recommendPrice;
        productRedis.consumerPrice = consumerPrice;
        productRedis.maximum = maximum;
        productRedis.minimum = minimum;
        productRedis.refreshDatetime = refreshDatetime;

        productRedis.productDescriptionText = productDescriptionText != null
                ? productDescriptionText
                : new HashMap<>();

        productRedis.productDescriptionVarchar = productDescriptionVarchar != null
                ? productDescriptionVarchar
                : new HashMap<>();

        productRedis.productImages = productImages != null
                ? productImages
                : new ArrayList<>();

        return productRedis;
    }

    public void refresh(Boolean isSale,
                        Boolean isUsed,
                        int supplierId,
                        double supplyPrice,
                        double recommendPrice,
                        double consumerPrice,
                        int maximum,
                        int minimum,
                        Map<String, String> productDescriptionText,
                        Map<String, String> productDescriptionVarchar,
                        List<ProductImageDetailDto> productImages,
                        LocalDateTime refreshDatetime) {
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

        this.productDescriptionText.clear();
        this.productDescriptionVarchar.clear();
        this.productImages.clear();

        if (productDescriptionText != null) {
            List<String> keys = new ArrayList<>(productDescriptionText.keySet());

            keys.forEach(key -> this.productDescriptionText.put(key, productDescriptionText.get(key)));
        }

        if (productDescriptionVarchar != null) {
            List<String> keys = new ArrayList<>(productDescriptionVarchar.keySet());

            keys.forEach(key -> this.productDescriptionVarchar.put(key, productDescriptionVarchar.get(key)));
        }

        if (productImages != null) {
            this.productImages.addAll(productImages);
        }
    }

}
