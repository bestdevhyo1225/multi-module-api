package com.hyoseok.point.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@RedisHash("point") // 레디스용 Entity 이며, JPA @Entity 대신 @RedisHash(Key 값)으로 생성한다.
public class Point implements Serializable {

    @Id
    private String id;
    private Long amount;
    private LocalDateTime refreshDatetime;

    @Builder
    public Point(String id, Long amount, LocalDateTime refreshDatetime) {
        this.id = id;
        this.amount = amount;
        this.refreshDatetime = refreshDatetime;
    }

    public void refresh(long amount, LocalDateTime refreshDatetime) {
        // 저장된 데이터보다 최신 데이터일 경우
        if (refreshDatetime.isAfter(this.refreshDatetime)) {
            this.amount = amount;
            this.refreshDatetime = refreshDatetime;
        }
    }

}
