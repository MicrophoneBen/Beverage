package com.github.morotsman.bevarage.product_catalog;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ProductCatalogUpdater {
    
   final ProductCatalogService productCatalogService;
    
    public ProductCatalogUpdater(final ProductCatalogService productCatalogService) {
        this.productCatalogService = productCatalogService;
    }  
    
    //TODO move to application.properties
    private final int rate = 60000*60;
    
    @Scheduled(fixedRate = rate)
    public void reportCurrentTime() {
        System.out.println("HEPP!!!!!!!!!!!!!!!!");   
        productCatalogService.reloadProductCatalog();
    }
    
}
