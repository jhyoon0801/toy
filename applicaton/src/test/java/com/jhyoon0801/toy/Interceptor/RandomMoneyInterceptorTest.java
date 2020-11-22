package com.jhyoon0801.toy.Interceptor;

import com.jhyoon0801.toy.controller.RandomMoneyController;
import com.jhyoon0801.toy.dto.ReceiveInfoDto;
import com.jhyoon0801.toy.service.RandomMoneyService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RandomMoneyInterceptorTest {

    private String userId = "ownerUserId";
    private String roomId = "roomId";
    private String token = "abc";
    private ReceiveInfoDto receiveInfoDto;

    @Mock
    private RandomMoneyService randomMoneyService;
    @InjectMocks
    private RandomMoneyController randomMoneyController;
    private MockMvc mockMvc;

    @Before
    public void setUpFixture(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(randomMoneyController).build();

        receiveInfoDto = new ReceiveInfoDto();
        receiveInfoDto.setAmount(5000);
        receiveInfoDto.setReceiverId(this.userId);
    }

    @Test
    public void SUCCESS() throws Exception {
        when(randomMoneyService.receiveRandomMoney(this.userId, this.roomId, token)).thenReturn(receiveInfoDto);
        mockMvc.perform(put("/random-money/abc").header("X-USER_ID", userId).header("X-ROOM-ID", roomId)).andExpect(status().isOk());
    }

    @Test
    public void INVALID_USER_ID_NULL() throws Exception {
        when(randomMoneyService.receiveRandomMoney(this.userId, this.roomId, token)).thenReturn(receiveInfoDto);
        mockMvc.perform(put("/random-money/abc").header("X-ROOM-ID", roomId)).andExpect(status().isBadRequest());
    }

    @Test
    public void INVALID_USER_ID_EMPTY_STRING() throws Exception {
        when(randomMoneyService.receiveRandomMoney(this.userId, this.roomId, token)).thenReturn(receiveInfoDto);
        mockMvc.perform(put("/random-money/abc").header("X-ROOM-ID", roomId).header("X-USER_ID", "")).andExpect(status().isBadRequest());
    }

    @Test
    public void INVALID_ROOM_ID_NULL() throws Exception {
        when(randomMoneyService.receiveRandomMoney(this.userId, this.roomId, token)).thenReturn(receiveInfoDto);
        mockMvc.perform(put("/random-money/abc").header("X-USER_ID", userId)).andExpect(status().isBadRequest());
    }

    @Test
    public void INVALID_ROOM_ID_EMPTY_STRING() throws Exception {
        when(randomMoneyService.receiveRandomMoney(this.userId, this.roomId, token)).thenReturn(receiveInfoDto);
        mockMvc.perform(put("/random-money/abc").header("X-USER_ID", userId).header("X-ROOM-ID", "")).andExpect(status().isBadRequest());
    }
}