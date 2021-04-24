package com.geoparking.bookingservice.util;

import javax.servlet.http.HttpServletRequest;

import com.geoparking.bookingservice.model.DecodedUserInfo;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class WithUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // We check if our parameter is exactly what we need:
        return parameter.hasParameterAnnotation(WithUser.class)
                && parameter.getParameterType().equals(DecodedUserInfo.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // take the request
        HttpServletRequest httpServletRequest = (HttpServletRequest) webRequest.getNativeRequest();
        // take our Authorization header from request and get access token
        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        authorizationHeader = authorizationHeader.replace("Bearer ", "");
        // Using jjwt library parse our token and create Claim object
        Claims claims = Jwts.parser().setSigningKey("secret") // todo just example
                .parseClaimsJws(authorizationHeader).getBody();
        // create UserInfo object which we need to inject in our method
        // System.err.println("Extracting claims in parking service...." +
        // claims.toString());

        return new DecodedUserInfo((String) claims.get("sub"), (String) claims.get("username"));
    }

}
