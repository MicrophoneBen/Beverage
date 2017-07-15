package com.github.morotsman.bevarage.product_catalog;

import com.github.morotsman.bevarage.product_catalog.model.Product;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/product_catalog")
public class ProductCatalogController {
    
    final ProductCatalogService productCatalogService;
    
    public ProductCatalogController(final ProductCatalogService productCatalogService){
        this.productCatalogService = productCatalogService;
    }
    
    @RequestMapping  
    public List<Product> getProductCatalog(final @RequestParam(required=true) String query, final @RequestParam(required=true) int page) {
        return productCatalogService.getProductCatalog(query, page);
    }
      
    
}
