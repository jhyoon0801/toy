package com.jhyoon0801.toy.service;

import com.jhyoon0801.toy.dao.RandomMoneyDao;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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

}
