package com.jhyoon0801.toy.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributionInfo {
    private Integer metaInfoSeq;
    private Integer distSeq;
    private Integer amount;
}
