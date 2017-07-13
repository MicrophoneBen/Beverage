package com.github.morotsman.bevarage.product_catalog.repository;

import com.github.morotsman.bevarage.product_catalog.model.Product;
import java.util.List;
import org.springframework.data.repository.CrudRepository;


public interface ProductRepository extends CrudRepository<Product, Long> {
    
    List<Product> findByName(final String query);
    
    List<Product> findByProductCategory(final String productCategory);
    
    List<Product> findDistinctProductsByNameIgnoreCaseContainingOrProducerIgnoreCaseContaining(final String name, final String producer);
    
}
