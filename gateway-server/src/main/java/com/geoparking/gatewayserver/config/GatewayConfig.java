// package com.geoparking.gatewayserver.config;

// import java.time.Duration;

// import
// org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
// import
// org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
// import org.springframework.cloud.client.circuitbreaker.Customizer;
// import org.springframework.cloud.gateway.route.RouteLocator;
// import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
// import io.github.resilience4j.timelimiter.TimeLimiterConfig;

// @Configuration
// public class GatewayConfig {

// @Bean
// public RouteLocator myRouter(RouteLocatorBuilder routeLocatorBuilder) {
// return routeLocatorBuilder.routes().route(p ->
// p.path("/auth/**").uri("lb://profile-service"))
// .route(p -> p.path("/api/**").filters(f -> f
// .circuitBreaker(c -> c.setName("commonServiceCircuitBreaker")
// .setFallbackUri("/commonServiceFallBack"))
// .stripPrefix(1)).uri("lb://common-service"))
// .build();
// }

// @Bean
// public Customizer<ReactiveResilience4JCircuitBreakerFactory>
// defaultCustomizer() {

// return factory -> factory
// .configureDefault(id -> new Resilience4JConfigBuilder(id)
// .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
// .timeLimiterConfig(TimeLimiterConfig.custom()
// .timeoutDuration(Duration.ofSeconds(4)).build())
// .build());
// }

// }
