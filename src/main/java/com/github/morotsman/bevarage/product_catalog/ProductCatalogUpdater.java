package com.github.morotsman.bevarage.product_catalog;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ProductCatalogUpdater {
    
    
    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        //System.out.println("The time is now" + new Date());
    }
    
}
