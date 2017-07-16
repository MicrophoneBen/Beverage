package com.github.morotsman.bevarage.rater.service;

import com.github.morotsman.bevarage.product_catalog.ProductRepository;
import com.github.morotsman.bevarage.product_catalog.model.Product;
import com.github.morotsman.bevarage.product_catalog.model.Rate;
import com.github.morotsman.bevarage.rater.RateDto;
import com.github.morotsman.bevarage.rater.RateRepository;
import com.github.morotsman.bevarage.rater.RateService;
import javax.persistence.EntityManager;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class RateServiceImpl implements RateService {
    
    private final RateRepository rateRepository;
    
    private final ProductRepository productRepository;
    
    private final EntityManager entityManager;
    
    public RateServiceImpl(final RateRepository rateRepository, final ProductRepository productRepository, final EntityManager entityManager) {
        this.rateRepository = rateRepository;
        this.productRepository = productRepository;
        this.entityManager= entityManager;
    }
    
    private Rate fromDto(final RateDto rateDto, final Product product) {
        return new Rate(rateDto.getRateId(), rateDto.getDescription(), rateDto.getRate(), product);
    }
   
    
    private RateDto toDto(final Rate rate) {
        return new RateDto(rate.getRateId(), rate.getDescription(), rate.getRate(), rate.getProduct().getProductId());
    }

    @Transactional
    @Override 
    public RateDto createRate(final RateDto rate) {
        final Product product = entityManager.getReference(Product.class, rate.getProductId());
        final Rate createdRate = rateRepository.save(fromDto(rate,product));
        return toDto(createdRate);
    }

    @Transactional
    @Override
    public Rate getRate(long rateId) {
        return rateRepository.findOne(rateId);
    }

    @Transactional
    @Override
    public RateDto updateRate(RateDto rate) {
        final Product product = entityManager.getReference(Product.class, rate.getProductId());
        final Rate updatedRate = rateRepository.save(fromDto(rate,product));
        return toDto(updatedRate);
    }

    @Transactional
    @Override
    public void deleteRate(long rateId) {
        rateRepository.delete(rateId);
    }

    @Transactional
    @Override
    public Iterable<Rate> getRates() {
        return rateRepository.findAll();
    }
    
}
