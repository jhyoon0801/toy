package com.jhyoon0801.toy.service;

import com.jhyoon0801.toy.contant.ErrorStatus;
import com.jhyoon0801.toy.dao.RandomMoneyDao;
import com.jhyoon0801.toy.domain.DistributionInfo;
import com.jhyoon0801.toy.domain.RandomMoneyMeta;
import com.jhyoon0801.toy.dto.RandomMoneyInfoDto;
import com.jhyoon0801.toy.dto.RandomMoneyReq;
import com.jhyoon0801.toy.dto.ReceiveInfoDto;
import com.jhyoon0801.toy.exception.RandomMoneyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class RandomMoneyServiceImpl implements RandomMoneyService {

    private final String TOKEN_ELEMENTS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final int TOKEN_LENGTH = 3;
    private final int TOKEN_GENERATE_RETRY = 3;
    private final RandomMoneyDao randomMoneyDao;

    public RandomMoneyServiceImpl(@Autowired RandomMoneyDao randomMoneyDao) {this.randomMoneyDao = randomMoneyDao;}

    @Override
    public String createRandomMoney(String userId, String roomId, RandomMoneyReq req) {

        List<DistributionInfo> distributionInfoList = createDistributionInfo(req.getTotalMoney(), req.getDistributionSize());

        for (int cnt = 0; cnt < TOKEN_GENERATE_RETRY; cnt++) {
            try{
                return randomMoneyDao.createRandomMoney(RandomMoneyMeta.builder()
                        .ownerId(userId)
                        .roomId(roomId)
                        .totalMoney(req.getTotalMoney())
                        .distributionSize(req.getDistributionSize())
                        .token(createToken()).build(), distributionInfoList);
            } catch (DuplicateKeyException ex) {
                // logging
            }
        }
        return null;
    }

    @Override
    public ReceiveInfoDto receiveRandomMoney(String userId, String roomId, String token) {
        if(!isParticipant(roomId, userId)){
            throw new RandomMoneyException(ErrorStatus.NOT_PARTICIPANT, "Request from not allowed user");
        }

        RandomMoneyInfoDto meta = randomMoneyDao.readActiveRandomMoneyInfo(roomId, token);
        if(null == meta) {
            throw new RandomMoneyException(ErrorStatus.NOT_EXIST_TOKEN, "Don't exist any valid random money");
        }
        if(meta.getOwnerId().equals(userId)){
            throw new RandomMoneyException(ErrorStatus.NOT_ALLOWED_FOR_OWNER, "Request from owner");
        }
        if(!meta.getReceiveInfoDtoList().stream().filter(e->e.getReceiverId().equals(userId)).findFirst().isPresent()){
            throw new RandomMoneyException(ErrorStatus.ALREADY_RECEIVE, "You have already received this random money");
        }

        randomMoneyDao.createTempReceiver(meta.getSeq(), userId);
        return randomMoneyDao.updateRandomMoneyReceiveInfo(meta.getSeq(), userId, randomMoneyDao.getOrderOfReceiver(meta.getSeq(), userId));
    }

    @Override
    public RandomMoneyInfoDto getRandomMoneyInfo(String userId, String roomId, String token) {
        RandomMoneyInfoDto result = randomMoneyDao.readRandomMoneyInfo(roomId, token);
        if(null == result) {
            throw new RandomMoneyException(ErrorStatus.NOT_EXIST_TOKEN, "Don't exist any valid random money");
        }
        if(!result.getOwnerId().equals(userId)){
            throw new RandomMoneyException(ErrorStatus.NOT_OWNER_ID, "Request from not allowed user");
        }

        result.setSeq(null);
        return result;
    }

    private String createToken() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < TOKEN_LENGTH; i++) {
            sb.append(this.TOKEN_ELEMENTS.charAt(ThreadLocalRandom.current().nextInt(TOKEN_ELEMENTS.length())));
        }
        return sb.toString();
    }

    // TODO : Modify distribution logic
    private List<DistributionInfo> createDistributionInfo(final Integer totalMoney, final Integer distributionSize) {
        List<DistributionInfo> result = new ArrayList();
        Integer tmpMoney = totalMoney;
        for(int i = 0; i<distributionSize-1; i++){
            Integer amount = ThreadLocalRandom.current().nextInt(distributionSize-i, tmpMoney-distributionSize+i);
            result.add(DistributionInfo.builder().distSeq(i+1).amount(amount).build());
            tmpMoney -= amount;
        }
        result.add(DistributionInfo.builder().distSeq(distributionSize).amount(tmpMoney).build());
        return result;
    }

    private boolean isParticipant(String roomId, String userId){
        // 받기 API를 호출한 사용자가 해당 채팅방이 참여자인지 조회하는 서비스는 별도로 있을 것이라 가정하고, 현재 요청한 사용자들은 모두 해당 방에 포함되어 있다고 가정
        return true;
    }
}