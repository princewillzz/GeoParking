package xyz.willz.geoparking.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import xyz.willz.geoparking.service.CustomerService;
import xyz.willz.geoparking.service.RenterService;

@EnableWebSecurity
public class SecurityConfig {


    @Order(1)
    @Configuration
    public static class CustomerSecurityConfig extends WebSecurityConfigurerAdapter {

        private final CustomerService customerService;

        @Autowired
        public CustomerSecurityConfig(@Qualifier("customerService") final CustomerService customerService) {
            this.customerService = customerService;
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(customerService);

        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/customer/**")
                    .authorizeRequests()
                    .anyRequest()
                    .hasRole("CUSTOMER")
                    .and()
                    .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/customer/login")
                    .failureUrl("/login")
                    .defaultSuccessUrl("/", true).permitAll()
                    .and().logout().logoutUrl("/logout").logoutSuccessUrl("/")
                    .invalidateHttpSession(true).deleteCookies("JSESSIONID", "XSRF-TOKEN")
                    .and().csrf().disable();

        }
    }

    @Configuration
    public static class RenterConfig extends WebSecurityConfigurerAdapter {

        private final RenterService renterService;

        @Autowired
        public RenterConfig(@Qualifier("renterService") final RenterService renterService) {
            this.renterService = renterService;
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(renterService);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()
                    .antMatcher("/renter/**")
                    .authorizeRequests().anyRequest().hasRole("RENTER")
                    .and()
                    .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/customer/login")
                    .failureUrl("/renter/login")
                    .defaultSuccessUrl("/retner", true).permitAll()
                    .and()
                    .logout().logoutUrl("/logout")
                    .logoutSuccessUrl("/renter/login")
                    .invalidateHttpSession(true).deleteCookies("JSESSIONID", "XSRF-TOKEN");

        }
    }

    @Bean
    public static PasswordEncoder encoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}
