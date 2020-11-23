package com.jhyoon0801.toy.service;

import com.jhyoon0801.toy.contant.ErrorStatus;
import com.jhyoon0801.toy.dao.RandomMoneyDao;
import com.jhyoon0801.toy.dto.RandomMoneyInfoDto;
import com.jhyoon0801.toy.dto.ReceiveInfoDto;
import com.jhyoon0801.toy.exception.RandomMoneyException;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;

public class RandomMoneyServiceReceiveTest {

    @Mock
    private RandomMoneyDao randomMoneyDao;

    @InjectMocks
    private RandomMoneyServiceImpl randomMoneyService;

    private String ownerUserId;
    private String firstReceiverUserId;
    private String secondReceiverUserId;
    private String roomId;
    private String token;

    @Before
    public void setupFixture(){
        this.ownerUserId = "ownerUserId";
        this.firstReceiverUserId = "firstReceiverUserId";
        this.secondReceiverUserId = "secondReceiverUserId";
        this.roomId = "roomId";
        this.token = "tok";

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void NOT_EXIST_TOKEN_TEST(){
        when(randomMoneyDao.readActiveRandomMoneyInfo(this.roomId, this.token)).thenReturn(null);

        try {
            randomMoneyService.receiveRandomMoney(firstReceiverUserId, roomId, token);
        } catch (RandomMoneyException e) {
            Assert.assertEquals(ErrorStatus.NOT_EXIST_TOKEN, e.getErrorStatus());
        }
    }

    @Test
    public void REQUEST_BY_OWNER_TEST(){
        RandomMoneyInfoDto mockData = makeRandomMoneyInfoDto();

        when(randomMoneyDao.readActiveRandomMoneyInfo(this.roomId, this.token)).thenReturn(mockData);

        try {
            randomMoneyService.receiveRandomMoney(ownerUserId, roomId, token);
        } catch (RandomMoneyException e) {
            Assert.assertEquals(ErrorStatus.NOT_ALLOWED_FOR_OWNER, e.getErrorStatus());
        }
    }

    @Test
    public void ALREADY_RECEIVE_TEST(){
        RandomMoneyInfoDto mockData = makeRandomMoneyInfoDto();

        when(randomMoneyDao.readActiveRandomMoneyInfo(this.roomId, this.token)).thenReturn(mockData);

        try {
            randomMoneyService.receiveRandomMoney(firstReceiverUserId, roomId, token);
        } catch (RandomMoneyException e) {
            Assert.assertEquals(ErrorStatus.ALREADY_RECEIVE, e.getErrorStatus());
        }
    }

    private RandomMoneyInfoDto makeRandomMoneyInfoDto(){
        Date createdDate = DateUtils.addMinutes(new Date(), 1);

        ReceiveInfoDto mockDataFirst = new ReceiveInfoDto(this.firstReceiverUserId, 7000);
        ReceiveInfoDto mockDataSecond = new ReceiveInfoDto(this.secondReceiverUserId, 3000);

        List<ReceiveInfoDto> mockDataList = new ArrayList<>();
        mockDataList.add(mockDataFirst);
        mockDataList.add(mockDataSecond);

        RandomMoneyInfoDto mockData = new RandomMoneyInfoDto();
        mockData.setOwnerId(this.ownerUserId);
        mockData.setDistributionSize(2);
        mockData.setCreteDatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ").format(createdDate));
        mockData.setTotalMoney(10000);
        mockData.setReceivedMoney(10000);
        mockData.setReceiveInfoDtoList(mockDataList);
        return mockData;
    }
}
