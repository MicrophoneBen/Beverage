package com.github.morotsman.beverage.rater;


public interface RateService {
    
    RateDto createRate(String username,RateDto rate);
    
    RateDto getRate(String username,long rateId);
    
    RateDto updateRate(String username, RateDto rate);
    
    void deleteRate(String username, long rateId);
    
    Iterable<RateDto> getRates(String username, String query, final int page);

    void deleteAllRates();
    
}
