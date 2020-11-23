package com.jhyoon0801.toy.dto;

import com.jhyoon0801.toy.domain.RandomMoneyMeta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RandomMoneyInfoDto extends RandomMoneyMeta {
    private Integer receivedMoney;
    private List<ReceiveInfoDto> receiveInfoDtoList;

    public RandomMoneyInfoDto(RandomMoneyMeta randomMoneyMeta){
        super(randomMoneyMeta);
    }
}
