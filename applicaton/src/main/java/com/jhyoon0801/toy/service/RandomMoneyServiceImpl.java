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
        return null;
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
        return result;
    }

    private String createToken() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < TOKEN_LENGTH; i++) {
            sb.append(this.TOKEN_ELEMENTS.charAt(ThreadLocalRandom.current().nextInt(TOKEN_ELEMENTS.length())));
        }
        return sb.toString();
    }

    private List<DistributionInfo> createDistributionInfo(Integer totalMoney, Integer distributionSize) {
        List<DistributionInfo> result = new ArrayList();



        return result;
    }
}