package com.jhyoon0801.toy.service;

import com.jhyoon0801.toy.dto.RandomMoneyReq;
import com.jhyoon0801.toy.dto.ReceiveInfo;

import java.util.List;

public interface RandomMoneyService {
    String createRandomMoney(String userId, String roomId, RandomMoneyReq req);
    ReceiveInfo receiveRandomMoney(String userId, String roomId, String token);
    List<ReceiveInfo> getRandomMoneyInfo(String userId, String roomId, String token);
}
