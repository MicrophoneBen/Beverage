package com.github.morotsman.bevarage.product_catalog.service;

import com.github.morotsman.bevarage.product_catalog.ProductCatalogService;
import com.github.morotsman.bevarage.product_catalog.model.Product;
import com.github.morotsman.bevarage.product_catalog.repository.ProductRepository;
import java.util.Comparator;
import java.util.List;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ProductCatalogServiceImpl implements ProductCatalogService {
    
    private final ProductCatalogProviderService productCatalogProviderService;
    private final ProductRepository productRepository;

    public ProductCatalogServiceImpl(final ProductCatalogProviderService productCatalogProviderService, final ProductRepository productRepository) {
        this.productCatalogProviderService = productCatalogProviderService;
        this.productRepository = productRepository;
    }
    
    @Transactional
    @Override
    public void reloadProductCatalog() {   
        Stream<Product> products = productCatalogProviderService.getProductCatalog();

        productRepository.save(products.collect(Collectors.toList()));      
    } 
  
    @Transactional
    @Override 
    public List<Product> getProductCatalog(final String query) {
        return productRepository.findDistinctProductsByNameIgnoreCaseContainingOrProducerIgnoreCaseContaining(query,query);          
    }
    
    
    
}
