package xyz.willz.geoparking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.AllArgsConstructor;
import xyz.willz.geoparking.service.CustomerService;

@Controller
public class HomeController {

    @Autowired
    private CustomerService customerService;

    @GetMapping(value = "/")
    public String home() {

        return "index";
    }

    @PutMapping(value = "/")
    @ResponseBody
    public ResponseEntity<?> checkIfUserIsLoggedIn() {

        final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof DefaultOidcUser) {

            final DefaultOidcUser userDetail = (DefaultOidcUser) principal;

            return ResponseEntity.ok().body(customerService.getCustomer(userDetail.getSubject()));
        }

        return ResponseEntity.badRequest().build();
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
