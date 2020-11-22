package com.jhyoon0801.toy.dao.mapper;

import com.jhyoon0801.toy.domain.DistributionInfo;
import com.jhyoon0801.toy.domain.RandomMoneyMeta;
import com.jhyoon0801.toy.domain.ReceiveInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RandomMoneyMapper {
    int insertRandomMoneyMeta(RandomMoneyMeta randomMoneyMeta);
    void insertRandomMoneyDistribution(List<DistributionInfo> distInfoList);

    RandomMoneyMeta selectRandomMoneyMeta(@Param("roomId") String roomId, @Param("token") String token);
    List<ReceiveInfo> selectRandomMoneyReceiveInfo(@Param("metaSeq") Integer metaSeq);
}