package com.jhyoon0801.toy.config;

import com.jhyoon0801.toy.interceptor.RandomMoneyInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private RandomMoneyInterceptor randomMoneyInterceptor;

    WebConfig(RandomMoneyInterceptor randomMoneyInterceptor){
        this.randomMoneyInterceptor = randomMoneyInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(this.randomMoneyInterceptor)
        .addPathPatterns("/random-money/**");
    }
}
