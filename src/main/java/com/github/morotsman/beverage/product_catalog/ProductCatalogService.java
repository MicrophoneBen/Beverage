package com.github.morotsman.beverage.product_catalog;

import com.github.morotsman.beverage.model.product.Product;
import java.util.Optional;


public interface ProductCatalogService {
    
    void reloadProductCatalog();
    
    Iterable<Product> getProductCatalog(String query, final int page);
    
    Iterable<Product> getProductCatalog();
    
    Optional<Product> getProduct(Long id);
    
    void updateProductWithAverageRateAndNumberOfReviews(Product product);
    
}
