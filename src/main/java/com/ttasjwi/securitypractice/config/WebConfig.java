package com.ttasjwi.securitypractice.config;

import com.ttasjwi.securitypractice.config.filter.LogFilter;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean<Filter> logFilter() {
        var filterFilterRegistrationBean = new FilterRegistrationBean<>();

        filterFilterRegistrationBean.setFilter(new LogFilter()); // 등록할 필터 지정
        filterFilterRegistrationBean.setOrder(1); // 순서 지정 -> 낮을 수록 우선 동작
        filterFilterRegistrationBean.addUrlPatterns("/*"); // 필터를 적용할 URL 패턴

        return filterFilterRegistrationBean;
    }
}
