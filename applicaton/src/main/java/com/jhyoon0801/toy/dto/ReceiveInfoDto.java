package com.jhyoon0801.toy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReceiveInfoDto {
    private String receiverId;
    private Integer amount;
}
