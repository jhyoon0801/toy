package com.jhyoon0801.toy.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DistributionInfo {
    private Integer metaInfoSeq;
    private Integer distSeq;
    private Integer amount;
}
