package com.github.morotsman.bevarage.product_catalog.repository;

import com.github.morotsman.bevarage.product_catalog.model.Product;
import java.util.stream.Stream;
import org.springframework.data.repository.CrudRepository;


public interface ProductRepository extends CrudRepository<Product, Long> {
    
    Stream<Product> findByNameContaining(final String name);
    
    Stream<Product> findByProductCategory(final String productCategory);
    
}
