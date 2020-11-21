package com.jhyoon0801.toy.controller;

import com.jhyoon0801.toy.contant.ErrorStatus;
import com.jhyoon0801.toy.dto.RandomMoneyReq;
import com.jhyoon0801.toy.dto.ReceiveInfo;
import com.jhyoon0801.toy.dto.Result;
import com.jhyoon0801.toy.service.RandomMoneyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/random-money")
public class RandomMoneyController {

    private RandomMoneyService randomMoneyService;

    RandomMoneyController(RandomMoneyService randomMoneyService){
        this.randomMoneyService = randomMoneyService;
    }

    @PostMapping
    public @ResponseBody
    Result createRandomMoney(@RequestHeader(name="X-USER-ID") String userId, @RequestHeader(name="X-ROOM-ID") String roomId, @RequestBody RandomMoneyReq req){


        String resultData = randomMoneyService.createRandomMoney(userId, roomId, req);
        return new Result(ErrorStatus.SUCCESS, null, null);
    }

    @PutMapping("/{token}")
    public @ResponseBody Result receiveRandomMoney(@RequestHeader(name="X-USER-ID") String userId, @RequestHeader(name="X-ROOM-ID") String roomId, @PathVariable String token){
        ReceiveInfo resultData = randomMoneyService.receiveRandomMoney(userId, roomId, token);
        return new Result(ErrorStatus.SUCCESS, null, null);
    }

    @GetMapping("/{token}")
    public @ResponseBody Result getRandomMoneyInfo(@RequestHeader(name="X-USER-ID") String userId, @RequestHeader(name="X-ROOM-ID") String roomId, @PathVariable String token){
        List<ReceiveInfo> resultData = randomMoneyService.getRandomMoneyInfo(userId, roomId, token);
        return new Result(ErrorStatus.SUCCESS, null, null);
    }


}
