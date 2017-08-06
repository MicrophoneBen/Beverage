package com.github.morotsman.beverage.product_catalog;

import com.github.morotsman.beverage.review.ReviewDto;
import com.github.morotsman.beverage.review.ReviewService;
import com.github.morotsman.beverage.user.BeverageUserDto;
import com.github.morotsman.beverage.user.UserService;
import java.util.Random;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ProductCatalogUpdater {  
    
    private final ReviewService rateService;
    private final ProductCatalogService productCatalogService; 
    private final UserService userService;

    public ProductCatalogUpdater(ReviewService rateService, ProductCatalogService productCatalogService, UserService userService) {
        this.rateService = rateService;
        this.productCatalogService = productCatalogService;
        this.userService = userService;
    }

           

    @Scheduled(fixedRateString = "${product_catalog.provider.systembolaget.reload_product.rate_in_ms}")
    public void reloadProductCatalog() {  
        productCatalogService.reloadProductCatalog();  
        userService.createUser(new BeverageUserDto("password","niklas",22L));
        
        Random ran = new Random();
        
        productCatalogService.getProductCatalog().forEach(p -> {
            long rate = ran.nextInt(11);
            rateService.createReview("niklas", new ReviewDto(null, "Some description", rate, p.getProductId(), p.getName(), p.getProducer()));
        });
        
        
    }

}
