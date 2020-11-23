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

public class RandomMoneyServiceGetInfoTest {

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

    private RandomMoneyInfoDto makeRandomMoneyInfoDto(){
        Date createdDate = DateUtils.addDays(new Date(), -2);

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

    @Test
    public void SUCCESS() {

        RandomMoneyInfoDto mockData = makeRandomMoneyInfoDto();

        when(randomMoneyDao.readRandomMoneyInfo(this.roomId, this.token)).thenReturn(mockData);

        RandomMoneyInfoDto result = randomMoneyService.getRandomMoneyInfo(this.ownerUserId, this.roomId, this.token);

        Assert.assertEquals(mockData.getTotalMoney(), result.getTotalMoney());
        Assert.assertEquals(mockData.getReceivedMoney(), result.getReceivedMoney());
        Assert.assertEquals(mockData.getCreteDatetime(), result.getCreteDatetime());
        Assert.assertEquals(mockData.getReceiveInfoDtoList(), result.getReceiveInfoDtoList());
    }

    @Test
    public void REQUEST_BY_NOT_OWNER() {
        RandomMoneyInfoDto mockData = makeRandomMoneyInfoDto();
        when(randomMoneyDao.readRandomMoneyInfo(this.roomId, this.token)).thenReturn(mockData);

        try {
            randomMoneyService.getRandomMoneyInfo(this.firstReceiverUserId, this.roomId, this.token);
        } catch (RandomMoneyException e) {
            Assert.assertEquals(ErrorStatus.NOT_OWNER_ID.getErrorCode(), e.getErrorStatus().getErrorCode());
            Assert.assertEquals("Request from not allowed user", e.getErrorCause());
            return;
        }
        Assert.fail();
    }

    @Test
    public void NOT_EXIST_TOKEN() {
        RandomMoneyInfoDto mockData = makeRandomMoneyInfoDto();
        when(randomMoneyDao.readRandomMoneyInfo(this.roomId, this.token)).thenReturn(mockData);

        try {
            randomMoneyService.getRandomMoneyInfo(this.ownerUserId, this.roomId, "abc");
        } catch (RandomMoneyException e) {
            Assert.assertEquals(ErrorStatus.NOT_EXIST_TOKEN.getErrorCode(), e.getErrorStatus().getErrorCode());
            Assert.assertEquals("Don't exist any valid random money", e.getErrorCause());
            return;
        }
        Assert.fail();
    }
}