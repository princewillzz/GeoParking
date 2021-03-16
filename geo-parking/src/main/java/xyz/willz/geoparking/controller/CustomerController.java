package xyz.willz.geoparking.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import xyz.willz.geoparking.service.CustomerService;

@Controller
@AllArgsConstructor
@Slf4j
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping(value = "/customer/my-bookings")
    public ModelAndView myBookingsView() {
        return new ModelAndView("customer_booking_history");
    }

    @GetMapping(value = "/customer/update")
    public RedirectView updateCustomerMobile(@RequestParam("token") final String token,
            @RequestParam("mobile") final String mobile, final RedirectAttributes redirectAttributes) {
        final RedirectView redirectView = new RedirectView("/");

        try {

            final DefaultOidcUser principal = (DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();

            customerService.updateCustomerMobile(principal.getSubject(), token, mobile);

            redirectAttributes.addAttribute("success", "Updated info...!");
        } catch (Exception e) {
            log.error(e.getMessage());
            redirectAttributes.addAttribute("error", "Unable to update info...!");
        }

        return redirectView;
    }

}
