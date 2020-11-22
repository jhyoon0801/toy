package com.jhyoon0801.toy.dto;

import lombok.Data;

@Data
public class ReceiveInfoDto {
    private String receiverId;
    private Integer amount;

    public ReceiveInfoDto(){}

    public ReceiveInfoDto(String receiverId, Integer amount){
        this.receiverId = receiverId;
        this.amount = amount;
    }
}
