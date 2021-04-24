package com.geoparking.bookingservice.configuration;

import java.util.List;

import com.geoparking.bookingservice.util.WithUserHandlerMethodArgumentResolver;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new WithUserHandlerMethodArgumentResolver());
    }

}