package com.jhyoon0801.toy.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RandomMoneyInfoDto {
    private String ownerId;
    private String roomId;
    private Integer totalMoney;
    private Integer distributionSize;
    private String createdTimeStamp;

    private Integer receivedMoney;
    private List<ReceiveInfoDto> receiveInfoDtoList;
}
