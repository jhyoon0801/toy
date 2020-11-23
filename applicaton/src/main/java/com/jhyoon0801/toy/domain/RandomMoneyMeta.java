package com.jhyoon0801.toy.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RandomMoneyMeta {
    private Integer seq;
    private String ownerId;
    private String roomId;
    private String token;
    private Integer totalMoney;
    private Integer distributionSize;
    private String creteDatetime;

    public RandomMoneyMeta(RandomMoneyMeta randomMoneyMeta) {
        this.seq = randomMoneyMeta.seq;
        this.ownerId = randomMoneyMeta.ownerId;
        this.roomId = randomMoneyMeta.roomId;
        this.token = randomMoneyMeta.token;
        this.totalMoney = randomMoneyMeta.totalMoney;
        this.distributionSize = randomMoneyMeta.distributionSize;
        this.creteDatetime = randomMoneyMeta.creteDatetime;
    }
}