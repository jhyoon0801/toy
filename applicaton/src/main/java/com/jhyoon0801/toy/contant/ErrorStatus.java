package com.jhyoon0801.toy.contant;

public enum ErrorStatus {

    SUCCESS(0, "Success", null);

    private int errorCode;
    private String errorMessage;
    private String errorCause;

    ErrorStatus(int errorCode, String errorMessage, String errorCause) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorCause = errorCause;
    }

    public int getErrorCode(){return this.errorCode;}
    public String getErrorMessage(){return this.errorMessage;}
    public String getErrorCause(){return this.errorCause;}
}
