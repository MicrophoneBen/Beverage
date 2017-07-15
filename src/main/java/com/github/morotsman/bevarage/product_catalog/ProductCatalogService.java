package com.github.morotsman.bevarage.product_catalog;

import com.github.morotsman.bevarage.product_catalog.model.Product;
import java.util.List;


public interface ProductCatalogService {
    
    void reloadProductCatalog();
    
    List<Product> getProductCatalog(String query, final int page);
    
}
