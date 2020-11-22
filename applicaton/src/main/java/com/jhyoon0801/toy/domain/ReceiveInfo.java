package com.jhyoon0801.toy.domain;

import lombok.Data;

@Data
public class ReceiveInfo {
    private Integer seq;
    private Integer metaInfoSeq;
    private String receiverId;
    private Integer amount;
    private String creteDatetime;
}