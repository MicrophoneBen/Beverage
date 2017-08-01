package com.github.morotsman.beverage.product_catalog;

import com.github.morotsman.beverage.model.Product;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    
    @GetMapping("/{productId}")  
    public Product getProductCatalog(@PathVariable final Long productId) {
        return productCatalogService.getProduct(productId);
    }
      
    
}
