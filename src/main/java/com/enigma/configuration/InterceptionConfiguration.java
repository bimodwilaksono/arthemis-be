package com.enigma.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptionConfiguration implements WebMvcConfigurer {
    private final SimpleInterceptor simpleInterceptor;

    @Autowired
    public InterceptionConfiguration(SimpleInterceptor simpleInterceptor) {
        this.simpleInterceptor = simpleInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry interceptorRegistry){
        interceptorRegistry.addInterceptor(simpleInterceptor);
    }
}
