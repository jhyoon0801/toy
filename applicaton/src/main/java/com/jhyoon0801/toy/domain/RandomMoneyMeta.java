package com.jhyoon0801.toy.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RandomMoneyMeta {
    private Integer seq;
    private String ownerId;
    private String roomId;
    private String token;
    private Integer totalMoney;
    private Integer distributionSize;
    private String creteDatetime;
}