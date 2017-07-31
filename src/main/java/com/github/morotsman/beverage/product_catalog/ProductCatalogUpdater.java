package com.github.morotsman.beverage.product_catalog;

import com.github.morotsman.beverage.rater.RateDto;
import com.github.morotsman.beverage.rater.RateService;
import com.github.morotsman.beverage.user.BeverageUserDto;
import com.github.morotsman.beverage.user.UserService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ProductCatalogUpdater {  
    
    private final RateService rateService;
    private final ProductCatalogService productCatalogService; 
    private final UserService userService;

    public ProductCatalogUpdater(RateService rateService, ProductCatalogService productCatalogService, UserService userService) {
        this.rateService = rateService;
        this.productCatalogService = productCatalogService;
        this.userService = userService;
    }

         

    @Scheduled(fixedRateString = "${product_catalog.provider.systembolaget.reload_product.rate_in_ms}")
    public void reportCurrentTime() {
        productCatalogService.reloadProductCatalog();  
        userService.createUser(new BeverageUserDto("password","niklas",22L));
        for(long i = 0; i < 800; i++) {
            rateService.createRate("niklas", new RateDto(null, "Some description", 5L, i));
        }
        
    }

}
