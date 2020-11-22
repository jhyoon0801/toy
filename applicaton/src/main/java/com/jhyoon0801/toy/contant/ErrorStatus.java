package com.jhyoon0801.toy.contant;

public enum ErrorStatus {

    SUCCESS(200, 0, "success"),
    INVALID_MONEY(400, 40001, "Invalid money"),
    INVALID_DISTRIBUTION_SIZE(400, 40002, "Invalid distribution size"),
    INVALID_TOKEN(400, 40003, "Invalid token"),
    INVALID_USER_ID(400, 40004, "Invalid user id"),
    INVALID_ROOM_ID(400, 40005, "Invalid room id"),
    NOT_EXIST_TOKEN(400, 40006, "Not exist token"),
    NOT_OWNER_ID(403, 40301, "Not owner id"),
    INTERNAL_SERVER_ERROR(500, 50001, "Internal server error");

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
