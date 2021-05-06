package com.geoparking.gatewayserver.controller;

import com.geoparking.gatewayserver.model.CustomClientHttpResponse;

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

@Component
public class ParkingServiceFallback implements FallbackProvider {

    @Override
    public String getRoute() {
        return "parking-ervice";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {

        return new CustomClientHttpResponse();
    }

}
