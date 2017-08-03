package com.github.morotsman.beverage.product_catalog.service;

import com.github.morotsman.beverage.model.product.Product;
import java.util.stream.Stream;


public interface ProductCatalogProviderService {
    
    public Stream<Product> getProductCatalog();
    
}
