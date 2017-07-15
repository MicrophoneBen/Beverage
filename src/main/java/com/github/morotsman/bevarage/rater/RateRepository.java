package com.github.morotsman.bevarage.rater;

import com.github.morotsman.bevarage.product_catalog.model.Rate;
import org.springframework.data.repository.CrudRepository;


public interface RateRepository extends CrudRepository<Rate, Long> {
    
    
}
