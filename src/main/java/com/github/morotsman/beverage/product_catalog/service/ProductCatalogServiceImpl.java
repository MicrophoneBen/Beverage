package com.github.morotsman.beverage.product_catalog.service;

import com.github.morotsman.beverage.product_catalog.ProductCatalogService;
import com.github.morotsman.beverage.model.product.Product;
import com.github.morotsman.beverage.model.product.ProductRepository;
import com.github.morotsman.beverage.model.review.ReviewRepository;
import java.util.Date;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Optional;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProductCatalogServiceImpl implements ProductCatalogService {

    private final ProductCatalogProviderService productCatalogProviderService;
    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;
    private final int pageSize;

    public ProductCatalogServiceImpl(final ProductCatalogProviderService productCatalogProviderService,
            final ProductRepository productRepository, ReviewRepository reviewRepository,
            @Value("${product_catalog.provider.page_size}") int pageSize) {
        this.productCatalogProviderService = productCatalogProviderService;
        this.productRepository = productRepository;
        this.pageSize = pageSize;
        this.reviewRepository = reviewRepository;
    }

    @Transactional
    @Override
    public void reloadProductCatalog() {
        productCatalogProviderService.getProductCatalog().forEach(p -> {
            Product existingProduct = productRepository.findOne(p.getProductId());
            if (existingProduct != null) {
                productRepository.updateProduct(
                        p.getName(), p.getProductCategory(), p.getPrice(), p.getVolume(), p.getNr(), p.getArticelNumber(),
                        p.getPricePerLiter(), p.getStartSellDate(), p.getExpired(), p.getType(), p.getStyle(), p.getPackaging(), p.getSeal(),
                        p.getOrigin(), p.getOriginCountry(), p.getProducer(), p.getSupplier(), p.getVintage(), p.getAlcoholPercent(),
                        p.getAssortment(), p.getAssortmentText(), p.getEcologic(), p.getEtnic(), p.getKoscher(), p.getRawMaterialDescription(), p.getProductId()
                );
            } else {
                productRepository.save(p);
            }

        });
        //final List productsAsList = products.collect(Collectors.toList());
        //productRepository.save(productsAsList);  
    }

    @Transactional
    @Override
    public Iterable<Product> getProductCatalog(final String query, final int page) {
        //TODO add to application.properties
        PageRequest pageRequest = new PageRequest(page, pageSize, Sort.DEFAULT_DIRECTION, new String[]{"productId"});
        return productRepository.findDistinctProductsByNameIgnoreCaseContainingOrProducerIgnoreCaseContaining(query, query, pageRequest);
    }

    @Transactional
    @Override
    public Iterable<Product> getProductCatalog() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getProduct(Long id) {
        return productRepository.findOne(id.longValue());
    }

    @Transactional
    @Override
    public void updateProductWithAverageRateAndNumberOfReviews(Product product) {
        System.out.println(product.getProductId());
        LongSummaryStatistics stats = reviewRepository.findByProduct(product).stream().mapToLong(p -> p.getRate()).summaryStatistics();
        System.out.println(stats);
        productRepository.updateProductAverageRateAndNumberOfReviews(Math.round(stats.getAverage() * 10d) / 10d, stats.getCount(), product.getProductId());
    }

}
