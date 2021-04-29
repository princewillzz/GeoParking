package com.geoparking.profileservice.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.geoparking.profileservice.principal.ProfilePrincipal;
import com.geoparking.profileservice.service.JwtUtilService;
import com.geoparking.profileservice.service.ProfileService;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    private JwtUtilService jwtUtilService;
    private final ProfileService profileService;

    public JwtRequestFilter(JwtUtilService jwtUtilService, ProfileService profileService) {
        this.jwtUtilService = jwtUtilService;
        this.profileService = profileService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        String jwt;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            jwt = authHeader.substring(7);

            try {

                final String subject = jwtUtilService.extractSubject(jwt);
                if (subject != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    final ProfilePrincipal userDetails = this.profileService.loadUserBySubject(subject);

                    if (jwtUtilService.validateToken(jwt, userDetails)) {
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());

                        usernamePasswordAuthenticationToken
                                .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }
                }

            } catch (Exception e) {
                log.error(e.getMessage());
                SecurityContextHolder.clearContext();
            }

        }

        filterChain.doFilter(request, response);

    }
}
