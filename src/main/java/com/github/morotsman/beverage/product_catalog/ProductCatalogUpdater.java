package com.github.morotsman.beverage.product_catalog;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ProductCatalogUpdater {  
  
    private final ProductCatalogService productCatalogService;    


    public ProductCatalogUpdater(final ProductCatalogService productCatalogService) {
        this.productCatalogService = productCatalogService;
    } 

    @Scheduled(fixedRateString = "${product_catalog.provider.systembolaget.reload_product.rate_in_ms}")
    public void reportCurrentTime() {
        System.out.println("HEPP!!!!!!!!!!!!!!!!");
        productCatalogService.reloadProductCatalog();  
    }

}
