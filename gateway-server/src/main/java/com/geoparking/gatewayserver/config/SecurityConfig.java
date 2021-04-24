package com.geoparking.gatewayserver.config;

import javax.servlet.http.HttpServletResponse;

import com.geoparking.gatewayserver.filter.JwtTokenAuthenticationFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .exceptionHandling()
                .authenticationEntryPoint((req, res, e) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED)).and()
                .authorizeRequests().antMatchers("/auth/authenticate", "/auth/register").permitAll()
                .antMatchers("/auth/**").authenticated().antMatchers("/api/user/**").hasRole("USER")
                .antMatchers("/api/admin/**").hasRole("ADMIN").antMatchers("/api/parking-service/admin/**")
                .hasRole("ADMIN").antMatchers("/api/booking/user/**").hasRole("USER")
                .antMatchers("/api/booking/admin/**").hasRole("ADMIN").anyRequest().permitAll();

        http.cors();

        http.addFilterBefore(jwtTokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    }

}
