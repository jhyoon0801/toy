package com.jhyoon0801.toy.controller;

import com.jhyoon0801.toy.contant.ErrorStatus;
import com.jhyoon0801.toy.contant.ToyException;
import com.jhyoon0801.toy.dto.RandomMoneyReq;
import com.jhyoon0801.toy.dto.RandomMoneyRes;
import com.jhyoon0801.toy.dto.Result;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RandomMoneyControllerTest {

    @Autowired
    private RandomMoneyController randomMoneyController;

    private String userId;
    private String roomId;

    @Before
    public void setUpFixture(){
        this.userId = "userId";
        this.roomId = "roomId";
    }

    @Test
    public void SUCCESS(){
        RandomMoneyReq req = new RandomMoneyReq();
        req.setTotalMoney(10000L);
        req.setDistributionSize(3);

        Result result = randomMoneyController.createRandomMoney(userId, roomId, req);

        Assert.assertEquals(result.getErrorCode(), ErrorStatus.SUCCESS.getErrorCode());
        Assert.assertEquals(true, ((RandomMoneyRes)result.getResultData()).getToken().length() == 3);
    }

    @Test
    public void INVALID_MONEY_ZERO() {
        RandomMoneyReq req = new RandomMoneyReq();
        req.setTotalMoney(0L);
        req.setDistributionSize(3);

        try {
            randomMoneyController.createRandomMoney(userId, roomId, req);
        } catch (ToyException e) {
            Assert.assertEquals(ErrorStatus.INVALID_MONEY.getErrorCode(), e.getErrorStatus().getErrorCode());
            Assert.assertEquals("Money cannot be zero or negative", e.getErrorCause());
        }
    }

    @Test
    public void INVALID_MONEY_NEGATIVE() {
        RandomMoneyReq req = new RandomMoneyReq();
        req.setTotalMoney(-10000L);
        req.setDistributionSize(3);

        try {
            randomMoneyController.createRandomMoney(userId, roomId, req);
        } catch (ToyException e) {
            Assert.assertEquals(ErrorStatus.INVALID_MONEY.getErrorCode(), e.getErrorStatus().getErrorCode());
            Assert.assertEquals("Money cannot be zero or negative", e.getErrorCause());
        }
    }

    @Test
    public void INVALID_DISTRIBUTION_SIZE_ZERO() {
        RandomMoneyReq req = new RandomMoneyReq();
        req.setTotalMoney(10000L);
        req.setDistributionSize(0);

        try {
            randomMoneyController.createRandomMoney(userId, roomId, req);
        } catch (ToyException e) {
            Assert.assertEquals(ErrorStatus.INVALID_DISTRIBUTION_SIZE.getErrorCode(), e.getErrorStatus().getErrorCode());
            Assert.assertEquals("Distribution size cannot be zero or negative", e.getErrorCause());
        }
    }

    @Test
    public void INVALID_DISTRIBUTION_SIZE_NEGATIVE() {
        RandomMoneyReq req = new RandomMoneyReq();
        req.setTotalMoney(10000L);
        req.setDistributionSize(-1);

        try {
            randomMoneyController.createRandomMoney(userId, roomId, req);
        } catch (ToyException e) {
            Assert.assertEquals(ErrorStatus.INVALID_DISTRIBUTION_SIZE.getErrorCode(), e.getErrorStatus().getErrorCode());
            Assert.assertEquals("Distribution size cannot be zero or negative", e.getErrorCause());
        }
    }

    @Test
    public void INVALID_TOKEN_LENGTH(){
        String invalidTokenLength = "abcd";

        try {
            randomMoneyController.receiveRandomMoney(userId, roomId, invalidTokenLength);
        } catch (ToyException e) {
            Assert.assertEquals(ErrorStatus.INVALID_TOKEN.getErrorCode(), e.getErrorStatus().getErrorCode());
            Assert.assertEquals("Token length cannot be a string of 3 or more digits", e.getErrorCause());
        }

        try {
            randomMoneyController.getRandomMoneyInfo(userId, roomId, invalidTokenLength);
        } catch (ToyException e) {
            Assert.assertEquals(ErrorStatus.INVALID_TOKEN.getErrorCode(), e.getErrorStatus().getErrorCode());
            Assert.assertEquals("Token length cannot be a string of 3 or more digits", e.getErrorCause());
        }
    }
}