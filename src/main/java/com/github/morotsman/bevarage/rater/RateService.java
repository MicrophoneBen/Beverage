package com.github.morotsman.bevarage.rater;

import com.github.morotsman.bevarage.product_catalog.model.Rate;


public interface RateService {
    
    RateDto createRate(RateDto rate);
    
    Rate getRate(long rateId);
    
    RateDto updateRate(RateDto rate);
    
    void deleteRate(long rateId);
    
    Iterable<Rate> getRates();
    
}
