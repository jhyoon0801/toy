package com.jhyoon0801.toy.dao;

import com.jhyoon0801.toy.dao.mapper.RandomMoneyMapper;
import com.jhyoon0801.toy.domain.DistributionInfo;
import com.jhyoon0801.toy.domain.RandomMoneyMeta;
import com.jhyoon0801.toy.domain.ReceiveInfo;
import com.jhyoon0801.toy.dto.RandomMoneyInfoDto;
import com.jhyoon0801.toy.dto.ReceiveInfoDto;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class RandomMoneyDao {

    private final int RANDOM_MONEY_LIFE_DURATION_MINUTE = 10080; // 7 days
    private final int RANDOM_MONEY_VALID_DURATION_MINUTE = 10;
    private RandomMoneyMapper randomMoneyMapper;

    public RandomMoneyDao(RandomMoneyMapper randomMoneyMapper){
        this.randomMoneyMapper = randomMoneyMapper;
    }

    public String createRandomMoney(RandomMoneyMeta domain, List<DistributionInfo> distInfoList) {
        int metaInfoSeq = randomMoneyMapper.insertRandomMoneyMeta(domain);
        distInfoList.stream().forEach(e-> e.setMetaInfoSeq(metaInfoSeq));

        randomMoneyMapper.insertRandomMoneyDistribution(distInfoList);
        return domain.getToken();
    }

    public RandomMoneyInfoDto readActiveRandomMoneyInfo(String roomId, String token) {
        return readRandomMoneyInfoByDuration(roomId, token, RANDOM_MONEY_VALID_DURATION_MINUTE);
    }

    public void createTempReceiver(Integer metaSeq, String receiverId) {
        try {
            randomMoneyMapper.insertTempReceiverInfo(metaSeq, receiverId);
        } catch (DuplicateKeyException e) {
        }
    }

    public int getOrderOfReceiver(Integer metaSeq, String receiverId) {
        return randomMoneyMapper.selectOrderOfReceiver(metaSeq, receiverId)+1;
    }

    public ReceiveInfoDto updateRandomMoneyReceiveInfo(Integer metaSeq, String receiverId, Integer order) {
       try {
           randomMoneyMapper.insertRandomMoneyReceiveInfo(metaSeq, receiverId, order);
       } catch (DuplicateKeyException e) {
       }
       return new ReceiveInfoDto(receiverId, randomMoneyMapper.selectRandomMoneyDistAmount(metaSeq, order));
    }

    public RandomMoneyInfoDto readRandomMoneyInfo(String roomId, String token) {
        return readRandomMoneyInfoByDuration(roomId, token, RANDOM_MONEY_LIFE_DURATION_MINUTE);
    }

    private RandomMoneyInfoDto readRandomMoneyInfoByDuration(String roomId, String token, int duration){
        RandomMoneyMeta meta = randomMoneyMapper.selectRandomMoneyMeta(roomId, token, duration);
        if (null == meta) {
            return null;
        }

        List<ReceiveInfo> receiveInfoList = randomMoneyMapper.selectRandomMoneyReceiveInfo(meta.getSeq());
        return transferToRandomMoneyInfoDto(meta, receiveInfoList);
    }

    private RandomMoneyInfoDto transferToRandomMoneyInfoDto(RandomMoneyMeta meta, List<ReceiveInfo> receiveInfoList){
        RandomMoneyInfoDto result = new RandomMoneyInfoDto(meta);
        result.setReceivedMoney(null == receiveInfoList ? 0 : receiveInfoList.stream().map(e -> e.getAmount()).reduce(0, (a, b) -> a + b));
        result.setReceiveInfoDtoList(null == receiveInfoList ? null : receiveInfoList.stream().map(e->{return new ReceiveInfoDto(e.getReceiverId(), e.getAmount());}).collect(Collectors.toList()));
        return result;
    }


}