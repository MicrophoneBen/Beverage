package com.github.morotsman.bevarage.product_catalog.service;

import com.github.morotsman.bevarage.product_catalog.ProductCatalogService;
import com.github.morotsman.bevarage.product_catalog.model.Product;
import com.github.morotsman.bevarage.product_catalog.ProductRepository;
import java.util.List;

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
    public List<Product> getProductCatalog(final String query, final int page) {
        PageRequest pageRequest = new PageRequest(page, 50,Sort.DEFAULT_DIRECTION, new String[]{"productId"});
        return productRepository.findDistinctProductsByNameIgnoreCaseContainingOrProducerIgnoreCaseContaining(query,query,pageRequest);          
    }
    
    
    
}
