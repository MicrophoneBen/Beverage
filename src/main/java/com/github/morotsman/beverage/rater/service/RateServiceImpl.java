package com.github.morotsman.beverage.rater.service;

import com.github.morotsman.beverage.model.BeverageUser;
import com.github.morotsman.beverage.model.ProductRepository;
import com.github.morotsman.beverage.model.Product;
import com.github.morotsman.beverage.model.Rate;
import com.github.morotsman.beverage.rater.RateDto;
import com.github.morotsman.beverage.model.RateRepository;
import com.github.morotsman.beverage.rater.RateService;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;

import javax.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    
    private Rate fromDto(final RateDto rateDto, final Product product, final BeverageUser beverageUser, final Long timestamp) {
        return new Rate(rateDto.getRateId(), rateDto.getDescription(), rateDto.getRate(), product, beverageUser,timestamp, product.getName(), product.getProducer());
    }
   
    
    private RateDto toDto(final Rate rate) {
        return new RateDto(rate.getRateId(), rate.getDescription(), rate.getRate(), rate.getProduct().getProductId(), rate.getName(), rate.getProducer());
    }

    @Transactional
    @Override 
    public Optional<RateDto> createRate(final String username, final RateDto rate) {
        return productRepository
                .findOne(rate.getProductId().longValue())
                .map(product -> {
                    final BeverageUser user = entityManager.getReference(BeverageUser.class, username);
                    return fromDto(rate,product,user, System.currentTimeMillis());
                })
                .map(rateRepository::save)
                .map(this::toDto);
    }

    @Transactional
    @Override
    public Optional<RateDto> getRate(final String username, long rateId) {          
        return getRateIfOwnedByUser(username, rateId)
                .map(this::toDto);  
    }
    
    private Optional<Rate> getRateIfOwnedByUser(final String username, long rateId) {
        return rateRepository.findOne(rateId)
                .filter(rate -> rate.getBevarageUser().getUsername().equals(username));
    }

    @Transactional
    @Override
    public Optional<RateDto> updateRate(final String username, RateDto rateDto) {
        return getRateIfOwnedByUser(username, rateDto.getRateId())
                .map((Rate rate) -> {
                    return fromDto(rateDto, rate.getProduct(), rate.getBevarageUser(),System.currentTimeMillis());
                })
                .map(rateRepository::save)
                .map(this::toDto);
    }

    @Transactional
    @Override
    public Optional<Boolean> deleteRate(final String username, long rateId) {
        return getRateIfOwnedByUser(username, rateId)
                .map(rate -> {
                    rateRepository.deleteByRateIdAndBevarageUser(rateId, rate.getBevarageUser());
                    return true;
                });
    }  
    
    @Transactional
    @Override
    public Iterable<RateDto> getRates(String username, String query, int page) {
        //TODO add to application.properties
        PageRequest pageRequest = new PageRequest(page, 100,Sort.DEFAULT_DIRECTION, new String[]{"updated"});
        BeverageUser user = entityManager.getReference(BeverageUser.class, username);
        return rateRepository.findDistinctByBevarageUserAndNameIgnoreCaseContainingOrBevarageUserAndProducerIgnoreCaseContainingOrderByUpdatedDesc(user,query,user,query,pageRequest)
                .map(r -> new RateDto(r.getRateId(), r.getDescription(), r.getRate(), r.getProduct().getProductId(), r.getName(), r.getProducer()))  
                .collect(Collectors.toList());
    }    
    
    @Transactional
    @Override
    public void deleteAllRates() {
        rateRepository.deleteAll();
    }
   
}
