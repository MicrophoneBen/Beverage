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
        return new Rate(rateDto.getRateId(), rateDto.getDescription(), rateDto.getRate(), product, beverageUser,timestamp);
    }
   
    
    private RateDto toDto(final Rate rate) {
        return new RateDto(rate.getRateId(), rate.getDescription(), rate.getRate(), rate.getProduct().getProductId());
    }

    @Transactional
    @Override 
    public RateDto createRate(final String username, final RateDto rate) {
        final Product product = entityManager.getReference(Product.class, rate.getProductId());
        final BeverageUser user = entityManager.getReference(BeverageUser.class, username);
        final Rate createdRate = rateRepository.save(fromDto(rate,product,user, System.currentTimeMillis()));
        return toDto(createdRate);
    }

    @Transactional
    @Override
    public RateDto getRate(final String username, long rateId) {        
        return toDto(getRateIfOwnedByUser(username, rateId));  
    }
    
    private Rate getRateIfOwnedByUser(final String username, long rateId) {
        final Rate rate = rateRepository.findOne(rateId);
        if(rate == null) throw new UnknownRateException();
        
        final BeverageUser owner = rate.getBevarageUser();
        if(!owner.getUsername().equals(username)) throw new WrongUserException();
        
        return rate;
    }

    @Transactional
    @Override
    public RateDto updateRate(final String username, RateDto rateDto) {
        final Rate rate = getRateIfOwnedByUser(username, rateDto.getRateId());         
        final Product product = entityManager.getReference(Product.class, rateDto.getProductId());
        final Rate updatedRate = rateRepository.save(fromDto(rateDto,product,rate.getBevarageUser(),System.currentTimeMillis()));
        return toDto(updatedRate);
    }

    @Transactional
    @Override
    public void deleteRate(final String username, long rateId) {
        final Rate rate = getRateIfOwnedByUser(username, rateId);     
        rateRepository.deleteByRateIdAndBevarageUser(rateId, rate.getBevarageUser());
    }  

    @Transactional
    @Override
    public Iterable<Rate> getRates(final String username) { 
        return rateRepository.findByBevarageUserOrderByUpdatedDesc(entityManager.getReference(BeverageUser.class, username))
                .map(r -> new Rate(r.getRateId(), r.getDescription(), r.getRate(), r.getProduct(), null,r.getUpdated()))
                .collect(Collectors.toList());
    }
    
    @Transactional
    @Override
    public Iterable<Rate> getRates(String username, int page) {
        //TODO add to application.properties
        PageRequest pageRequest = new PageRequest(page, 50,Sort.DEFAULT_DIRECTION, new String[]{"updated"});
        return rateRepository.findByBevarageUserOrderByUpdatedDesc(entityManager.getReference(BeverageUser.class, username),pageRequest)
                .map(r -> new Rate(r.getRateId(), r.getDescription(), r.getRate(), r.getProduct(), null,r.getUpdated()))
                .collect(Collectors.toList());
    }    
    
    @Transactional
    @Override
    public void deleteAllRates() {
        rateRepository.deleteAll();
    }
   
}
