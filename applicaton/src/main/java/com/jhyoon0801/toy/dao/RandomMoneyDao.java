package com.jhyoon0801.toy.dao;

import com.jhyoon0801.toy.dao.mapper.RandomMoneyMapper;
import com.jhyoon0801.toy.domain.DistributionInfo;
import com.jhyoon0801.toy.domain.RandomMoneyMeta;
import com.jhyoon0801.toy.domain.ReceiveInfo;
import com.jhyoon0801.toy.dto.RandomMoneyInfoDto;
import com.jhyoon0801.toy.dto.ReceiveInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class RandomMoneyDao {

    //@Autowired
    private RandomMoneyMapper randomMoneyMapper;

    public String createRandomMoney(RandomMoneyMeta domain, List<DistributionInfo> distInfoList) {
        int metaInfoSeq = randomMoneyMapper.insertRandomMoneyMeta(domain);
        distInfoList.stream().forEach(e-> e.setMetaInfoSeq(metaInfoSeq));

        randomMoneyMapper.insertRandomMoneyDistribution(distInfoList);
        return domain.getToken();
    }

    public void updateRandomMoney(String token, String receiverUserId, String roomId) {

    }

    public RandomMoneyInfoDto readRandomMoneyInfo(String roomId, String token) {
        RandomMoneyMeta meta = randomMoneyMapper.selectRandomMoneyMeta(roomId, token);
        if (null == meta) {
            return null;
        }

        List<ReceiveInfo> receiveInfoList = randomMoneyMapper.selectRandomMoneyReceiveInfo(meta.getSeq());
        return RandomMoneyInfoDto.builder()
                .ownerId(meta.getOwnerId())
                .roomId(meta.getRoomId())
                .totalMoney(meta.getTotalMoney())
                .distributionSize(meta.getDistributionSize())
                .createdTimeStamp(meta.getCreteDatetime())
                .receivedMoney(null == receiveInfoList ? 0 : receiveInfoList.stream().map(e -> e.getAmount()).reduce(0, (a, b) -> a + b))
                .receiveInfoDtoList(null == receiveInfoList ? null : receiveInfoList.stream().map(e->{return new ReceiveInfoDto(e.getReceiverId(), e.getAmount());}).collect(Collectors.toList()))
                .build();
    }
}