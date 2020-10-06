package com.foodPro.demo.config;

import com.foodPro.demo.config.filter.HeaderFilter;
import com.foodPro.demo.config.interceptor.AuthInterceptor;
import com.foodPro.demo.config.interceptor.JwtTokenInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(jwtTokenInterceptor()) // LINE :: JWT
                .addPathPatterns("/content/**");
        registry.addInterceptor(authInterceptor()) // LINE :: SESSION
                .addPathPatterns("/content/**");
    }

    @Bean
    public FilterRegistrationBean<HeaderFilter> getFilterRegistrationBean() {
        FilterRegistrationBean<HeaderFilter> registrationBean = new FilterRegistrationBean<>(createHeaderFilter());
        registrationBean.setOrder(Integer.MIN_VALUE);
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

    @Bean
    public HeaderFilter createHeaderFilter() {
        return new HeaderFilter();
    }

    @Bean
    public JwtTokenInterceptor jwtTokenInterceptor() {
        return new JwtTokenInterceptor();
    }

    @Bean
    public AuthInterceptor authInterceptor() { return new AuthInterceptor();}
}
