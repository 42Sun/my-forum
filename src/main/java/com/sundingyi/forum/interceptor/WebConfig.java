package com.sundingyi.forum.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final SessionInteceptor sessionInteceptor;
    
    public WebConfig(SessionInteceptor sessionInteceptor) {
        this.sessionInteceptor = sessionInteceptor;
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionInteceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/**/*.css");
        
    }
}
