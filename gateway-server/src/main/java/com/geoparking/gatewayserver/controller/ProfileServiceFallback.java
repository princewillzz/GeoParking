package com.geoparking.gatewayserver.controller;

import com.geoparking.gatewayserver.model.CustomClientHttpResponse;

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

@Component
public class ProfileServiceFallback implements FallbackProvider {

    @Override
    public String getRoute() {
        return "profile-service";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {

        return new CustomClientHttpResponse();
    }

}
