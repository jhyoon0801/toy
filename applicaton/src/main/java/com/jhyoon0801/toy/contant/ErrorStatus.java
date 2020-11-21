package com.jhyoon0801.toy.contant;

public enum ErrorStatus {

    SUCCESS(200, 0, "success"),
    INVALID_MONEY(400, 40001, "Invalid money"),
    INVALID_DISTRIBUTION_SIZE(400, 40002, "Invalid distribution size"),
    INVALID_TOKEN(400, 40003, "Invalid token");

    private int statusCode;
    private int errorCode;
    private String errorMessage;

    ErrorStatus(int statusCode, int errorCode, String errorMessage) {
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getStatusCode(){return this.statusCode;}
    public int getErrorCode(){return this.errorCode;}
    public String getErrorMessage(){return this.errorMessage;}
}
