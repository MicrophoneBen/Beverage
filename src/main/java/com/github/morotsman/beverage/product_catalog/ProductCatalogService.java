package com.github.morotsman.beverage.product_catalog;

import com.github.morotsman.beverage.model.Product;
import java.util.List;


public interface ProductCatalogService {
    
    void reloadProductCatalog();
    
    List<Product> getProductCatalog(String query, final int page);
    
}
