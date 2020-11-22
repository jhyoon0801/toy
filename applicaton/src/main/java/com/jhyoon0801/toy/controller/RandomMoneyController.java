package com.jhyoon0801.toy.controller;

import com.jhyoon0801.toy.contant.ErrorStatus;
import com.jhyoon0801.toy.dto.RandomMoneyInfoDto;
import com.jhyoon0801.toy.exception.RandomMoneyException;
import com.jhyoon0801.toy.dto.*;
import com.jhyoon0801.toy.service.RandomMoneyService;
import org.springframework.web.bind.annotation.*;

@RestController("/random-money")
public class RandomMoneyController {

    private static final int TOKEN_LENGTH = 3;

    private RandomMoneyService randomMoneyService;

    RandomMoneyController(RandomMoneyService randomMoneyService){
        this.randomMoneyService = randomMoneyService;
    }

    @PostMapping
    public @ResponseBody Result createRandomMoney(@RequestHeader(name="X-USER-ID") String userId, @RequestHeader(name="X-ROOM-ID") String roomId, @RequestBody RandomMoneyReq req){

        if( 0 >= req.getTotalMoney()) {
            throw new RandomMoneyException(ErrorStatus.INVALID_MONEY, "Money cannot be zero or negative");
        }
        if( 0 >= req.getDistributionSize()) {
            throw new RandomMoneyException(ErrorStatus.INVALID_DISTRIBUTION_SIZE, "Distribution size cannot be zero or negative");
        }

        String token = randomMoneyService.createRandomMoney(userId, roomId, req);
        if( null == token ){
            throw new RandomMoneyException(ErrorStatus.INTERNAL_SERVER_ERROR, "Create random money failed");
        }

        RandomMoneyRes resultData = new RandomMoneyRes();
        resultData.setToken(token);
        return new Result(ErrorStatus.SUCCESS, null, resultData);
    }

    @PutMapping("/{token}")
    public @ResponseBody Result receiveRandomMoney(@RequestHeader(name="X-USER-ID") String userId, @RequestHeader(name="X-ROOM-ID") String roomId, @PathVariable(required = true) String token){

        if( TOKEN_LENGTH != token.length()) {
            throw new RandomMoneyException(ErrorStatus.INVALID_TOKEN, "Token length cannot be greater than 3 digit string");
        }

        ReceiveInfoDto resultData = randomMoneyService.receiveRandomMoney(userId, roomId, token);
        return new Result(ErrorStatus.SUCCESS, null, resultData);
    }

    @GetMapping("/{token}")
    public @ResponseBody Result getRandomMoneyInfo(@RequestHeader(name="X-USER-ID") String userId, @RequestHeader(name="X-ROOM-ID") String roomId, @PathVariable(required = true, value = "token") String token){

        if( TOKEN_LENGTH != token.length()) {
            throw new RandomMoneyException(ErrorStatus.INVALID_TOKEN, "Token length cannot be greater than 3 digit string");
        }

        RandomMoneyInfoDto resultData = randomMoneyService.getRandomMoneyInfo(userId, roomId, token);
        return new Result(ErrorStatus.SUCCESS, null, resultData);
    }
}
