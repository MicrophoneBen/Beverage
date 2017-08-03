package com.github.morotsman.beverage.product_catalog;

import com.github.morotsman.beverage.model.product.Product;
import com.github.morotsman.beverage.model.exceptions.UnknownProductException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/product_catalog")
public class ProductCatalogController {
    
    final ProductCatalogService productCatalogService;
    
    public ProductCatalogController(final ProductCatalogService productCatalogService){
        this.productCatalogService = productCatalogService;
    }
    
    @RequestMapping  
    public Iterable<Product> getProductCatalog(final @RequestParam(required=true) String query, final @RequestParam(required=true) int page) {
        return productCatalogService.getProductCatalog(query, page);
    }
    
    @GetMapping("/{productId}")  
    public Product getProductCatalog(@PathVariable final Long productId) {
        return productCatalogService.getProduct(productId).orElseThrow(() -> new UnknownProductException());
    }
    
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler({UnknownProductException.class})
    public void handleException(UnknownProductException e) {
        System.out.println(e.getMessage());  
        e.printStackTrace(System.out);  
    }
      
    
}
