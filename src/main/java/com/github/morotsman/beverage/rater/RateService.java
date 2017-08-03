package com.github.morotsman.beverage.rater;

import java.util.Optional;


public interface RateService {
    
    Optional<RateDto> createRate(String username,RateDto rate);
    
    Optional<RateDto> getRate(String username,long rateId);
    
    Optional<RateDto> updateRate(String username, RateDto rate);
    
    Optional<Boolean> deleteRate(String username, long rateId);
    
    Iterable<RateDto> getRates(String username, String query, final int page);

    void deleteAllRates();
    
}
