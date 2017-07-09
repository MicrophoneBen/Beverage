package com.github.morotsman.bevarage.beverage;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class BeverageApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeverageApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
        return args -> {
            String quote = restTemplate.getForObject(
                    "https://www.systembolaget.se/api/assortment/products/xml", String.class);
            System.out.println(quote);
        };
    }

}
