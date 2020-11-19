package com.jhyoon0801.toy.service;

import com.jhyoon0801.toy.dao.RandomMoneyDao;
import com.jhyoon0801.toy.dto.RandomMoneyReq;
import com.jhyoon0801.toy.dto.ReceiveInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RandomMoneyServiceImpl implements RandomMoneyService{

    final private RandomMoneyDao randomMoneyDao;

    public RandomMoneyServiceImpl(@Autowired RandomMoneyDao randomMoneyDao) {
        this.randomMoneyDao = randomMoneyDao;
    }

    @Override
    public String createRandomMoney(String userId, String roomId, RandomMoneyReq req) {
        return null;
    }

    @Override
    public ReceiveInfo receiveRandomMoney(String userId, String roomId, String token) {
        return null;
    }

    @Override
    public List<ReceiveInfo> getRandomMoneyInfo(String userId, String roomId, String token) {
        return null;
    }
}