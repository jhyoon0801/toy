package com.jhyoon0801.toy.dto;

import com.jhyoon0801.toy.contant.ErrorStatus;
import lombok.Data;

@Data
public class Result {

    private int errorCode;
    private String errorMessage;
    private String errorCause;
    private Object resultData;

    public Result(ErrorStatus errorStatus, String errorCause, Object resultData){
        this.errorCode = errorStatus.getErrorCode();
        this.errorMessage = errorStatus.getErrorMessage();
        this.errorCause = errorCause;
        this.resultData = resultData;
    }
}
