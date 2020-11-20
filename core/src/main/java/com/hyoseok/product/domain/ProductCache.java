package com.hyoseok.product.domain;

import com.hyoseok.product.usecase.dto.ProductDetail;
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
    private Map<String, String> productDescriptionText;
    private Map<String, String> productDescriptionVarchar;
    private List<ProductImageDetail> productImages;

    public static RedisProduct createRedisProduct(ProductDetail productDetail, LocalDateTime refreshDatetime) {
        RedisProduct redisProduct = new RedisProduct();

        redisProduct.id = productDetail.getId().toString();
        redisProduct.isSale = productDetail.getIsSale();
        redisProduct.isUsed = productDetail.getIsUsed();
        redisProduct.supplierId = productDetail.getSupplierId();
        redisProduct.supplyPrice = productDetail.getSupplyPrice();
        redisProduct.recommendPrice = productDetail.getRecommendPrice();
        redisProduct.consumerPrice = productDetail.getConsumerPrice();
        redisProduct.maximum = productDetail.getMaximum();
        redisProduct.minimum = productDetail.getMinimum();

        redisProduct.productDescriptionText = productDetail.getProductDescriptionText() != null
                ? productDetail.getProductDescriptionText()
                : new HashMap<>();

        redisProduct.productDescriptionVarchar = productDetail.getProductDescriptionVarchar() != null
                ? productDetail.getProductDescriptionVarchar()
                : new HashMap<>();

        redisProduct.productImages = productDetail.getProductImages() != null
                ? productDetail.getProductImages()
                : new ArrayList<>();

        redisProduct.refreshDatetime = refreshDatetime;

        return redisProduct;
    }

//    public void refresh(String id,
//                        Boolean isSale,
//                        Boolean isUsed,
//                        int supplierId,
//                        double supplyPrice,
//                        double recommendPrice,
//                        double consumerPrice,
//                        int maximum,
//                        int minimum,
//                        LocalDateTime refreshDatetime,
//                        Map<String, String> productDescriptionText,
//                        Map<String, String> productDescriptionVarchar,
//                        List<ProductImageDetail> productImages) {
//        // 최신 데이터가 저장된 데이터보다 빠르면 그냥 리턴
//        if (refreshDatetime.isBefore(this.refreshDatetime)) return;
//
//        this.isSale = isSale;
//        this.isUsed = isUsed;
//        this.supplierId = supplierId;
//        this.supplyPrice = supplyPrice;
//        this.recommendPrice = recommendPrice;
//        this.consumerPrice = consumerPrice;
//        this.maximum = maximum;
//        this.minimum = minimum;
//        this.refreshDatetime = refreshDatetime;
//        this.productDescriptionText = productDescriptionText != null ? productDescriptionText : new HashMap<>();
//        this.productDescriptionVarchar = productDescriptionVarchar != null ? productDescriptionVarchar : new HashMap<>();
//        this.productImages = productImages != null ? productImages : new ArrayList<>();
//    }

}
