package com.github.morotsman.bevarage.product_catalog.service;

import com.github.morotsman.bevarage.product_catalog.model.Product;
import java.util.stream.Stream;


public interface ProductCatalogProviderService {
    
    public Stream<Product> getProductCatalog();
    
}
