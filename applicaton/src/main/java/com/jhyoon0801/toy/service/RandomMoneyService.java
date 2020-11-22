package com.jhyoon0801.toy.service;

import com.jhyoon0801.toy.dto.RandomMoneyInfoDto;
import com.jhyoon0801.toy.dto.RandomMoneyReq;
import com.jhyoon0801.toy.dto.ReceiveInfoDto;

public interface RandomMoneyService {
    String createRandomMoney(String userId, String roomId, RandomMoneyReq req);
    ReceiveInfoDto receiveRandomMoney(String userId, String roomId, String token);
    RandomMoneyInfoDto getRandomMoneyInfo(String userId, String roomId, String token);
}
