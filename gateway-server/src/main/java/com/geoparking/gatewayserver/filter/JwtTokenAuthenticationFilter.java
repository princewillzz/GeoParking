package com.geoparking.gatewayserver.filter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    final Logger log = LoggerFactory.getLogger(JwtTokenAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. get the authentication header. Tokens are supposed to be passed in the
        // authentication header
        final String header = request.getHeader("Authorization");

        // 2. validate the header and check the prefix
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // If not valid, go to the next filter.
            return;
        }

        // If there is no token provided and hence the user won't be authenticated.
        // It's Ok. Maybe the user accessing a public path or asking for a token.

        // All secured paths that needs a token are already defined and secured in
        // config class.
        // And If user tried to access without access token, then he won't be
        // authenticated and an exception will be thrown.

        // 3. Get the token
        final String token = header.replace("Bearer ", "");

        try { // exceptions might be thrown in creating the claims if for example the token is
              // expired

            // 4. Validate the token
            final String SECRET_KEY = "secret";
            System.out.println("Extracting claims: " + SECRET_KEY + " " + token);
            Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();

            String username = claims.getSubject();
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                @SuppressWarnings("unchecked")
                List<String> authorities = (List<String>) claims.get("authorities");

                // 5. Create auth object
                // UsernamePasswordAuthenticationToken: A built-in object, used by spring to
                // represent the current authenticated / being authenticated user.
                // It needs a list of authorities, which has type of GrantedAuthority interface,
                // where SimpleGrantedAuthority is an implementation of that interface
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null,
                        authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

                // 6. Authenticate the user
                // Now, user is authenticated
                SecurityContextHolder.getContext().setAuthentication(auth);
            }

        } catch (Exception e) {
            // In case of failure. Make sure it's clear; so guarantee user won't be
            // authenticated
            log.error(e.getMessage());
            SecurityContextHolder.clearContext();
        }

        // go to the next filter in the filter chain
        filterChain.doFilter(request, response);

    }
}
