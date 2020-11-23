package com.jhyoon0801.toy.dao.mapper;

import com.jhyoon0801.toy.domain.DistributionInfo;
import com.jhyoon0801.toy.domain.RandomMoneyMeta;
import com.jhyoon0801.toy.domain.ReceiveInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface RandomMoneyMapper {
    int insertRandomMoneyMeta(RandomMoneyMeta randomMoneyMeta);
    void insertRandomMoneyDistribution(List<DistributionInfo> distInfoList);

    int insertTempReceiverInfo(@Param("metaSeq") Integer metaSeq, @Param("receiverId") String receiverId);
    int selectOrderOfReceiver(@Param("metaSeq") Integer metaSeq, @Param("receiverId") String receiverId);
    int insertRandomMoneyReceiveInfo(@Param("metaSeq") Integer metaSeq, @Param("receiverId") String receiverId, @Param("order") Integer order);
    int selectRandomMoneyDistAmount(@Param("metaSeq") Integer metaSeq, @Param("distSeq") Integer distSeq);

    RandomMoneyMeta selectRandomMoneyMeta(@Param("roomId") String roomId, @Param("token") String token, @Param("duration") int duration);
    List<ReceiveInfo> selectRandomMoneyReceiveInfo(@Param("metaSeq") Integer metaSeq);
}