package com.jhyoon0801.toy.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperScanConfig {
    @MapperScan(basePackages = {"com.jhyoon0801.toy.dao"}
            , sqlSessionFactoryRef = "randomMoneySqlSessionFactory")
    public class randomMoneyMapperScan {

    }
}
