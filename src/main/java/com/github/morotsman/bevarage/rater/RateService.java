package com.github.morotsman.bevarage.rater;

import com.github.morotsman.bevarage.product_catalog.model.Rate;


public interface RateService {
    
    Rate createRate(RateDto rate);
    
    Rate getRate(long rateId);
    
    Rate updateRate(RateDto rate);
    
    void deleteRate(long rateId);
    
    Iterable<Rate> getRates();
    
}
