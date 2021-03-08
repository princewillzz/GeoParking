package xyz.willz.geoparking.EventListener;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import xyz.willz.geoparking.service.CustomerService;

@Component
@AllArgsConstructor
@Slf4j
public class LoginActionListener implements ApplicationListener<InteractiveAuthenticationSuccessEvent> {

        private final CustomerService customerService;

        @Override
        public void onApplicationEvent(InteractiveAuthenticationSuccessEvent event) {

                if (event.getAuthentication().getPrincipal() instanceof DefaultOidcUser) {

                        final DefaultOidcUser userDetails = (DefaultOidcUser) event.getAuthentication().getPrincipal();
                        System.err.println(userDetails);

                        System.out.println("Login initiated");

                        try {
                                customerService.saveCustomerDetail(userDetails);
                        } catch (Exception e) {
                                log.error(e.getMessage());
                                SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);

                        }
                }
        }

}
