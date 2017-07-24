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
    
    private String extectXsrfToken(final String source) {
        return StringUtils.substringBetween(source, "XSRF-TOKEN=", ";");
    }
    
    private String extectJSessionToken(final String source) {
        return StringUtils.substringBetween(source, "JSESSIONID=", ";");
    }
    
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
        
        System.out.println(setCookie);
        
        if(setCookie != null) {
            xsrfToken = setCookie
                    .stream()
                    .filter(v -> v.contains("XSRF-TOKEN"))
                    .map(this::extectXsrfToken)
                    .filter(StringUtils::isNoneEmpty)
                    .findFirst();
        }
        
        if(setCookie != null) {
            jsession = setCookie
                    .stream()
                    .filter(v -> v.contains("JSESSIONID"))
                    .map(this::extectJSessionToken)
                    .filter(StringUtils::isNoneEmpty)
                    .findFirst();
        }
        
        
      
        return clientHttpResponse;
    }
    
}
