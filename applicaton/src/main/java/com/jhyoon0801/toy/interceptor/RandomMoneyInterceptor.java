package com.jhyoon0801.toy.interceptor;

import com.jhyoon0801.toy.contant.ErrorStatus;
import com.jhyoon0801.toy.exception.RandomMoneyException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RandomMoneyInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userId = request.getHeader("X-USER-ID");
        String roomId = request.getHeader("X-ROOM-ID");

        if(null == userId || userId.isEmpty()) {
            throw new RandomMoneyException(ErrorStatus.INVALID_USER_ID, "Empty user id is not allowed");
        }
        if(null == roomId || roomId.isEmpty()) {
            throw new RandomMoneyException(ErrorStatus.INVALID_ROOM_ID, "Empty room id is not allowed");
        }

        return true;
    }
}
