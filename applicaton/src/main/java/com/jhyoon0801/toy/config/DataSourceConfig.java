package com.jhyoon0801.toy.config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean("randomMoneyDataSource")
    @ConfigurationProperties(prefix = "randommoney.db")
    public DataSource randomMoneyDataSource() {
        DataSource dataSource = DataSourceBuilder.create().build();
        return dataSource;
    }

    @Bean(name = "randomMoneySqlSessionFactory")
    public SqlSessionFactoryBean subscriptionReadOnlySqlSessionFactory(@Qualifier("randomMoneyDataSource") DataSource dataSource, ApplicationContext applicationContext) {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatisConfig.xml"));
        return sqlSessionFactoryBean;
    }

    @Bean("randomMoneyTransactionManager")
    public DataSourceTransactionManager randomMoneyTransactionManager(@Qualifier("randomMoneyDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
