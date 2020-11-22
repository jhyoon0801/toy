package com.jhyoon0801.toy.exception;

import com.jhyoon0801.toy.contant.ErrorStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class RandomMoneyException extends RuntimeException {

    private ErrorStatus errorStatus;
    private String errorCause;

    public RandomMoneyException(ErrorStatus e, String errorCause){
        super(e.getErrorMessage());
        this.errorStatus = e;
        this.errorCause = errorCause;
    }
}
