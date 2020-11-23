package com.jhyoon0801.toy.service;

import com.jhyoon0801.toy.dao.RandomMoneyDao;
import com.jhyoon0801.toy.domain.DistributionInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;

import org.powermock.reflect.Whitebox;

import java.util.List;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("jdk.internal.reflect.*")
public class RandomMoneyServiceCreatePrivateTest {

    @Mock
    private RandomMoneyDao randomMoneyDao;

    @InjectMocks
    private RandomMoneyServiceImpl randomMoneyService;

    @Test
    public void VALIDATE_AMOUNT_DISTRIBUTION() throws Exception {
        List<DistributionInfo> result = Whitebox.<List<DistributionInfo>>invokeMethod(randomMoneyService, "createDistributionInfo", 10000, 9);
        int sum = (int)result.stream().map(e -> e.getAmount()).reduce(0, (a, b) -> a + b);
        Assert.assertEquals(10000, sum);
    }
}
