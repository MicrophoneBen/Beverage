package com.github.morotsman.beverage.product_catalog.service;

import com.github.morotsman.beverage.product_catalog.ProductCatalogService;
import com.github.morotsman.beverage.model.product.Product;
import com.github.morotsman.beverage.model.product.ProductRepository;
import java.util.List;
import java.util.Optional;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
        final List productsAsList = products.collect(Collectors.toList());
        productRepository.save(productsAsList);  
    } 
  
    @Transactional
    @Override 
    public Iterable<Product> getProductCatalog(final String query, final int page) {
        //TODO add to application.properties
        PageRequest pageRequest = new PageRequest(page, 50,Sort.DEFAULT_DIRECTION, new String[]{"productId"});
        return productRepository.findDistinctProductsByNameIgnoreCaseContainingOrProducerIgnoreCaseContaining(query,query,pageRequest);          
    }

    @Transactional
    @Override 
    public Iterable<Product> getProductCatalog() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getProduct(Long id) {
        return productRepository.findOne(id.byteValue());
    }
    
    
    
}
