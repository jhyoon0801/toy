package com.jhyoon0801.toy.contant;

import lombok.Data;

@Data
public class ToyException extends RuntimeException {

    private ErrorStatus errorStatus;
    private String errorCause;

    public ToyException(ErrorStatus e, String errorCause){
        super(e.getErrorMessage());
        this.errorStatus = e;
        this.errorCause = errorCause;
    }
}
