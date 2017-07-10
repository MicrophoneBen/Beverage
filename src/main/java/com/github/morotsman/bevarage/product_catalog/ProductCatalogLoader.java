package com.github.morotsman.bevarage.product_catalog;


import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ProductCatalogLoader {
    
    final ProductCatalogService productCatalogService;
    
    public ProductCatalogLoader(final ProductCatalogService productCatalogService) {
        this.productCatalogService = productCatalogService;
    }  
  
      
    @Bean
    public CommandLineRunner run(final RestTemplate restTemplate) throws Exception {
        return args -> {
            productCatalogService.reloadProductCatalog();
            
        };
    }   
    

    

}
