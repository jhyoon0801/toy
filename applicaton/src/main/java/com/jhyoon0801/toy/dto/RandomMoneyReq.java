package com.jhyoon0801.toy.dto;

import lombok.Data;

@Data
public class RandomMoneyReq {
    private Long totalMoney;
    private Integer distributionSize;
}
