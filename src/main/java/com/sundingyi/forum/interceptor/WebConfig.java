package com.sundingyi.forum.interceptor;

import com.sundingyi.forum.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
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
