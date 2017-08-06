package com.github.morotsman.beverage.test_util;

import com.github.morotsman.beverage.test_util.CsrfInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfiguration {
    
    @Bean("CsrfRestTemplate")
    public RestTemplate restTemplate() {
        final RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new CsrfInterceptor());
        return restTemplate;
    }
    
}
