package com.jhyoon0801.toy.service;

import com.jhyoon0801.toy.dao.RandomMoneyDao;
import com.jhyoon0801.toy.domain.DistributionInfo;
import com.jhyoon0801.toy.domain.RandomMoneyMeta;
import com.jhyoon0801.toy.dto.RandomMoneyReq;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RandomMoneyServiceCreateTest {

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
    public void DUPLICATED_TOKEN_GENERATED_OVER_RETRY_COUNT(){

        RandomMoneyMeta mockRandomMoneyMetaData = RandomMoneyMeta.builder()
                .ownerId(ownerUserId)
                .roomId(roomId)
                .totalMoney(100000)
                .distributionSize(2)
                .token(token).build();

        List<DistributionInfo> mockDistributionInfoList = new ArrayList<>();
        mockDistributionInfoList.add(DistributionInfo.builder().amount(3415).distSeq(1).build());
        mockDistributionInfoList.add(DistributionInfo.builder().amount(6585).distSeq(2).build());

        when(randomMoneyDao.createRandomMoney(mockRandomMoneyMetaData, mockDistributionInfoList)).thenThrow(DuplicateKeyException.class);

        RandomMoneyReq req = new RandomMoneyReq();
        req.setTotalMoney(100000);
        req.setDistributionSize(2);

        Assert.assertEquals(null, randomMoneyService.createRandomMoney(ownerUserId, roomId, req));
    }

}
