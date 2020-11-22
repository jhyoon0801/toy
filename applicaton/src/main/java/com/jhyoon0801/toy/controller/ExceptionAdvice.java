package com.jhyoon0801.toy.controller;

import com.jhyoon0801.toy.dto.Result;
import com.jhyoon0801.toy.exception.RandomMoneyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(value = RandomMoneyException.class)
    @ResponseBody
    public ResponseEntity handleRandomMoneyException(RandomMoneyException ex){
        return new ResponseEntity(new Result(ex.getErrorStatus(), ex.getErrorCause(), null), HttpStatus.valueOf(ex.getErrorStatus().getStatusCode()));
    }

}
