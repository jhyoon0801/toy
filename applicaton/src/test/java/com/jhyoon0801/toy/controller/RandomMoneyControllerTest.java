package com.jhyoon0801.toy.controller;

import com.jhyoon0801.toy.contant.ErrorStatus;
import com.jhyoon0801.toy.dto.RandomMoneyInfoDto;
import com.jhyoon0801.toy.exception.RandomMoneyException;
import com.jhyoon0801.toy.dto.*;
import com.jhyoon0801.toy.service.RandomMoneyService;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RandomMoneyControllerTest {

    @Mock
    private RandomMoneyService randomMoneyService;

    @InjectMocks
    @Autowired
    private RandomMoneyController randomMoneyController;

    private String ownerUserId;
    private String firstReceiverUserId;
    private String secondReceiverUserId;
    private String roomId;

    @Before
    public void setUpFixture(){
        this.ownerUserId = "ownerUserId";
        this.firstReceiverUserId = "firstReceiverUserId";
        this.secondReceiverUserId = "secondReceiverUserId";
        this.roomId = "roomId";

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void SUCCESS_CREATE(){
        RandomMoneyReq req = new RandomMoneyReq();
        req.setTotalMoney(10000);
        req.setDistributionSize(3);

        when(randomMoneyService.createRandomMoney(this.ownerUserId, this.roomId, req)).thenReturn("abc");

        Result result = randomMoneyController.createRandomMoney(ownerUserId, roomId, req);

        Assert.assertEquals(result.getErrorCode(), ErrorStatus.SUCCESS.getErrorCode());
        Assert.assertEquals("abc".length(), ((RandomMoneyRes)result.getResultData()).getToken().length());
    }

    @Test
    public void SUCCESS_RECEIVE(){
        String token = "abc";

        ReceiveInfoDto mockData = new ReceiveInfoDto();
        mockData.setAmount(5000);
        mockData.setReceiverId(this.firstReceiverUserId);

        when(randomMoneyService.receiveRandomMoney(this.ownerUserId, this.roomId, token)).thenReturn(mockData);

        Result result = randomMoneyController.receiveRandomMoney(ownerUserId, roomId, token);

        Assert.assertEquals(result.getErrorCode(), ErrorStatus.SUCCESS.getErrorCode());
        Assert.assertEquals(mockData.getAmount(), ((ReceiveInfoDto)result.getResultData()).getAmount());
        Assert.assertEquals(mockData.getReceiverId(), ((ReceiveInfoDto)result.getResultData()).getReceiverId());
    }

    @Test
    public void SUCCESS_GET_RANDOM_MONEY_INFO(){
        String token = "abc";

        Date createdDate = DateUtils.addDays(new Date(), -3);

        ReceiveInfoDto mockDataFirst = new ReceiveInfoDto();
        mockDataFirst.setAmount(7000);
        mockDataFirst.setReceiverId(this.firstReceiverUserId);

        ReceiveInfoDto mockDataSecond = new ReceiveInfoDto();
        mockDataSecond.setAmount(3000);
        mockDataSecond.setReceiverId(this.secondReceiverUserId);

        List<ReceiveInfoDto> mockDataList = new ArrayList<>();
        mockDataList.add(mockDataFirst);
        mockDataList.add(mockDataSecond);

        RandomMoneyInfoDto mockData = RandomMoneyInfoDto.builder()
                .createdTimeStamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ").format(createdDate))
                .totalMoney(10000)
                .receivedMoney(10000)
                .receiveInfoDtoList(mockDataList)
                .build();

        when(randomMoneyService.getRandomMoneyInfo(this.ownerUserId, this.roomId, token)).thenReturn(mockData);

        Result result = randomMoneyController.getRandomMoneyInfo(ownerUserId, roomId, token);
        Assert.assertEquals(result.getErrorCode(), ErrorStatus.SUCCESS.getErrorCode());
        Assert.assertEquals(mockData.getTotalMoney(), ((RandomMoneyInfoDto)result.getResultData()).getTotalMoney());
        Assert.assertEquals(mockData.getReceivedMoney(), ((RandomMoneyInfoDto)result.getResultData()).getReceivedMoney());
        Assert.assertEquals(mockData.getCreatedTimeStamp(), ((RandomMoneyInfoDto)result.getResultData()).getCreatedTimeStamp());
    }

    @Test
    public void INVALID_MONEY_ZERO() {
        RandomMoneyReq req = new RandomMoneyReq();
        req.setTotalMoney(0);
        req.setDistributionSize(3);

        try {
            randomMoneyController.createRandomMoney(ownerUserId, roomId, req);
        } catch (RandomMoneyException e) {
            Assert.assertEquals(ErrorStatus.INVALID_MONEY.getErrorCode(), e.getErrorStatus().getErrorCode());
            Assert.assertEquals("Money cannot be zero or negative", e.getErrorCause());
            return;
        }
        Assert.fail();
    }

    @Test
    public void INVALID_MONEY_NEGATIVE() {
        RandomMoneyReq req = new RandomMoneyReq();
        req.setTotalMoney(-10000);
        req.setDistributionSize(3);

        try {
            randomMoneyController.createRandomMoney(ownerUserId, roomId, req);
        } catch (RandomMoneyException e) {
            Assert.assertEquals(ErrorStatus.INVALID_MONEY.getErrorCode(), e.getErrorStatus().getErrorCode());
            Assert.assertEquals("Money cannot be zero or negative", e.getErrorCause());
            return;
        }
        Assert.fail();
    }

    @Test
    public void INVALID_DISTRIBUTION_SIZE_ZERO() {
        RandomMoneyReq req = new RandomMoneyReq();
        req.setTotalMoney(10000);
        req.setDistributionSize(0);

        try {
            randomMoneyController.createRandomMoney(ownerUserId, roomId, req);
        } catch (RandomMoneyException e) {
            Assert.assertEquals(ErrorStatus.INVALID_DISTRIBUTION_SIZE.getErrorCode(), e.getErrorStatus().getErrorCode());
            Assert.assertEquals("Distribution size cannot be zero or negative", e.getErrorCause());
            return;
        }
        Assert.fail();
    }

    @Test
    public void INVALID_DISTRIBUTION_SIZE_NEGATIVE() {
        RandomMoneyReq req = new RandomMoneyReq();
        req.setTotalMoney(10000);
        req.setDistributionSize(-1);

        try {
            randomMoneyController.createRandomMoney(ownerUserId, roomId, req);
        } catch (RandomMoneyException e) {
            Assert.assertEquals(ErrorStatus.INVALID_DISTRIBUTION_SIZE.getErrorCode(), e.getErrorStatus().getErrorCode());
            Assert.assertEquals("Distribution size cannot be zero or negative", e.getErrorCause());
            return;
        }
        Assert.fail();
    }

    @Test
    public void INVALID_TOKEN_LENGTH(){
        String invalidTokenLength = "abcd";

        receiveRandomMoneyTestCase(invalidTokenLength);
        getRandomMoneyInfoTestCase(invalidTokenLength);
    }

    private void receiveRandomMoneyTestCase(final String invalidTokenLength){
        try {
            randomMoneyController.receiveRandomMoney(ownerUserId, roomId, invalidTokenLength);
        } catch (RandomMoneyException e) {
            Assert.assertEquals(ErrorStatus.INVALID_TOKEN.getErrorCode(), e.getErrorStatus().getErrorCode());
            Assert.assertEquals("Token length cannot be greater than 3 digit string", e.getErrorCause());
            return;
        }
        Assert.fail();
    }

    private void getRandomMoneyInfoTestCase(final String invalidTokenLength){
        try {
            randomMoneyController.getRandomMoneyInfo(ownerUserId, roomId, invalidTokenLength);
        } catch (RandomMoneyException e) {
            Assert.assertEquals(ErrorStatus.INVALID_TOKEN.getErrorCode(), e.getErrorStatus().getErrorCode());
            Assert.assertEquals("Token length cannot be greater than 3 digit string", e.getErrorCause());
            return;
        }
        Assert.fail();
    }
}