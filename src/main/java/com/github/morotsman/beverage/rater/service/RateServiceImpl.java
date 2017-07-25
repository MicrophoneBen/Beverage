package com.github.morotsman.beverage.rater.service;

import com.github.morotsman.beverage.model.BeverageUser;
import com.github.morotsman.beverage.model.ProductRepository;
import com.github.morotsman.beverage.model.Product;
import com.github.morotsman.beverage.model.Rate;
import com.github.morotsman.beverage.rater.RateDto;
import com.github.morotsman.beverage.model.RateRepository;
import com.github.morotsman.beverage.rater.RateService;
import java.util.stream.Collectors;
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
    
    private Rate fromDto(final RateDto rateDto, final Product product, final BeverageUser beverageUser) {
        return new Rate(rateDto.getRateId(), rateDto.getDescription(), rateDto.getRate(), product, beverageUser);
    }
   
    
    private RateDto toDto(final Rate rate) {
        return new RateDto(rate.getRateId(), rate.getDescription(), rate.getRate(), rate.getProduct().getProductId());
    }

    @Transactional
    @Override 
    public RateDto createRate(final String username, final RateDto rate) {
        final Product product = entityManager.getReference(Product.class, rate.getProductId());
        final BeverageUser user = entityManager.getReference(BeverageUser.class, username);
        final Rate createdRate = rateRepository.save(fromDto(rate,product,user));
        return toDto(createdRate);
    }

    @Transactional
    @Override
    public Rate getRate(final String username, long rateId) {
        return rateRepository.findOne(rateId);  
    }

    @Transactional
    @Override
    public RateDto updateRate(final String username, RateDto rate) {
        final Product product = entityManager.getReference(Product.class, rate.getProductId());
        final BeverageUser user = entityManager.getReference(BeverageUser.class, username);
        final Rate updatedRate = rateRepository.save(fromDto(rate,product,user));
        return toDto(updatedRate);
    }

    @Transactional
    @Override
    public void deleteRate(final String username, long rateId) {
        final BeverageUser user = entityManager.getReference(BeverageUser.class, username);
        System.out.println("Kommer hit 1!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        rateRepository.deleteByRateIdAndBevarageUser(rateId, user);
        System.out.println("Kommer hit 2!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

    @Transactional
    @Override
    public Iterable<Rate> getRates(final String username) { 
        return rateRepository.findByBevarageUser(entityManager.getReference(BeverageUser.class, username))
                .map(r -> new Rate(r.getRateId(), r.getDescription(), r.getRate(), r.getProduct(), null))
                .collect(Collectors.toList());
    }
    
}
