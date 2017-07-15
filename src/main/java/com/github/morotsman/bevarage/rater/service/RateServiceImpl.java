package com.github.morotsman.bevarage.rater.service;

import com.github.morotsman.bevarage.product_catalog.ProductRepository;
import com.github.morotsman.bevarage.product_catalog.model.Product;
import com.github.morotsman.bevarage.product_catalog.model.Rate;
import com.github.morotsman.bevarage.rater.RateDto;
import com.github.morotsman.bevarage.rater.RateRepository;
import com.github.morotsman.bevarage.rater.RateService;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class RateServiceImpl implements RateService {
    
    private final RateRepository rateRepository;
    
    private final ProductRepository productRepository;
    
    public RateServiceImpl(final RateRepository rateRepository, final ProductRepository productRepository) {
        this.rateRepository = rateRepository;
        this.productRepository = productRepository;
    }
    
    private Rate fromDto(final RateDto rateDto, final Product product) {
        return new Rate(rateDto.getRateId(), rateDto.getDescription(), rateDto.getRate(), product);
    }
   
    
    private RateDto toDto(final Rate rate) {
        return new RateDto(rate.getRateId(), rate.getDescription(), rate.getRate(), rate.getProduct().getProductId());
    }

    @Transactional
    @Override
    public Rate createRate(final RateDto rate) {
        Product product = productRepository.findOne(rate.getProductId());
        return rateRepository.save(fromDto(rate,product));
    }

    @Transactional
    @Override
    public Rate getRate(long rateId) {
        return rateRepository.findOne(rateId);
    }

    @Transactional
    @Override
    public Rate updateRate(RateDto rate) {
        return rateRepository.save(fromDto(rate,null));
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
