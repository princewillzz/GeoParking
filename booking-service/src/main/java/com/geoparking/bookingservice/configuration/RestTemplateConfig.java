package com.geoparking.bookingservice.configuration;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.common.circuitbreaker.configuration.CircuitBreakerConfigCustomizer;

@Configuration
public class RestTemplateConfig {

    // Create a bean to call other services
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {

        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(3000);

        return new RestTemplate(clientHttpRequestFactory);
    }

    @Bean
    public CircuitBreakerConfigCustomizer testCustomizer() {
        return CircuitBreakerConfigCustomizer.of("backendA", builder -> builder.slidingWindowSize(100));
    }
}
