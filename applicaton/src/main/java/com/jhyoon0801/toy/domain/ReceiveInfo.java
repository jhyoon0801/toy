package com.jhyoon0801.toy.domain;

import lombok.Data;

@Data
public class ReceiveInfo {
    private Integer distSeq;
    private Integer metaInfoSeq;
    private String receiverId;
    private Integer amount;
    private String creteDatetime;
}