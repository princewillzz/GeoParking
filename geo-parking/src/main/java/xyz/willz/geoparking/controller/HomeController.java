package xyz.willz.geoparking.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.AllArgsConstructor;
import lombok.var;
import xyz.willz.geoparking.service.CustomerService;

@Controller
@AllArgsConstructor
public class HomeController {

    private final CustomerService customerService;

    @GetMapping(value = "/")
    public String home() {

        return "index";
    }

    @PutMapping(value = "/")
    @ResponseBody
    public ResponseEntity<?> checkIfUserIsLoggedIn() {

        final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof DefaultOidcUser) {

            final var userDetail = (DefaultOidcUser) principal;

            return ResponseEntity.ok().body(customerService.getCustomer(userDetail.getSubject()));
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping(value = "test")
    @ResponseBody
    public Object test() {
        var cont = SecurityContextHolder.getContext().getAuthentication();
        SecurityContextHolder.getContext().getAuthentication();
        System.err.println(cont.getPrincipal());
        System.err.println(cont.getAuthorities());
        System.err.println(cont.getCredentials());
        return cont;
    }

    @GetMapping("/login")
    public String userLoginView(Model modal) {

        return "login";
    }

    @GetMapping("/user/login")
    @ResponseBody
    public String userLoginTest() {
        return "Hewllo logged in";
    }

}
