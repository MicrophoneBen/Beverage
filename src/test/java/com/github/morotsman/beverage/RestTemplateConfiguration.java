package com.github.morotsman.beverage;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfiguration {
    
    @Bean("CsrfRestTemplate")
    public RestTemplate restTemplate() {
        System.out.println("******************");
         System.out.println("******************");
          System.out.println("******************");
           System.out.println("******************");
        final RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new CsrfInterceptor());
        return restTemplate;
    }
    
}
