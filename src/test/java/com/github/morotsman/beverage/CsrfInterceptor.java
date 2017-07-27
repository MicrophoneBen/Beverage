package com.github.morotsman.beverage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;


public class CsrfInterceptor implements ClientHttpRequestInterceptor {

    private Optional<String> xsrfToken = Optional.empty();
    private Optional<String> jsession =  Optional.empty();
   
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {   
        if(xsrfToken.isPresent()) {
            request.getHeaders().add("X-XSRF-TOKEN", xsrfToken.get());
            request.getHeaders().add("Cookie","XSRF-TOKEN=" + xsrfToken.get() + "; " + "JSESSIONID=" + jsession.get());
        }
        
        ClientHttpResponse clientHttpResponse = execution.execute(request, body);
        
        List<String> setCookie = clientHttpResponse
                    .getHeaders()
                    .get("Set-Cookie");
        
        if(setCookie != null) {
            xsrfToken = getValueFromSetCookie(setCookie, "XSRF-TOKEN");
            jsession =  getValueFromSetCookie(setCookie, "JSESSIONID");
        }
        return clientHttpResponse;
    }
    
    private Optional<String> getValueFromSetCookie(final List<String> cookieList, final String tag) {
        return cookieList
                .stream()
                .filter(v -> v.contains(tag))
                .map(s -> StringUtils.substringBetween(s, tag + "=", ";"))
                .filter(StringUtils::isNoneEmpty)
                .findFirst();
    }
    
    
}
