package com.geoparking.gatewayserver.filter;

import com.netflix.zuul.ZuulFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class HandleOnErrorZuulFilter extends ZuulFilter {

    final Logger log = LoggerFactory.getLogger(HandleOnErrorZuulFilter.class);

    @Override
    public boolean shouldFilter() {

        return true;
    }

    @Override
    public Object run() {

        log.error("Zuul failure detected: ");

        // final RequestContext context = RequestContext.getCurrentContext();
        // final Object throwable = context.get("throwable");

        // if (throwable instanceof ZuulException) {
        // final ZuulException zuulException = (ZuulException) throwable;
        //

        // // remove error code to prevent further error handling in follow up filters
        // context.remove("throwable");

        // // populate context with new response values
        // context.setResponseBody("Service unavailable...");
        // context.getResponse().setContentType("application/json");
        // // can set any error code as excepted
        // context.setResponseStatusCode(503);
        // }
        return null;
    }

    @Override
    public String filterType() {

        return "error";
    }

    @Override
    public int filterOrder() {
        return -1;
    }

}
