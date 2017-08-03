package com.github.morotsman.beverage.model;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
    
    List<Product> findByName(final String query);
    
    List<Product> findByProductCategory(final String productCategory);
    
    List<Product> findDistinctProductsByNameIgnoreCaseContainingOrProducerIgnoreCaseContaining(final String name, final String producer);
    
    List<Product> findDistinctProductsByNameIgnoreCaseContainingOrProducerIgnoreCaseContaining(final String name, final String producer,final Pageable pageable);
    
    Optional<Product> findOne(long productId);    
}
