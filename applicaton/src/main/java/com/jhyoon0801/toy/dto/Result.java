package com.jhyoon0801.toy.dto;

import com.jhyoon0801.toy.contant.ErrorStatus;

public class Result {

    private int errorCode;
    private String errorMessage;
    private String errorCause;
    private Object resultData;

    public Result(ErrorStatus errorStatus, Object resultData){
        this.errorCode = errorStatus.getErrorCode();
        this.errorMessage = errorStatus.getErrorMessage();
        this.errorCause = errorStatus.getErrorCause();
        this.resultData = resultData;
    }
}
