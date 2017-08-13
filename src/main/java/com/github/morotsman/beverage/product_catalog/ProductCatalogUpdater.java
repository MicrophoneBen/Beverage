package com.github.morotsman.beverage.product_catalog;

import com.github.morotsman.beverage.review.ReviewDto;
import com.github.morotsman.beverage.review.ReviewService;
import com.github.morotsman.beverage.user.BeverageUserDto;
import com.github.morotsman.beverage.user.UserService;
import java.util.Random;
import java.util.stream.IntStream;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ProductCatalogUpdater {  
    
    private final ProductCatalogService productCatalogService; 
    
    private final UserService userService;
    private final ReviewService reviewService;
    
    public ProductCatalogUpdater(ProductCatalogService productCatalogService, UserService userService, ReviewService reviewService) {
        this.productCatalogService = productCatalogService;
        this.userService = userService;
        this.reviewService = reviewService;
    }

           

    @Scheduled(fixedRateString = "${product_catalog.provider.systembolaget.reload_product.rate_in_ms}")
    public void reloadProductCatalog() { 
        System.out.println("**************************");
        System.out.println("*Loading product catalog.*");
        System.out.println("**************************");
        productCatalogService.reloadProductCatalog(); 
        System.out.println("*************************");
        System.out.println("*Product catalog loaded.*");
        System.out.println("*************************");   
        
        createFakeReviews();
    }
    
    //used when developing
    
    private void createFakeReviews() {
        Random ran = new Random();
        
        
        IntStream.rangeClosed(0, 500).forEach(n -> {
            long rate = ran.nextInt(11);
            userService.createUser(new BeverageUserDto("password","user" + n,22L));
            reviewService.createReview("user" + n, new ReviewDto(null, "Some description", rate, 1L, "Renat", "Pernod Ricard"));
        });
        userService.createUser(new BeverageUserDto("password","niklas",22L));
        
        
        
        
        
        productCatalogService.getProductCatalog().forEach(p -> {
            long rate = ran.nextInt(11);
            reviewService.createReview("niklas", new ReviewDto(null, "Some description", rate, p.getProductId(), p.getName(), p.getProducer()));
        });   
    }
    

}
